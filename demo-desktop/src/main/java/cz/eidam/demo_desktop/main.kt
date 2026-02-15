package cz.eidam.demo_desktop

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import cz.eidam.material_pickers.DoubleHorizontalPicker
import cz.eidam.material_pickers.DoubleVerticalPicker
import cz.eidam.material_pickers.HorizontalPicker
import cz.eidam.material_pickers.VerticalPicker

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}

@Composable
fun App() {
    Scaffold(Modifier.fillMaxSize()) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                modifier = Modifier.padding(12.dp),
                text = "Hello, Desktop!",
                style = MaterialTheme.typography.headlineMedium
            )

            val itemSize = 50.dp

            val letters = ('A'..'H').map { "$it" }
            val numbers = (1..8).map { "$it" }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                var value1 by remember { mutableStateOf(0) }
                VerticalPicker(
                    modifier = Modifier.height(itemSize * 3).width(itemSize),
                    items = letters,
                    selectedIndex = value1,
                    onItemSelected = { value1 = it }
                )

                var value2 by remember { mutableStateOf(0) }
                var value3 by remember { mutableStateOf(0) }

                DoubleVerticalPicker(
                    modifier = Modifier.height(itemSize * 3).width(itemSize * 2),
                    itemsLeft = letters,
                    selectedIndexLeft = value2,
                    onSelectedIndexChangeLeft = { value2 = it },
                    itemsRight = numbers,
                    selectedIndexRight = value3,
                    onSelectedIndexChangeRight = { value3 = it },
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                var value1 by remember { mutableStateOf(0) }
                HorizontalPicker(
                    modifier = Modifier.height(itemSize).width(itemSize * 3),
                    items = letters,
                    selectedIndex = value1,
                    onItemSelected = { value1 = it }
                )

                var value2 by remember { mutableStateOf(0) }
                var value3 by remember { mutableStateOf(0) }

                DoubleHorizontalPicker(
                    modifier = Modifier.height(itemSize * 2).width(itemSize * 3),
                    itemsTop = letters,
                    selectedIndexTop = value2,
                    onSelectedIndexChangeTop = { value2 = it },
                    itemsBottom = numbers,
                    selectedIndexBottom = value3,
                    onSelectedIndexChangeBottom = { value3 = it },
                )
            }
        }
    }
}