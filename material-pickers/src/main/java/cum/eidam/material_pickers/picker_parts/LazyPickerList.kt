package cum.eidam.material_pickers.picker_parts

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import cum.eidam.material_pickers.PickerProperties

/**
 * Internal abstraction that chooses a [LazyRow] or [LazyColumn] based on the
 * picker orientation, wiring consistent snap fling behavior and
 * passing through caller provided [content].
 *
 * Snap behavior ensures items settle with an item precisely aligned after a
 * fling gesture, producing the classic wheel picker feel.
 *
 * Padding & other layout concerns are pulled from [properties] so that the
 * calling higher level picker only needs to calculate them once.
 *
 * @param listState Shared list state controlling/observing scroll position.
 * @param properties Picker configuration providing orientation and content padding.
 * @param content Lambda where the caller emits item slots (mirrors standard LazyListScope DSL).
 */
@Composable
internal fun LazyPickerList(
    listState: LazyListState,
    properties: PickerProperties,
    content: LazyListScope.() -> Unit,
) {

    when (properties.orientation) {
        Orientation.Horizontal -> LazyRow(
            state = listState,
            flingBehavior = rememberSnapFlingBehavior(listState),
            contentPadding = properties.padding,
            content = content
        )

        Orientation.Vertical -> LazyColumn(
            state = listState,
            flingBehavior = rememberSnapFlingBehavior(listState),
            contentPadding = properties.padding,
            content = content
        )
    }

}
