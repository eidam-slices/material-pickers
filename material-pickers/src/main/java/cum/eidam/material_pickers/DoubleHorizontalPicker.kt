package cum.eidam.material_pickers

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp

@Composable
fun <TT, TB> DoubleHorizontalPicker(
    itemsTop: List<TT>,
    valueTop: TT,
    onValueChangeTop: (TT) -> Unit,

    itemsBottom: List<TB>,
    valueBottom: TB,
    onValueChangeBottom: (TB) -> Unit,
) {

    val shapeTop = MaterialTheme.shapes.large.copy(
        bottomStart = CornerSize(0.dp),
        bottomEnd = CornerSize(0.dp),
    )
    val shapeBottom = MaterialTheme.shapes.large.copy(
        topStart = CornerSize(0.dp),
        topEnd = CornerSize(0.dp),
    )

    val textOffset = remember { 6.dp }

    val pickerHeight = remember { 46.dp - textOffset }


    HorizontalPicker(
        items = itemsTop,
        value = valueTop,
        onValueChange = onValueChangeTop,
        shape = shapeTop,
        textOffset = textOffset,
        itemHeight = pickerHeight
    )

    HorizontalPicker(
        items = itemsBottom,
        value = valueBottom,
        onValueChange = onValueChangeBottom,
        shape = shapeBottom,
        textOffset = -textOffset,
        itemHeight = pickerHeight
    )

}