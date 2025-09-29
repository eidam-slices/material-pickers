package cum.eidam.material_pickers.style

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp

/**
 * Styling definition for a single (stand‑alone) picker column.
 *
 * Extends [PickerStyle] by adding the container [surfaceShape] that frames the
 * entire picker (e.g. a rounded rectangle). The rest of the visual tokens are
 * inherited from the base style.
 *
 * Use [cum.eidam.material_pickers.defaults.SinglePickerDefaults.style] to obtain a
 * theme‑aware default instance, or create your own to fully customize visuals.
 *
 * @property surfaceColor See [PickerStyle.surfaceColor].
 * @property indicatorColor See [PickerStyle.indicatorColor].
 * @property indicatorShape See [PickerStyle.indicatorShape].
 * @property selectedItemTextColor See [PickerStyle.selectedItemTextColor].
 * @property unselectedItemTextColor See [PickerStyle.unselectedItemTextColor].
 * @property textOffset See [PickerStyle.textOffset].
 * @property surfaceShape Shape of the outer picker surface (clipping + background).
 */
@Immutable
data class SinglePickerStyle(
    override val surfaceColor: Color,
    override val indicatorColor: Color,
    override val indicatorShape: Shape,
    override val selectedItemTextColor: Color,
    override val unselectedItemTextColor: Color,
    override val textOffset: Dp,

    val surfaceShape: Shape,
): PickerStyle(
    surfaceColor = surfaceColor,
    indicatorColor = indicatorColor,
    indicatorShape = indicatorShape,
    selectedItemTextColor = selectedItemTextColor,
    unselectedItemTextColor = unselectedItemTextColor,
    textOffset = textOffset
)