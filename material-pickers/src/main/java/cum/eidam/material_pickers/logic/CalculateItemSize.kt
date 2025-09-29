package cum.eidam.material_pickers.logic

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntSize

/**
 * Computes the logical pixel size (width & height) of a single picker item cell based on the
 * provided layout [constraints], picker [orientation] and the number of items simultaneously
 * visible in the viewport ([itemsVisible]).
 *
 * Logic:
 * - For a vertical picker: height is containerHeight / itemsVisible, width spans full container.
 * - For a horizontal picker: width is containerWidth / itemsVisible, height spans full container.
 * - If a dimension is unbounded (== [Constraints.Infinity]) we return 0 for the corresponding
 *   item dimension so that upstream measurement can supply / constrain it later.
 *
 * Note: A previous implementation mistakenly checked the opposite container dimension when
 * guarding against [Constraints.Infinity]; this has been corrected.
 */
internal fun calculateItemSize(
    orientation: Orientation,
    constraints: Constraints,
    itemsVisible: Int,
): IntSize {
    val containerWidth = constraints.maxWidth
    val containerHeight = constraints.maxHeight

    //* DIMENSION CALCULATION:
    val itemHeightPx = if (containerWidth != Constraints.Infinity) {
        if (orientation == Orientation.Vertical) containerHeight / itemsVisible
        else containerHeight
    }
    else 0

    val itemWidthPx = if (containerHeight != Constraints.Infinity) {
        if (orientation == Orientation.Horizontal) containerWidth / itemsVisible
        else containerWidth
    }
    else 0

    return IntSize(width = itemWidthPx, height = itemHeightPx)
}