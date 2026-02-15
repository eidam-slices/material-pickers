package cz.eidam.material_pickers

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntSize

/**
 * Snapshot of sizing & orientation derived values for a picker instance.
 *
 * The picker measures / computes a single logical item size in both dp ([itemSizeDp]) and px
 * ([itemSizePx]). From these, this helper exposes main-axis vs cross-axis dimensions depending
 * on [orientation] and constructs the content [padding] used by the lazy list.
 *
 * Padding strategy:
 * - We pad both start & end (or top & bottom) by exactly one item size so that when the list
 *   scrolls, one item can rest perfectly centered between the paddings (classic wheel effect).
 *
 * All properties are immutable; create a new instance when measurements change.
 *
 * @property orientation Scroll direction of the picker.
 * @property itemSizeDp Size of one item cell in dp (width x height as measured or derived).
 * @property itemSizePx Size of one item cell in raw pixels.
 */
@Immutable
data class PickerProperties(
    val orientation: Orientation,
    val itemSizeDp: DpSize,
    val itemSizePx: IntSize
) {

    /** Main-axis size in pixels (height for vertical, width for horizontal). */
    val mainAxisSizePx get() = when(orientation) {
        Orientation.Horizontal -> itemSizePx.width
        Orientation.Vertical -> itemSizePx.height
    }
    /** Main-axis size in dp. */
    val mainAxisSizeDp get() = when(orientation) {
        Orientation.Horizontal -> itemSizeDp.width
        Orientation.Vertical -> itemSizeDp.height
    }

    /** Cross-axis size in pixels (perpendicular to scroll). */
    val crossAxisSizePx get() = when(orientation) {
        Orientation.Horizontal -> itemSizePx.height
        Orientation.Vertical -> itemSizePx.width
    }
    /** Cross-axis size in dp. */
    val crossAxisSizeDp get() = when(orientation) {
        Orientation.Horizontal -> itemSizeDp.height
        Orientation.Vertical -> itemSizeDp.width
    }

    /** Padding values adding exactly one main-axis item size to both ends to center the active item. */
    val padding = when(orientation) {
        Orientation.Vertical -> PaddingValues(vertical = mainAxisSizeDp)
        Orientation.Horizontal -> PaddingValues(horizontal = mainAxisSizeDp)
    }

}
