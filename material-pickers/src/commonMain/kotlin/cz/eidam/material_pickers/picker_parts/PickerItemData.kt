package cz.eidam.material_pickers.picker_parts

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.DpSize

/**
 * Immutable model describing a single picker entry used by higher-level list builders.
 *
 * Acts as a lightweight view-model carrying both presentation state ([label], [selected], [size])
 * and a user interaction callback ([onClick]). This separation allows list composables to remain
 * stateless and easily testable.
 *
 * @param label Text displayed for this picker option.
 * @param selected Whether this item is currently the active / centered selection.
 * @param size Intended rendered size of the item container (used for layout decisions upstream).
 * @param onClick Invoked when the user taps/clicks the item (usually triggers scroll to center).
 */
@Immutable
data class PickerItemData(
    val label: String,
    val selected: Boolean,
    val size: DpSize,
    val onClick: () -> Unit,
)
