package cum.eidam.material_pickers_demo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
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
    onNavigate: (String) -> Unit = { _ -> }
) {


    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(24.dp))

        val itemsA = listOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J")
        val itemsB = List(20) { it + 1 }


        var valueLeft by remember { mutableStateOf(itemsA.first()) }
        var valueRight by remember { mutableIntStateOf(itemsB.first()) }

        var valueTop by remember { mutableStateOf(itemsA.first()) }
        var valueBottom by remember { mutableIntStateOf(itemsB.first()) }


        Text(
            text = "Double Vertical Picker",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(vertical = 4.dp)
        )
        DoubleVerticalPicker(
            itemsLeft = itemsA,
            valueLeft = valueLeft,
            onValueChangeLeft = { valueLeft = it },

            itemsRight = itemsB,
            valueRight = valueRight,
            onValueChangeRight = { valueRight = it },
        )

        Spacer(Modifier.height(48.dp))

        Text(
            text = "Double Horizontal Picker",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        DoubleHorizontalPicker(
            itemsTop = itemsA,
            valueTop = valueTop,
            onValueChangeTop = { valueTop = it },

            itemsBottom = itemsB,
            valueBottom = valueBottom,
            onValueChangeBottom = { valueBottom = it },
        )

        Spacer(Modifier.weight(1f))


        FilledTonalButton(
            modifier = Modifier.padding(vertical = 4.dp),
            onClick = { onNavigate("single") },
        ) {
            Text("Go Back")
        }
    }

}