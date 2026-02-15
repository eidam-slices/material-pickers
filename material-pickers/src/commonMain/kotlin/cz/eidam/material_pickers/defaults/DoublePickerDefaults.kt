package cz.eidam.material_pickers.defaults

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import cz.eidam.material_pickers.style.DoublePickerStyle

/**
 * Default helpers for a double picker (two coordinated columns when [Orientation.Vertical],
 * or two stacked rows when [Orientation.Horizontal]).
 *
 * Responsibilities:
 * - Re-exports the shared visible items constant via [ITEMS_VISIBLE]
 * - Supplies a tuned [textOffset] used to create a subtle mirrored alignment between the two parts
 * - Exposes a theme-aware [style] factory that derives base colors from [PickerDefaults.style]
 * - Computes complementary rounded corner shapes for the two picker parts based on [orientation]
 *
 * Shape logic:
 * - Vertical: creates LEFT (start) and RIGHT (end) side-by-side surfaces; only the outer outer
 *   corners are rounded so the union visually forms a single rounded rectangle.
 * - Horizontal: creates TOP and BOTTOM stacked surfaces; only the outer top and bottom corners
 *   are rounded for the same visual effect.
 *
 * You can copy this object or its [style] implementation as a starting point if you need fully
 * custom shapes, offsets or color tokens.
 */
object DoublePickerDefaults {

    /** Re-export of [PickerDefaults.ITEMS_VISIBLE] for convenience in double picker contexts. */
    const val ITEMS_VISIBLE = PickerDefaults.ITEMS_VISIBLE

    /** Baseline absolute offset applied (mirrored) to text in each single picker part. */
    val textOffset = 6.dp

    /**
     * Produces a [DoublePickerStyle] using theme-derived colors and orientation-specific shapes.
     *
     * @param orientation Determines whether the two pickers are arranged side-by-side (Vertical)
     * or stacked (Horizontal); this changes how corner radii are assigned.
     *
     * Corner assignment summary:
     * - Vertical: (First = left) rounds start corners; (Second = right) rounds end corners.
     * - Horizontal: (First = top) rounds top corners; (Second = bottom) rounds bottom corners.
     */
    @Composable
    fun style(orientation: Orientation): DoublePickerStyle {

        val common = PickerDefaults.style()

        val r = PickerDefaults.cornerRadius

        return DoublePickerStyle(
            surfaceColor = common.surfaceColor,
            indicatorColor = common.indicatorColor,
            indicatorShape = common.indicatorShape,
            selectedItemTextColor = common.selectedItemTextColor,
            unselectedItemTextColor = common.unselectedItemTextColor,

            textOffset = textOffset,
            surfaceShapes = when (orientation) {
                Orientation.Vertical -> RoundedCornerShape(
                    topStart = r, topEnd = 0.dp,
                    bottomStart = r, bottomEnd = 0.dp,
                ) to RoundedCornerShape(
                    topStart = 0.dp, topEnd = r,
                    bottomStart = 0.dp, bottomEnd = r,
                )
                Orientation.Horizontal -> RoundedCornerShape(
                    topStart = r, topEnd = r,
                    bottomStart = 0.dp, bottomEnd = 0.dp,
                ) to RoundedCornerShape(
                    topStart = 0.dp, topEnd = 0.dp,
                    bottomStart = r, bottomEnd = r,
                )
            }

        )

    }
}