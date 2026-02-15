package cz.eidam.material_pickers.picker_parts

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.util.VelocityTracker
import cz.eidam.material_pickers.PickerProperties
import cz.eidam.material_pickers.Platform
import cz.eidam.material_pickers.getPlatform
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

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


    val state = rememberPickerListState(listState, properties.orientation)

    when (properties.orientation) {
        Orientation.Horizontal -> LazyRow(
            modifier = Modifier.pickerGestures(state),
            state = listState,
            flingBehavior = state.flingBehavior,
            contentPadding = properties.padding,
            content = content
        )

        Orientation.Vertical -> LazyColumn(
            modifier = Modifier.pickerGestures(state),
            state = listState,
            flingBehavior = state.flingBehavior,
            contentPadding = properties.padding,
            content = content
        )
    }
}

@Stable
internal class PickerListState(
    val listState: LazyListState,
    val orientation: Orientation,
    val flingBehavior: FlingBehavior,
)

@Composable
internal fun rememberPickerListState(
    listState: LazyListState,
    orientation: Orientation,
): PickerListState {

    val snapFlingBehavior = rememberSnapFlingBehavior(listState)

    return remember {
        PickerListState(
            listState,
            orientation,
            snapFlingBehavior
        )
    }
}

internal fun Modifier.pickerGestures(
    state: PickerListState,
): Modifier = composed {

    if (getPlatform() != Platform.Desktop) return@composed this

    val scope = rememberCoroutineScope()
    pointerInput(state.orientation) {
        val velocityTracker = VelocityTracker()

        detectDragGestures(
            onDragStart = { velocityTracker.resetTracking() },
            onDrag = { change, amount ->
                change.consume()
                velocityTracker.addPosition(change.uptimeMillis, change.position)

                val delta = if (state.orientation == Orientation.Vertical) {
                    -amount.y
                } else {
                    -amount.x
                }

                state.listState.dispatchRawDelta(delta)
            },
            onDragEnd = {
                val velocity = velocityTracker.calculateVelocity()
                var initialVelocity = if (state.orientation == Orientation.Vertical) {
                    -velocity.y
                } else {
                    -velocity.x
                }

                val velocityDampening = 0.55f
                initialVelocity *= velocityDampening

                val minVelocityThreshold = 400f
                if (initialVelocity.absoluteValue < minVelocityThreshold) {
                    initialVelocity = 0f
                }

                val maxVelocity = 3000f
                initialVelocity = initialVelocity.coerceIn(-maxVelocity, maxVelocity)


                scope.launch {
                    state.listState.scroll {
                        with(state.flingBehavior) {
                            performFling(initialVelocity)
                        }
                    }
                }
            },
            onDragCancel = {
                scope.launch {
                    state.listState.scroll {
                        with(state.flingBehavior) {
                            performFling(0f)
                        }
                    }
                }

            }
        )

    }


}