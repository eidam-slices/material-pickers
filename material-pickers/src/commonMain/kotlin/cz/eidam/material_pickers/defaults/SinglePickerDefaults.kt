package cz.eidam.material_pickers.defaults

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import cz.eidam.material_pickers.style.SinglePickerStyle

/**
 * Default helpers for a standalone (single column / row) picker instance.
 *
 * Builds upon [PickerDefaults] by:
 * - Re-exporting the shared [ITEMS_VISIBLE]
 * - Providing a themed [style] that wraps the base picker style with a rounded surface shape
 *
 * Use these defaults when you want a quick, Material‑aware configuration without
 * manually wiring color or shape tokens.
 */
object SinglePickerDefaults {

    /** Re-export of [PickerDefaults.ITEMS_VISIBLE] for convenience in single picker contexts. */
    const val ITEMS_VISIBLE = PickerDefaults.ITEMS_VISIBLE

    /**
     * Produces a [SinglePickerStyle] based on the base [PickerDefaults.style] and
     * decorated with a rounded corner surface shape.
     */
    @Composable
    fun style(): SinglePickerStyle {
        val common = PickerDefaults.style()

        return SinglePickerStyle(
            surfaceColor = common.surfaceColor,
            indicatorColor = common.indicatorColor,
            indicatorShape = common.indicatorShape,
            selectedItemTextColor = common.selectedItemTextColor,
            unselectedItemTextColor = common.unselectedItemTextColor,

            surfaceShape = RoundedCornerShape(PickerDefaults.cornerRadius),
            textOffset = 0.dp
        )
    }
}