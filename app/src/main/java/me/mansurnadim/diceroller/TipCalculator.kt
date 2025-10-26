package me.mansurnadim.diceroller

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.text.NumberFormat

class TipCalculator {
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
    private fun Screen(modifier: Modifier = Modifier) {
        var billAmount by remember { mutableStateOf<Double?>(null) }
        var tipPercent by remember { mutableStateOf<Double?>(null) }
        Column(
            modifier = modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .fillMaxWidth()
                .wrapContentWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                stringResource(R.string.calculate_tip),
                modifier = Modifier
                    .align(alignment = Alignment.Start)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(),
                label = {
                    Text(stringResource(R.string.bill_amount))
                },
                value = "${billAmount ?: ""}",
                onValueChange = { value ->
                    val sanitizedValue = value.replace(Regex("\\.{2,}"), ".")
                    billAmount =
                        if (sanitizedValue.isEmpty() || sanitizedValue.toDouble() == 0.0) {
                            null
                        } else {
                            sanitizedValue.toDoubleOrNull()
                        }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                label = {
                    Text(stringResource(R.string.tip_percentage))
                },
                value = "${tipPercent ?: ""}",
                onValueChange = { value ->
                    val sanitizedValue = value.replace(Regex("\\.{2,}"), ".")
                    tipPercent =
                        if (sanitizedValue.isEmpty() || sanitizedValue.toDouble() == 0.0) {
                            null
                        } else {
                            sanitizedValue.toDoubleOrNull()
                        }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(
                    R.string.tip_amount,
                    calculateTip(
                        amount = billAmount ?: 0.0,
                        tipPercent = tipPercent ?: 1.5
                    )
                ),
                style = MaterialTheme.typography.displaySmall
            )
            Row {
                Text(
                    text = stringResource(R.string.round_up_tip),
                    style = MaterialTheme.typography.displaySmall
                )
                Spacer(modifier = Modifier.weight(1f))
                Switch(false, onCheckedChange = {})
            }
        }
    }

    private fun calculateTip(amount: Double, tipPercent: Double = 15.0): String {
        try {
            val tip = tipPercent / 100 * amount
            return NumberFormat.getCurrencyInstance().format(tip)
        } catch (e: NumberFormatException) {
            print(e)
            return ""
        }
    }
}