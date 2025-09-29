package cum.eidam.material_pickers.defaults

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import cum.eidam.material_pickers.picker_parts.PickerSelectionIndicator
import cum.eidam.material_pickers.style.PickerStyle

/**
 * Collection of default styling values and helper composables shared across picker variants.
 *
 * Provides:
 * - Standard number of visible items ([ITEMS_VISIBLE])
 * - Corner radius token used by higher-level defaults
 * - A baseline [style] factory that is theme-aware (Material3)
 * - A convenience [SelectionIndicator] composable that renders the indicator using the default style
 *
 * You can use these as building blocks when constructing customized picker styles or override them
 * entirely for a different design language.
 */
object PickerDefaults {

    /** Default number of items (rows) simultaneously visible in the picker viewport. */
    const val ITEMS_VISIBLE = 3

    /** Shared corner radius token used by composite / outer surfaces. */
    val cornerRadius = 16.dp

    /**
     * Produces a baseline [PickerStyle] wired to the current [MaterialTheme].
     *
     * Indicator & text colors are derived from the theme's color scheme, and the surface color
     * uses a low elevation overlay for subtle separation.
     */
    @Composable
    fun style(): PickerStyle {

        val indicatorColor = MaterialTheme.colorScheme.primaryContainer

        return PickerStyle(
            surfaceColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
            indicatorColor = indicatorColor,
            indicatorShape = RectangleShape,
            selectedItemTextColor = contentColorFor(indicatorColor),
            unselectedItemTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }

    /**
     * Renders the selection indicator box/stripe with the default style colors and shape.
     *
     * @param size Desired size of the indicator container.
     */
    @Composable
    fun SelectionIndicator(size: DpSize) {
        val style = style()
        PickerSelectionIndicator(
            size = size,
            color = style.indicatorColor,
            shape = style.indicatorShape,
        )
    }

}