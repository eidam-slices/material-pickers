package cz.eidam.material_pickers

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import cz.eidam.material_pickers.defaults.PickerDefaults
import cz.eidam.material_pickers.defaults.SinglePickerDefaults
import cz.eidam.material_pickers.picker_parts.PickerItem
import cz.eidam.material_pickers.style.SinglePickerStyle

/**
 * Vertical wheel-style picker allowing users to choose an item by scrolling.
 *
 * This overload is index-driven and therefore works with non‑unique item values (duplicates are allowed).
 * The caller supplies the current [selectedIndex] and is notified via [onItemSelected] when the
 * centered (highlighted) index changes due to user scroll or programmatic animation.
 *
 * Requires an odd [itemsVisible] count so a single item can be perfectly centered.
 *
 * Styling:
 * - Visual aspects (surface, indicator, text colors, spacing offset) come from [style].*
 *
 * Performance:
 * - Only visible items are composed (LazyList). Scrolling animations are handled by a lightweight scroller.
 *
 * @param T Type of the items (converted to string via toString for display).
 * @param items List of items to display.
 * @param selectedIndex Currently selected index (must be within [items] bounds; out-of-range values are ignored for scrolling but not enforced here).
 * @param onItemSelected Callback with the new selected index after scroll settles.
 * @param modifier Optional layout modifier.
 * @param style Style tokens (colors, shapes, text offsets) for rendering.
 * @param itemsVisible Odd number of visible rows (default = [SinglePickerDefaults.ITEMS_VISIBLE]).
 *
 * @throws IllegalArgumentException if [itemsVisible] is not odd.
 */
@Composable
fun <T> VerticalPicker(
    items: List<T>,
    selectedIndex: Int,
    onItemSelected: (index: Int) -> Unit,
    modifier: Modifier = Modifier,

    style: SinglePickerStyle = SinglePickerDefaults.style(),

    itemsVisible: Int = SinglePickerDefaults.ITEMS_VISIBLE,
) {

    require(itemsVisible % 2 == 1) { "itemsVisible must be an odd number to have a centered selection" }

    GenericPicker(
        modifier = modifier,
        items = items,
        selectedIndex = selectedIndex,
        onItemSelected = onItemSelected,
        orientation = Orientation.Vertical,
        itemsVisible = itemsVisible,
        item = {
            PickerItem(
                modifier = Modifier
                    .width(it.size.width)
                    .height(it.size.height),
                selected = it.selected,
                label = it.label,
                onClick = it.onClick,
                selectedTextColor = style.selectedItemTextColor,
                unselectedTextColor = style.unselectedItemTextColor,
                offset = DpOffset(x = style.textOffset, y = 0.dp)
            )
        },
        selectionIndicator = { size ->
            PickerDefaults.SelectionIndicator(size)
        },

        background = {
            Surface(
                shape = style.surfaceShape,
                color = style.surfaceColor,
                content = {}
            )
        }
    )

}


/**
 * Convenience overload of [VerticalPicker] operating on value identity instead of index.
 *
 * This variant enforces that all [items] are unique (using equality). It maps the provided
 * [selectedItem] to its index and delegates to the index-based picker. Use this when you prefer
 * working with domain objects directly. If your list can contain duplicates, use the index-based
 * overload instead to avoid ambiguity.
 *
 * @param T Type of the items (must provide meaningful equality for uniqueness check).
 * @param items List of UNIQUE items.
 * @param selectedItem Currently selected item (must be a member of [items]).
 * @param onItemSelected Callback with the newly selected item.
 * @param modifier Optional layout modifier.
 * @param style Style tokens (colors, shapes, text offsets) for rendering.
 * @param itemsVisible Odd number of visible rows.
 *
 * @throws IllegalArgumentException if [itemsVisible] is not odd.
 * @throws IllegalArgumentException if [items] are not unique.
 */
@Composable
fun <T> VerticalPicker(
    items: List<T>,
    selectedItem: T,
    onItemSelected: (T) -> Unit,

    modifier: Modifier = Modifier,

    style: SinglePickerStyle = SinglePickerDefaults.style(),
    itemsVisible: Int = SinglePickerDefaults.ITEMS_VISIBLE,
) {
    require(itemsVisible % 2 == 1) { "itemsVisible must be an odd number to have a centered selection" }
    require(items.distinct().size == items.size) { "Items for this variant must be unique. Use the index-based overload if you need duplicate items." }

    VerticalPicker(
        items = items,
        selectedIndex = items.indexOf(selectedItem),
        onItemSelected = { onItemSelected(items[it]) },
        modifier = modifier,
        style = style,
        itemsVisible = itemsVisible,
    )

}
