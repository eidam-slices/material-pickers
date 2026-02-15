package cz.eidam.material_pickers

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.DpSize
import cz.eidam.material_pickers.logic.PickerScroller
import cz.eidam.material_pickers.logic.calculateItemSize
import cz.eidam.material_pickers.logic.observeSelectedIndex
import cz.eidam.material_pickers.picker_parts.LazyPickerList
import cz.eidam.material_pickers.picker_parts.PickerItemData
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNot

/**
 * Core building block powering concrete picker variants (vertical / horizontal, single / double).
 *
 * Responsibilities:
 * 1. Measures available space and computes a single item size (px + dp) via [calculateItemSize].
 * 2. Derives a [PickerProperties] snapshot for downstream layout helpers.
 * 3. Hosts layered content using [SubcomposeLayout]: background, selection indicator, lazy list.
 * 4. Creates and manages a [PickerScroller] to animate scroll-to-item requests.
 * 5. Observes scroll position changes and emits stable selected indices while preventing
 *    feedback loops with lock flags.
 *
 * Item sizing strategy:
 * - The main-axis dimension is divided by [itemsVisible] (for vertical height / horizontal width).
 * - Cross-axis extends to the full constraint in that axis.
 * - If constraints are unbounded in the needed axis, item size becomes 0 (caller should guard).
 *
 * Selection computation:
 * - Uses [observeSelectedIndex] which considers the first visible item + offset > 1/2 item.
 * - Applies a center offset of (itemsVisible / 2 - 1) so the logical index aligns with the center slot.
 *
 * Locking logic:
 * - external scroll (programmatic) sets valueChangeLock to ignore intermediate emissions.
 * - internalValueChangeLock avoids re-triggering programmatic scroll when state is being updated externally.
 *
 * Layer order (bottom -> top):
 * background() -> selectionIndicator() -> LazyPickerList (items render above background but under overlay logic except indicator sits centered visually due to Box layering).
 *
 * Expectations:
 * - Caller ensures [itemsVisible] is odd so a unique center exists (not validated here).
 * - [selectedIndex] ideally within item bounds; out-of-range indices are filtered on observe.
 *
 * @param T Item element type.
 * @param items Data items to show (converted to string inside default item composable).
 * @param selectedIndex Current selected index (source of truth held by caller).
 * @param onItemSelected Invoked with new index after scroll / tap selection change.
 * @param orientation Scroll orientation.
 * @param modifier Layout modifier.
 * @param itemsVisible Count of visible slots (should be odd for centering).
 * @param background Composable layer drawn beneath list & indicator (e.g. Surface).
 * @param item Item slot renderer receiving [PickerItemData].
 * @param selectionIndicator Center overlay sized to one item slot.
 */
@Composable
fun <T> GenericPicker(
    items: List<T>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    orientation: Orientation,
    modifier: Modifier = Modifier,

    itemsVisible: Int,

    background: @Composable () -> Unit,

    item: @Composable (PickerItemData) -> Unit,

    selectionIndicator: @Composable (
        itemSize: DpSize,
    ) -> Unit,
) {

    SubcomposeLayout(modifier) { constraints ->
        val containerWidth = constraints.maxWidth
        val containerHeight = constraints.maxHeight

        // Compute per-item size in px + convert to dp snapshot
        val itemSizePx = calculateItemSize(orientation, constraints, itemsVisible)
        val itemSizeDp = DpSize(
            width = itemSizePx.width.toDp(),
            height = itemSizePx.height.toDp(),
        )

        val properties = PickerProperties(
            itemSizeDp = itemSizeDp,
            itemSizePx = itemSizePx,
            orientation = orientation,
        )

        val placeables = subcompose("picker") {
            // Stateful lazy list + coroutine scope
            val listState = rememberLazyListState()
            val coroutineScope = rememberCoroutineScope()

            // Flag controlling reaction to programmatic vs user-driven changes
            var valueChangeLock by remember { mutableStateOf(false) }

            // Animated scroller utility
            val scroller = remember {
                PickerScroller(
                    listState = listState,
                    coroutineScope = coroutineScope,
                    itemsVisible = itemsVisible,
                    itemBoxSizePx = properties.mainAxisSizePx
                ) { externalScrollInProgress ->
                    valueChangeLock = externalScrollInProgress
                }
            }

            // Prevents loop when emitting new selected index & reacting again
            var internalValueChangeLock by remember { mutableStateOf(false) }

            // External index change -> scroll
            LaunchedEffect(selectedIndex) {
                if (!internalValueChangeLock && selectedIndex in items.indices) {
                    scroller.externallyScrollToItem(selectedIndex)
                }
                else {
                    internalValueChangeLock = false
                }
            }

            // Observe list scroll -> propagate index
            val centerItemIndexOffset = (itemsVisible / 2) - 1


            LaunchedEffect(listState, selectedIndex, items) {
                listState.observeSelectedIndex(
                    itemSizePx = properties.mainAxisSizePx,
                    centerItemIndexOffset = centerItemIndexOffset
                ).filterNot { it == selectedIndex }
                    .filter { it in items.indices }
                    .filter { !valueChangeLock }
                    .filter { !internalValueChangeLock }
                    .collect { index ->
                        internalValueChangeLock = true
                        onItemSelected(index)
                    }
            }

            background()

            Box(
                contentAlignment = Alignment.Center
            ) {
                // Center overlay indicator sized to one item
                selectionIndicator(properties.itemSizeDp)

                // Lazy list of items
                LazyPickerList(
                    listState = listState,
                    properties = properties,
                ) {
                    itemsIndexed(
                        items = items,
                        key = { index, item -> "$index-$item" }
                    ) { index, item ->
                        val selected = index == selectedIndex
                        item(
                            PickerItemData(
                                label = item.toString(),
                                selected = selected,
                                size = properties.itemSizeDp,
                                onClick = {
                                    if (selected) return@PickerItemData
                                    scroller.scrollToItem(index)
                                }
                            )
                        )
                    }
                }
            }
        }.map { it.measure(constraints) }

        layout(containerWidth, containerHeight) {
            placeables.forEach { it.place(0, 0) }
        }
    }
}
