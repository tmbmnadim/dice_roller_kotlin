package me.mansurnadim.diceroller

import android.graphics.drawable.shapes.RoundRectShape
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class LemonadeApp {

    @Preview
    @Composable
    fun App() {
        Screen(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.Center)
        )
    }

    @Composable
    fun Screen(modifier: Modifier = Modifier) {
        var index by remember { mutableIntStateOf(0) }
        var squeezeCount = (2..5).random()
        var squeeze = {
            squeezeCount--
            if(squeezeCount == 0){
                index++
            }
        }
        val description = when (index) {
            0 -> R.string.lemon_tree
            1 -> R.string.lemon_squeeze
            2 -> R.string.lemon_drink
            else -> R.string.lemon_restart
        }
        val contentDescription = when (index) {
            0 -> R.string.lemon_tree_title.toString()
            1 -> R.string.lemon_squeeze_title.toString()
            2 -> R.string.lemon_drink_title.toString()
            else -> R.string.lemon_restart_title.toString()
        }
        val image = when (index) {
            0 -> R.drawable.lemon_tree
            1 -> R.drawable.lemon_squeeze
            2 -> R.drawable.lemon_drink
            else -> R.drawable.lemon_restart
        }
        Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .background(color = Color(color = 0xFFf9e44c))
                    .padding(all = 15.dp),
                contentAlignment = Alignment.BottomCenter

            ) {
                Text(
                    text = stringResource(R.string.lemon_title),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.weight(weight = 1f))
            LemonImageButton(
                imageId = image, contentDescription = contentDescription
            ) {
                when(index){
                    0->index++
                    1->squeeze()
                    2->index++
                    else-> index = 0
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(description), fontSize = 18.sp)
            Spacer(modifier = Modifier.weight(weight = 1f))
        }
    }

    @Composable
    private fun LemonImageButton(
        imageId: Int, contentDescription: String, onClick: () -> Unit
    ) {
        Button(
            onClick = onClick, shape = RoundedCornerShape(20.dp), colors = ButtonColors(
                containerColor = Color(color = 0xffc3ecd2),
                contentColor = Color.Unspecified,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.Black
            )
        ) {
            Image(painter = painterResource(imageId), contentDescription = contentDescription)
        }
    }
}