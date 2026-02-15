package cz.eidam.material_pickers

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cz.eidam.material_pickers.defaults.DoublePickerDefaults
import cz.eidam.material_pickers.style.DoublePickerStyle

/**
 * Two side-by-side vertical pickers sharing a coordinated [DoublePickerStyle].
 *
 * Use this for selecting dual related values (e.g. hours & minutes, start & end, left/right options).
 * The composable simply delegates to two [VerticalPicker] instances with equal weight.
 *
 * @param TL Item type of the left picker.
 * @param TR Item type of the right picker.
 * @param itemsLeft Items for left column.
 * @param itemsRight Items for right column.
 * @param selectedIndexLeft Currently selected index in left column.
 * @param selectedIndexRight Currently selected index in right column.
 * @param onSelectedIndexChangeLeft Callback invoked with new left index.
 * @param onSelectedIndexChangeRight Callback invoked with new right index.
 * @param modifier Layout modifier for the containing Row.
 * @param style Combined style describing surface colors, indicator, shapes & text offset mirroring.
 * @param itemsVisible Odd number of visible rows for each column (must match for symmetry).
 */
@Composable
fun <TL, TR> DoubleVerticalPicker(
    itemsLeft: List<TL>,
    itemsRight: List<TR>,
    selectedIndexLeft: Int,
    selectedIndexRight: Int,
    onSelectedIndexChangeLeft: (Int) -> Unit,
    onSelectedIndexChangeRight: (Int) -> Unit,
    modifier: Modifier = Modifier,
    style: DoublePickerStyle = DoublePickerDefaults.style(Orientation.Vertical),
    itemsVisible: Int = DoublePickerDefaults.ITEMS_VISIBLE,
) {

    Row(
        modifier = modifier.fillMaxWidth()
    ) {

        VerticalPicker(
            items = itemsLeft,
            selectedIndex = selectedIndexLeft,
            onItemSelected = onSelectedIndexChangeLeft,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .offset(x = (0.2).dp),
            style = style.asSingle(DoublePickerStyle.Part.First),
            itemsVisible = itemsVisible,
        )
        VerticalPicker(
            items = itemsRight,
            selectedIndex = selectedIndexRight,
            onItemSelected = onSelectedIndexChangeRight,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .offset(x = (-0.2).dp),
            style = style.asSingle(DoublePickerStyle.Part.Second),
            itemsVisible = itemsVisible,
        )
    }


}