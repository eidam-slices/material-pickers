package cum.eidam.material_pickers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PickerItem(
    text: String,
    selected: Boolean,

    modifier: Modifier = Modifier,
    textOffset: Dp = 0.dp,

    onClick: (() -> Unit)? = null,
) {

    val itemModifier = onClick?.let {
        modifier.clickable(
            onClick = it,
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        )
    } ?: modifier

    Box(
        modifier = itemModifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {

        Text(
            text = text,
            modifier = Modifier.offset(x = textOffset),
            style =
                if (selected) {
                    MaterialTheme.typography.titleMedium.copy(
                        // todo: which color
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                else {
                    MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant


                    )
                },
        )
    }

}