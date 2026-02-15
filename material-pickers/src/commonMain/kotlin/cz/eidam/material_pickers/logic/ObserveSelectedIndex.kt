package cz.eidam.material_pickers.logic

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

/**
 * Emits the currently "selected" item index of a picker backed by a [LazyListState].
 *
 * Selection heuristic:
 * - Takes the first visible item index and its scroll offset.
 * - If the leading item has been scrolled past half of its pixel size ([itemSizePx]/2), the
 *   next item is considered selected (index + 1).
 * - Applies an additional constant [centerItemIndexOffset] which accounts for any virtual or
 *   padded items the caller conceptually places before the real data list in order to center
 *   the selection highlight.
 *
 * The resulting stream is de-duplicated via [distinctUntilChanged] so downstream collectors only
 * react to actual index changes.
 *
 * Performance: Uses [snapshotFlow] so it only re-evaluates during state changes that Compose
 * observes; safe to collect in a Coroutine scope tied to composition.
 *
 * @param itemSizePx Exact pixel size of one item slot (height if vertical list, width if horizontal).
 * @param centerItemIndexOffset Logical offset added to bring the computed index in sync with the
 * visual center reference (often (itemsVisible/2 - 1)).
 *
 * @return A cold [Flow] emitting stable selected indices.
 */
internal fun LazyListState.observeSelectedIndex(
    itemSizePx: Int,
    centerItemIndexOffset: Int,
): Flow<Int> = snapshotFlow {
    firstVisibleItemIndex to firstVisibleItemScrollOffset
}.map { (index, offset) ->
    val halfItemPx = itemSizePx / 2
    val indexOffset = if (offset > halfItemPx) 1 else 0
    index + indexOffset + centerItemIndexOffset
}.distinctUntilChanged()
