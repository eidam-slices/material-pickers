package cum.eidam.material_pickers_demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cum.eidam.material_pickers_demo.ui.theme.MaterialPickersTheme

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            var selectedScreenKey by remember { mutableStateOf("single") }

            MaterialPickersTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    val modifier = Modifier.fillMaxSize().padding(innerPadding)

                    val onNavigate = { key: String -> selectedScreenKey = key}

                    when (selectedScreenKey) {
                        "single" -> SinglePickersDemoScreen(modifier, onNavigate)
                        "double" -> DoublePickersDemoScreen(modifier, onNavigate)
                    }


                }

            }
        }

    }
}