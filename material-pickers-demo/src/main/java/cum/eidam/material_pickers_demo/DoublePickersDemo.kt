package cum.eidam.material_pickers_demo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cum.eidam.material_pickers.DoubleHorizontalPicker
import cum.eidam.material_pickers.DoubleVerticalPicker

@Composable
fun DoublePickersDemoScreen(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit = { _ -> },
) {


    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(48.dp))

        val sharedItems1 = remember { List(25) { (it + 1).toString() } }
        val sharedItems2 = remember { ('A'..'Z').map { it.toString() } }

        var verticalLeftIndex by remember { mutableIntStateOf(0) }
        var verticalRightIndex by remember { mutableIntStateOf(0) }
        DoubleVerticalPicker(
            itemsLeft = sharedItems1,
            selectedIndexLeft = verticalLeftIndex,
            onSelectedIndexChangeLeft = { verticalLeftIndex = it },
            itemsRight = sharedItems2,
            selectedIndexRight = verticalRightIndex,
            onSelectedIndexChangeRight = { verticalRightIndex = it },
            modifier = Modifier
                .height(128.dp)
                .width(96.dp)
        )

        Spacer(Modifier.height(16.dp))


        var horizontalTopIndex by remember { mutableIntStateOf(0) }
        var horizontalBottomIndex by remember { mutableIntStateOf(0) }

        DoubleHorizontalPicker(
            itemsTop = sharedItems1,
            selectedIndexTop = horizontalTopIndex,
            onSelectedIndexChangeTop = { horizontalTopIndex = it },
            itemsBottom = sharedItems2,
            selectedIndexBottom = horizontalBottomIndex,
            onSelectedIndexChangeBottom = { horizontalBottomIndex = it },
            modifier = Modifier
                .height(96.dp)
                .width(128.dp)
        )




        Spacer(Modifier.weight(1f))

        ExtendedFloatingActionButton(
            onClick = {
                onNavigate("single")
            }
        ) { Text("Go back") }

        Spacer(Modifier.height(12.dp))
    }

}