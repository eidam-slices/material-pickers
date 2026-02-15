package cz.eidam.material_pickers.picker_parts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.DpSize

/**
 * Visual overlay indicating the currently selected picker row/column.
 *
 * This composable simply draws a box of the given [size] using the supplied [color]
 * and [shape]. It's typically positioned to align with the center item in a wheel picker.
 *
 * You normally won't use this directly; higher level defaults like
 * `PickerDefaults.SelectionIndicator` wire style tokens for you.
 *
 * @param size Desired width & height of the indicator area.
 * @param color Background color of the indicator region.
 * @param shape Shape used for clipping & background drawing (e.g. rounded rectangle).
 */
@Composable
fun PickerSelectionIndicator(
    size: DpSize,
    color: Color,
    shape: Shape,
) {

    Box(
        modifier = Modifier
            .width(size.width)
            .height(size.height)
            .background(
                color = color,
                shape = shape
            )
    )


}