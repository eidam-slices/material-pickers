package cum.eidam.material_pickers_demo

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cum.eidam.material_pickers.HorizontalPicker
import cum.eidam.material_pickers.VerticalPicker

@Composable
fun SinglePickersDemoScreen(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit = { _ -> },
) {
    val context = LocalContext.current


    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Spacer(Modifier.height(48.dp))

        val items = remember { List(26) { it.toString() } }
        var verticalValue by remember { mutableStateOf(items.first()) }
        var horizontalValue by remember { mutableStateOf(items.first()) }

        VerticalPicker(
            items = items,
            value = verticalValue,
            onValueChange = { verticalValue = it },
        )

        Spacer(Modifier.height(24.dp))

        HorizontalPicker(
            items = items,
            value = horizontalValue,
            onValueChange = { horizontalValue = it },
        )

        Spacer(Modifier.height(36.dp))


        var textValue by remember { mutableStateOf("") }
        TextField(
            value = textValue,
            onValueChange = {
                textValue = it
            },
            modifier = Modifier.padding(horizontal = 56.dp),
            label = { Text("Enter valid value for pickers") }
        )

        TextButton(
            onClick = {
                if (items.contains(textValue)) {
                    verticalValue = textValue
                    horizontalValue = textValue
                }
                else {
                    Toast.makeText(context, "Value not found in items", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text("Change Pickers Value")
        }


        Spacer(Modifier.weight(1f))


        FilledTonalButton(
            modifier = Modifier.padding(vertical = 4.dp),
            onClick = { onNavigate("double") }
        ) { Text("Double Pickers Demo") }
    }

}