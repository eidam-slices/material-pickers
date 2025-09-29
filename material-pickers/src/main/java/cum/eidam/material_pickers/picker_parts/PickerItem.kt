package cum.eidam.material_pickers.picker_parts

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.sp
import kotlin.math.log

/**
 * Single text item inside the picker list.
 *
 * Responsibilities:
 * - Applies a clickable area without default ripple (interaction source + null indication)
 * - Dynamically computes a target font size from the available max width using [scaleTextByBox]
 * - Animates font size changes when selection state toggles (selected vs unselected)
 * - Applies a positional offset ([offset]) to support horizontal/vertical text shifts inside the cell
 *
 * Sizing heuristic:
 * Font size is derived logarithmically from the item box width so that growth tapers off for
 * extremely wide layouts, keeping visual hierarchy more consistent. A higher multiplier is used
 * for the selected item to emphasize it.
 *
 * @param label Text to display.
 * @param modifier Optional modifier for layout/semantics.
 * @param selected Whether this item is currently the centered/active selection.
 * @param selectedTextColor Color for the selected state.
 * @param unselectedTextColor Color for the unselected (faded) state.
 * @param offset Positional offset applied to the text (useful for mirrored dual pickers).
 * @param onClick Callback invoked when the user taps the item.
 */
@Composable
fun PickerItem(
    label: String,
    modifier: Modifier = Modifier,
    selected: Boolean,

    selectedTextColor: Color,
    unselectedTextColor: Color,

    offset: DpOffset,

    onClick: () -> Unit,
) {

    BoxWithConstraints(
        modifier = modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onClick,
        ),
        contentAlignment = Alignment.Center,
    ) {

        val boxSize = this.maxWidth

        val fontSize = scaleTextByBox(
            boxSize = boxSize,
            multiplier = if (selected) 3.5f else 2.75f
        )
        val animatedFontSize by animateFloatAsState(
            targetValue = fontSize,
            label = "fontSizeAnimation"
        )

        Text(
            modifier = Modifier.offset(x = offset.x, y = offset.y),
            text = label,
            color = if (selected) selectedTextColor else unselectedTextColor,
            fontSize = animatedFontSize.sp,
            fontWeight = if (selected) FontWeight.W500
            else FontWeight.W400,
            maxLines = 1,
        )


    }
}

/**
 * Computes a font size from the item box width using a base-2 logarithm so that the growth rate
 * slows as width increases (sub-linear scaling). A [multiplier] adjusts emphasis for selected vs
 * unselected states.
 *
 * @param boxSize Available width (Dp) of the item container.
 * @param multiplier Scaling factor to differentiate visual prominence.
 * @return Scaled raw font size (sp units applied by caller).
 */
private fun scaleTextByBox(
    boxSize: Dp,
    multiplier: Float,
): Float = log(boxSize.value + 1f, 2f) * multiplier