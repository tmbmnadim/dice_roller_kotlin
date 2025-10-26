package me.mansurnadim.diceroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import me.mansurnadim.diceroller.ui.theme.DiceRollerTheme

class MainActivity : ComponentActivity() {
    val lemonApp = LemonadeApp()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DiceRollerTheme {
                lemonApp.App()
            }
        }
    }
}