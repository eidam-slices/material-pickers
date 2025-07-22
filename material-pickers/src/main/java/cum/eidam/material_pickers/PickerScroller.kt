package cum.eidam.material_pickers

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TargetBasedAnimation
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class PickerScroller(
    private val itemsVisible: Int,
    private val listState: LazyListState,
    private val coroutineScope: CoroutineScope,

    private val density: Density,
    private val itemBoxSize: Dp,

    private val onExternalScrollInProgress: ((Boolean) -> Unit)? = null,
) {

    companion object {
        const val SCROLL_DURATION_MS = 300
    }

    // variable for callback on external scroll in progress
    private var isExternallyScrolling: Boolean = false

    // job for scroll to be cancelable
    private var scrollJob: Job? = null

    fun scrollToItem(itemIndex: Int) {
        coroutineScope.launch {

            scrollJob?.cancelAndJoin()

            scrollJob = launch {
                try {
                    val itemBoxSizePx = with(density) { itemBoxSize.toPx() }
                    val allItemsSizePx = itemBoxSizePx * itemsVisible

                    val currentIndex = listState.firstVisibleItemIndex

                    // TODO: make the calc more easy
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


    fun externallyScrollToItem(itemIndex: Int) {
        isExternallyScrolling = true
        onExternalScrollInProgress?.invoke(true)
        scrollToItem(itemIndex)
    }

}