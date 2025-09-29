package cum.eidam.material_pickers_demo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cum.eidam.material_pickers.HorizontalPicker
import cum.eidam.material_pickers.VerticalPicker
import org.w3c.dom.Text


@Composable
fun SinglePickersDemoScreen(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit = { _ -> },
) {


    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val sharedItems = remember { List(25) { (it + 1).toString() } }

        var verticalIndex by remember { mutableIntStateOf(0) }
        var horizontalIndex by remember { mutableIntStateOf(0) }

        Spacer(Modifier.height(48.dp))

        VerticalPicker(
            items = sharedItems,
            selectedIndex = verticalIndex,
            onItemSelected = { verticalIndex = it },
            modifier = Modifier
                .width(52.dp)
                .height(128.dp)
        )
        Spacer(Modifier.height(4.dp))

        Text(
            text = "Vertical Picker",
            style = MaterialTheme.typography.labelMedium,
        )

        Spacer(Modifier.height(16.dp))

        HorizontalPicker(
            items = sharedItems,
            selectedIndex = horizontalIndex,
            onItemSelected = { horizontalIndex = it },
            modifier = Modifier
                .width(128.dp)
                .height(52.dp)
        )
        Spacer(Modifier.height(4.dp))

        Text(
            text = "Horizontal Picker",
            style = MaterialTheme.typography.labelMedium,
        )

        Spacer(Modifier.height(32.dp))



        Row{
            Button(
                onClick = {
                    verticalIndex = (verticalIndex - 1).coerceIn(sharedItems.indices)
                    horizontalIndex = (horizontalIndex - 1).coerceIn(sharedItems.indices)
                },
                enabled = verticalIndex != sharedItems.indices.first
            ) { Text("-") }

            Spacer(Modifier.width(4.dp))

            Button(
                onClick = {
                    verticalIndex = (verticalIndex + 1).coerceIn(sharedItems.indices)
                    horizontalIndex = (horizontalIndex + 1).coerceIn(sharedItems.indices)

                },
                enabled = verticalIndex != sharedItems.indices.last

            ) { Text("+") }

        }

        Spacer(Modifier.weight(1f))


        ExtendedFloatingActionButton(
            onClick = {
                onNavigate("double")
            }
        ) {
            Text("Go to Double Pickers Demo")
        }

        Spacer(Modifier.height(12.dp))



    }


}
