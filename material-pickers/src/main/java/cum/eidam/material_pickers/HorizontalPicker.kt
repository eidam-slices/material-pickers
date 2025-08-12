package cum.eidam.material_pickers

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.map

@Composable
fun <T> HorizontalPicker(
    items: List<T>,
    value: T,
    onValueChange: (T) -> Unit,
    modifier: Modifier = Modifier,
    itemWidth: Dp = 52.dp,
    itemHeight: Dp = 46.dp,

    shape: Shape = MaterialTheme.shapes.medium,
    // TODO: rename to verticalTextOffset
    textOffset: Dp = 0.dp,
) {

    val density = LocalDensity.current
    // calculations:
    val itemWidthPx = with(density) { itemWidth.toPx() }
    val horizontalPickerPadding = itemWidth * (PICKER_ITEMS_VISIBLE / 2)

    val centerItemIndexOffset = (PICKER_ITEMS_VISIBLE / 2) - 1

    // lazy row behavior:
    val listState = rememberLazyListState()
    val flingBehavior = rememberSnapFlingBehavior(listState)
    val coroutineScope = rememberCoroutineScope()

    val scroller = remember {
        PickerScroller(
            itemsVisible = PICKER_ITEMS_VISIBLE,
            listState = listState,
            coroutineScope = coroutineScope,
            density = density,
            itemBoxSize = itemWidth
        ) { /* TODO: implement onExternalScroll */ }
    }


    var internalValueChangeLock by remember { mutableStateOf(false) }

    LaunchedEffect(value) {
        if (!internalValueChangeLock) {
            val index = items.indexOf(value)
            scroller.externallyScrollToItem(index)
        } else internalValueChangeLock = false
    }

    LaunchedEffect(listState, value) {
        snapshotFlow {
            listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset
        }.map { (index, offset) ->
            val halfItemSizePx = itemWidthPx / 2
            val indexOffset = if (offset > halfItemSizePx) 1
            else 0

            index + indexOffset + centerItemIndexOffset
        }.distinctUntilChanged()
            .filter { it in items.indices }
            .filterNot { items[it] == value }
            .collect { index ->
                internalValueChangeLock = true
                onValueChange(items[index])
            }
    }




    Surface(

        modifier = Modifier.Companion
            .width(itemWidth * PICKER_ITEMS_VISIBLE)
            .height(itemHeight),
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        shape = shape,
    ) {

        Box {

            Box(
                modifier = Modifier
                    .height(itemHeight)
                    .width(itemWidth)
                    .align(Alignment.Center)
                    .background(MaterialTheme.colorScheme.primaryContainer)
            )

            LazyRow(
                modifier = Modifier.fillMaxSize(),

                state = listState,
                flingBehavior = flingBehavior,

                contentPadding = PaddingValues(
                    horizontal = horizontalPickerPadding
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {

                items(items) { item ->
                    val selected = item == value
                    val index = items.indexOf(item)

                    PickerItem(
                        text = item.toString(),
                        selected = selected,
                        modifier = Modifier.width(itemWidth).height(itemHeight),
                        textModifier = Modifier.offset(y = textOffset)
                    ) { // onClick ->
                        if (value == item) return@PickerItem

                        scroller.scrollToItem(index)
                    }

                }

            }
        }
    }



}