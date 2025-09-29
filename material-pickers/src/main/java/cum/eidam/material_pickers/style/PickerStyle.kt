package cum.eidam.material_pickers.style

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Base styling contract for picker composables.
 *
 * This open class encapsulates the visual tokens (colors, shapes, spacing) that
 * define how a picker renders its container surface, the selection indicator and
 * the textual content (selected vs. unselected items).
 *
 * You normally don't instantiate [PickerStyle] directly; instead use one of the
 * concrete data classes such as [SinglePickerStyle] or [DoublePickerStyle], or obtain
 * a preconfigured instance from the defaults helpers (e.g. [cum.eidam.material_pickers.defaults.PickerDefaults.style]).
 *
 * All color & shape values are kept immutable (val) so a style instance can be safely
 * reused across recompositions without unintended mutations.
 *
 * @property surfaceColor Color of the scrollable surface / background behind the items.
 * @property indicatorColor Color used to draw the selection indicator (highlight stripe / box).
 * @property indicatorShape Shape of the selection indicator.
 * @property selectedItemTextColor Text color applied to the currently selected item line.
 * @property unselectedItemTextColor Text color applied to the non‑selected, faded items.
 * @property textOffset Optional horizontal text offset (positive = shift right, negative = shift left).
 */
@Stable
open class PickerStyle(
    open val surfaceColor: Color,
    open val indicatorColor: Color,
    open val indicatorShape: Shape,
    open val selectedItemTextColor: Color,
    open val unselectedItemTextColor: Color,
    open val textOffset: Dp = 0.dp,
)