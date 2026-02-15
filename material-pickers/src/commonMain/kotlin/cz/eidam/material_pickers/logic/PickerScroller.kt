package cz.eidam.material_pickers.logic

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TargetBasedAnimation
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.withFrameNanos
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/**
 * Imperative scroller utility for wheel / picker components backed by a [LazyListState].
 *
 * Provides smooth animated scrolling to a target logical item index while maintaining
 * awareness of externally initiated scrolls (user gestures or other programmatic calls).
 * When an external scroll is triggered via [externallyScrollToItem], a callback can inform
 * higher layers so they can, for example, debounce further commands or show a transient UI state.
 *
 * Scrolling model:
 * - Computes absolute pixel positions using a fixed [itemBoxSizePx] per item.
 * - Centers the target item by offsetting with half of the overall viewport size
 *   (derived from [itemsVisible] * [itemBoxSizePx]).
 * - Animates the delta using a Material-like easing ([LinearOutSlowInEasing]) over
 *   [SCROLL_DURATION_MS] milliseconds.
 * - Cancels any in-flight animation before starting a new one (coalescing behavior).
 *
 * Threading / lifecycle:
 * - All animations run inside the provided [coroutineScope].
 * - Cancellation of the parent scope cancels active animations safely.
 *
 * Limitations / assumptions:
 * - Assumes uniform item size (fixed height/width depending on orientation).
 * - Does not clamp [itemIndex]; caller should ensure valid target indices.
 *
 * @param itemsVisible Number of item slots visible; used to compute centering offset.
 * @param listState Backing lazy list state to read and mutate scroll position.
 * @param coroutineScope Scope in which animations are launched.
 * @param itemBoxSizePx Pixel size of one item container (main axis dimension).
 * @param onExternalScrollInProgress Optional listener toggled true when an external programmatic
 * scroll starts via [externallyScrollToItem] and false when the animation finishes.
 */
class PickerScroller(
    private val itemsVisible: Int,
    private val listState: LazyListState,
    private val coroutineScope: CoroutineScope,

    private val itemBoxSizePx: Int,

    private val onExternalScrollInProgress: ((Boolean) -> Unit)? = null,
) {

    companion object {
        /** Duration (ms) used for the animated scroll tween. */
        const val SCROLL_DURATION_MS = 300
    }

    // variable for callback on external scroll in progress
    private var isExternallyScrolling: Boolean = false

    // job for scroll to be cancelable
    private var scrollJob: Job? = null

    /**
     * Smoothly scrolls so that the item at [itemIndex] ends centered in the picker viewport.
     * If an existing animation is running it is cancelled first (ensuring only one active).
     */
    fun scrollToItem(itemIndex: Int) {
        coroutineScope.launch {

            scrollJob?.cancelAndJoin()

            scrollJob = launch {
                try {
                    val allItemsSizePx = itemBoxSizePx * itemsVisible

                    val currentIndex = listState.firstVisibleItemIndex

                    // Center pixel offset for the viewport
                    val centerOffsetPx = (allItemsSizePx / 2f - itemBoxSizePx / 2f)
                    val currentOffsetPx = listState.firstVisibleItemScrollOffset

                    val currentScrollPositionPx = currentIndex * itemBoxSizePx + currentOffsetPx

                    val targetScrollPositionPx = itemBoxSizePx + (itemIndex * itemBoxSizePx) - centerOffsetPx

                    val deltaPositionPx = targetScrollPositionPx - currentScrollPositionPx
                    if (deltaPositionPx == 0f) return@launch

                    val animationSpec = tween<Float>(
                        durationMillis = SCROLL_DURATION_MS,
                        easing = LinearOutSlowInEasing
                    )

                    val animation = TargetBasedAnimation(
                        animationSpec = animationSpec,
                        typeConverter = Float.VectorConverter,
                        initialValue = 0f,
                        targetValue = deltaPositionPx
                    )

                    val startTime = withFrameNanos { it }
                    var lastValue = 0f

                    do {
                        val playTime = withFrameNanos { it } - startTime

                        val value = animation.getValueFromNanos(playTime)

                        val scrollProgressDelta = value - lastValue
                        listState.scrollBy(scrollProgressDelta)
                        lastValue = value
                    }
                    while (!animation.isFinishedFromNanos(playTime))


                } finally {
                    // reset scroll job
                    scrollJob = null

                    if (isActive) {
                        // if an external scroll was in progress, reset
                        if (isExternallyScrolling) {
                            isExternallyScrolling = false
                            onExternalScrollInProgress?.invoke(false)
                        }
                    }

                }


            }
        }

    }


    /**
     * Initiates a programmatic scroll marking it as an "external" one so observers can react.
     * Simply sets the flag, invokes the listener with true, and delegates to [scrollToItem].
     */
    fun externallyScrollToItem(itemIndex: Int) {
        isExternallyScrolling = true
        onExternalScrollInProgress?.invoke(true)
        scrollToItem(itemIndex)
    }

}