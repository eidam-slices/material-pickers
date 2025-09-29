package cum.eidam.material_pickers

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import cum.eidam.material_pickers.defaults.PickerDefaults
import cum.eidam.material_pickers.defaults.SinglePickerDefaults
import cum.eidam.material_pickers.picker_parts.PickerItem
import cum.eidam.material_pickers.style.SinglePickerStyle

/**
 * Horizontal wheel-style picker allowing users to choose an item by horizontal scrolling.
 *
 * Index-driven variant: works with duplicate values (items needn't be unique). Selection is
 * maintained externally via [selectedIndex]; when user flings or taps to select another item,
 * [onItemSelected] is invoked with the new centered index.
 *
 * Measurement & layout:
 * - Delegates to [GenericPicker] for measuring and snapping logic.
 * - Requires an odd [itemsVisible] so one item can rest perfectly centered.
 *
 * Styling:
 * - Colors, shapes & offsets come from [style].
 * - The horizontal orientation maps [SinglePickerStyle.textOffset] to a vertical offset (Y).
 *
 * Performance:
 * - Only visible items are composed (lazy list with snap fling behavior).
 *
 * @param T Item element type (displayed via toString unless custom item composable is provided upstream).
 * @param items Items to display.
 * @param selectedIndex Current selected index (expected within bounds; out-of-range is ignored for scroll but not enforced here).
 * @param onItemSelected Callback after scroll settles on a new centered index.
 * @param modifier Layout modifier.
 * @param style Visual style tokens.
 * @param itemsVisible Odd count of simultaneously visible items (default = [SinglePickerDefaults.ITEMS_VISIBLE]).
 *
 * @throws IllegalArgumentException if [itemsVisible] is not odd.
 */
@Composable
fun <T> HorizontalPicker(
    items: List<T>,
    selectedIndex: Int,
    onItemSelected: (index: Int) -> Unit,

    modifier: Modifier = Modifier,

    style: SinglePickerStyle = SinglePickerDefaults.style(),
    itemsVisible: Int = SinglePickerDefaults.ITEMS_VISIBLE,
) {
    require(itemsVisible % 2 == 1) { "itemsVisible must be an odd number to have a centered selection" }

    GenericPicker(
        orientation = Orientation.Horizontal,
        modifier = modifier,
        items = items,
        selectedIndex = selectedIndex,
        onItemSelected = onItemSelected,
        itemsVisible = itemsVisible,
        item = {
            PickerItem(
                label = it.label,
                selected = it.selected,
                onClick = it.onClick,
                selectedTextColor = style.selectedItemTextColor,
                unselectedTextColor = style.unselectedItemTextColor,
                offset = DpOffset(y = style.textOffset, x = 0.dp),
                modifier = Modifier
                    .width(it.size.width)
                    .height(it.size.height)
            )
        },
        selectionIndicator = { size ->
            PickerDefaults.SelectionIndicator(size)
        },
        background = {
            Surface(
                color = style.surfaceColor,
                shape = style.surfaceShape,
            ) { }
        }


    )

}

/**
 * Convenience overload of [HorizontalPicker] operating on value identity instead of index.
 *
 * Enforces uniqueness of [items] (via equality). The provided [selectedItem] is mapped to its
 * index; UI updates propagate by delegating to the index-based picker. Use if you prefer working
 * directly with domain objects. If duplicates are possible, use the index variant.
 *
 * @param T Item element type (must have meaningful equality for uniqueness validation).
 * @param items Unique items to display.
 * @param selectedItem Currently selected item (must exist in [items]).
 * @param onItemSelected Callback delivering the newly selected item.
 * @param modifier Layout modifier.
 * @param style Visual style tokens.
 * @param itemsVisible Odd count of visible items.
 *
 * @throws IllegalArgumentException if [itemsVisible] is not odd.
 * @throws IllegalArgumentException if [items] contain duplicates.
 */
@Composable
fun <T> HorizontalPicker(
    items: List<T>,
    selectedItem: T,
    onItemSelected: (item: T) -> Unit,

    modifier: Modifier = Modifier,

    style: SinglePickerStyle = SinglePickerDefaults.style(),
    itemsVisible: Int = PickerDefaults.ITEMS_VISIBLE,
) {
    require(itemsVisible % 2 == 1) { "itemsVisible must be an odd number to have a centered selection" }
    require(items.distinct().size == items.size) { "Items must be unique to use selectedItem variant; use index-based overload for duplicates." }

    HorizontalPicker(
        items = items,
        selectedIndex = items.indexOf(selectedItem),
        onItemSelected = { onItemSelected(items[it]) },

        modifier = modifier,

        style = style,
        itemsVisible = itemsVisible,
    )

}