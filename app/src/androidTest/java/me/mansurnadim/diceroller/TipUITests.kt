package me.mansurnadim.diceroller

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import me.mansurnadim.diceroller.ui.theme.DiceRollerTheme

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule
import java.text.NumberFormat

@RunWith(AndroidJUnit4::class)
class TipUITests {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun calculate_20_percent_tip() {
        val tipCalc = TipCalculator()
        composeTestRule.setContent {
            DiceRollerTheme() {
                tipCalc.App()
            }
        }
        val expectedTip = NumberFormat.getCurrencyInstance().format(2);
        composeTestRule.onNodeWithText("Bill Amount").performTextInput("10")
        composeTestRule.onNodeWithText("Tip Percentage").performTextInput("20")
        composeTestRule.onNodeWithText("Tip Amount: $expectedTip")
            .assertExists("No node with this text was found.")
    }
}