package me.mansurnadim.diceroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import me.mansurnadim.diceroller.ui.theme.DiceRollerTheme

class MainActivity : ComponentActivity() {
    val lemonApp = LemonadeApp()
    val tipCalculatorApp = TipCalculator()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DiceRollerTheme {
                Surface(color = MaterialTheme.colorScheme.surface) {
                    tipCalculatorApp.App()
                }
            }
        }
    }
}