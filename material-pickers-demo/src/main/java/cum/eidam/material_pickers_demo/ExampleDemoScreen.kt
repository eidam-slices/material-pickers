package cum.eidam.material_pickers_demo

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cum.eidam.material_pickers.DoubleVerticalPicker
import cum.eidam.material_pickers.HorizontalPicker
import cum.eidam.material_pickers.VerticalPicker

@Composable
fun ExampleDemoScreen(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit = { _ -> },
) {

    val context = LocalContext.current

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        // list of items (T) for picker(s)
        val exampleItems = List(26) { "$it" }

        var verticalValue by remember { mutableStateOf(exampleItems.first()) }
        var horizontalValue by remember { mutableStateOf(exampleItems.first()) }


        var valueLeft by remember { mutableStateOf(exampleItems.first()) }
        var valueRight by remember { mutableStateOf(exampleItems.first()) }


        Spacer(Modifier.height(36.dp))

        var number by remember { mutableStateOf("") }
        OutlinedTextField(
            value = number,
            onValueChange = { number = it },
            label = { Text("Enter valid value for pickers") }
        )

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = {
                if (exampleItems.contains(number)) {
                    verticalValue = number
                    horizontalValue = number

                    valueLeft = number
                    valueRight = number


                }
                else {
                    Toast.makeText(
                        context,
                        "This value is not in the picker items",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        ) {
            Text("Change Picker Values")
        }

        Spacer(Modifier.height(36.dp))


        VerticalPicker(
            items = exampleItems,
            value = verticalValue,
            onValueChange = { verticalValue = it },
        )

        Spacer(Modifier.height(12.dp))

        HorizontalPicker(
            items = exampleItems,
            value = horizontalValue,
            onValueChange = { horizontalValue = it },
        )


        Spacer(Modifier.height(24.dp))



        DoubleVerticalPicker(
            itemsLeft = exampleItems,
            valueLeft = valueLeft,
            onValueChangeLeft = { valueLeft = it },

            itemsRight = exampleItems,
            valueRight = valueRight,
            onValueChangeRight = { valueRight = it },
        )


    }
}