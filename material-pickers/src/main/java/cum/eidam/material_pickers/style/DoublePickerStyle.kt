package cum.eidam.material_pickers.style

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp

/**
 * Styling definition for a dual / side‑by‑side picker (two coordinated columns / rows).
 *
 * In addition to the base visual tokens inherited from [PickerStyle], this class
 * allows specifying a pair of surface shapes ([surfaceShapes]) so each column can
 * have its own clipping / corner treatment (e.g. start-rounded vs end-rounded)
 * while still sharing the same color & indicator settings.
 *
 * The [textOffset] is interpreted as a horizontal shift applied symmetrically:
 * the first column uses +offset, the second uses -offset (mirrored) when converted
 * to a [SinglePickerStyle] via [asSingle]. This helps visually center aligned content
 * in a composite layout where columns / rows face each other.
 *
 * @property surfaceColor See [PickerStyle.surfaceColor].
 * @property indicatorColor See [PickerStyle.indicatorColor].
 * @property indicatorShape See [PickerStyle.indicatorShape].
 * @property selectedItemTextColor See [PickerStyle.selectedItemTextColor].
 * @property unselectedItemTextColor See [PickerStyle.unselectedItemTextColor].
 * @property surfaceShapes Pair of shapes (first, second) for the two picker columns / rows.
 * @property textOffset Base horizontal / vertical text offset whose sign is flipped for the second column / row.
 */
@Immutable
data class DoublePickerStyle(
    override val surfaceColor: Color,
    override val indicatorColor: Color,
    override val indicatorShape: Shape,
    override val selectedItemTextColor: Color,
    override val unselectedItemTextColor: Color,

    val surfaceShapes: Pair<Shape, Shape>,
    override val textOffset: Dp,
): PickerStyle(
    surfaceColor = surfaceColor,
    indicatorColor = indicatorColor,
    indicatorShape = indicatorShape,
    selectedItemTextColor = selectedItemTextColor,
    unselectedItemTextColor = unselectedItemTextColor
) {

    /**
     * Identifies which half (column / row) of the double picker is being referenced.
     */
    enum class Part {
        First,
        Second
    }

    /**
     * Produces a corresponding [SinglePickerStyle] for the requested [part].
     *
     * The horizontal [textOffset] is applied with a positive sign for [Part.First]
     * and a negative sign for [Part.Second] to keep mirrored alignment. The
     * correct surface shape for the part is also selected from [surfaceShapes].
     */
    fun asSingle(part: Part) = SinglePickerStyle(
        surfaceColor = surfaceColor,
        indicatorColor = indicatorColor,
        indicatorShape = indicatorShape,
        selectedItemTextColor = selectedItemTextColor,
        unselectedItemTextColor = unselectedItemTextColor,
        textOffset = when (part) {
            Part.First -> textOffset
            Part.Second -> -textOffset
        },
        surfaceShape = when (part) {
            Part.First -> surfaceShapes.first
            Part.Second -> surfaceShapes.second
        }
    )
}
