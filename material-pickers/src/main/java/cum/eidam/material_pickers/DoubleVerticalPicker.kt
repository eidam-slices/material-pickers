package cum.eidam.material_pickers

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <TL, TR> DoubleVerticalPicker(
    modifier: Modifier = Modifier,

    itemsLeft: List<TL>,
    valueLeft: TL,
    onValueChangeLeft: (TL) -> Unit,

    itemsRight: List<TR>,
    valueRight: TR,
    onValueChangeRight: (TR) -> Unit,
) {

    val shapeLeft = RoundedCornerShape(
        topStart = MaterialTheme.shapes.large.topStart,
        bottomStart = MaterialTheme.shapes.large.bottomStart,
        topEnd = CornerSize(0.dp),
        bottomEnd = CornerSize(0.dp)
    )
    val shapeRight = RoundedCornerShape(
        topStart = CornerSize(0.dp),
        bottomStart = CornerSize(0.dp),
        topEnd = MaterialTheme.shapes.large.topEnd,
        bottomEnd = MaterialTheme.shapes.large.bottomEnd,
    )

    val textOffsetCenter = 8.dp

    val pickerWidth = 56.dp - textOffsetCenter

    Row {
        VerticalPicker(
            items = itemsLeft,
            value = valueLeft,
            onValueChange = onValueChangeLeft,
            shape = shapeLeft,
            textOffset = textOffsetCenter,
            itemWidth = pickerWidth
        )

        VerticalPicker(
            items = itemsRight,
            value = valueRight,
            onValueChange = onValueChangeRight,
            shape = shapeRight,
            textOffset = -textOffsetCenter,
            itemWidth = pickerWidth
        )
    }

}