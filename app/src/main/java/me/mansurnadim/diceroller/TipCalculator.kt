package me.mansurnadim.diceroller

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.text.NumberFormat
import kotlin.math.roundToInt

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
        var roundTip by remember { mutableStateOf<Boolean>(false) }
        Column(
            modifier = modifier
                .padding(bottom = 16.dp, top = 40.dp, start = 32.dp, end = 32.dp)
                .fillMaxWidth()
                .wrapContentWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                stringResource(R.string.calculate_tip),
                modifier = Modifier
                    .align(alignment = Alignment.Start)
            )
            Spacer(modifier = Modifier.height(16.dp))
            EditNumberField(
                label = R.string.bill_amount,
                leadingIcon = R.drawable.payments,
                value = "${billAmount ?: ""}",
            ) { value ->
                val sanitizedValue = value.replace(Regex("\\.{2,}"), ".")
                billAmount =
                    if (sanitizedValue.isEmpty() || sanitizedValue.toDouble() == 0.0) {
                        null
                    } else {
                        sanitizedValue.toDoubleOrNull()
                    }
            }
            Spacer(modifier = Modifier.height(16.dp))
            EditNumberField(
                label = R.string.tip_percentage,
                leadingIcon = R.drawable.percent_discount,
                value = "${tipPercent ?: ""}",
            ) { value ->
                val sanitizedValue = value.replace(Regex("\\.{2,}"), ".")
                tipPercent =
                    if (sanitizedValue.isEmpty() || sanitizedValue.toDouble() == 0.0) {
                        null
                    } else {
                        sanitizedValue.toDoubleOrNull()
                    }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Text(
                    text = stringResource(R.string.round_up_tip),
                    style = MaterialTheme.typography.displaySmall
                )
                Spacer(modifier = Modifier.weight(1f))
                Switch(roundTip, onCheckedChange = { value -> roundTip = value })
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier
                    .align(alignment = Alignment.Start),
                text = stringResource(
                    R.string.tip_amount,
                    calculateTip(
                        amount = billAmount ?: 0.0,
                        tipPercent = tipPercent ?: 1.5,
                        roundTip,
                    )
                ),
                style = MaterialTheme.typography.displaySmall
            )
        }
    }

    @Composable
    private fun EditNumberField(
        @StringRes label: Int,
        @DrawableRes leadingIcon: Int,
        value: String,
        onValueChanged: (String) -> Unit
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            label = {
                Text(stringResource(label))
            },
            leadingIcon = {
                Icon(
                    painterResource(leadingIcon),
                    contentDescription = "Leading Icon for Text Field"
                )
            },
            value = value,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            onValueChange = onValueChanged,
        )
    }

    @VisibleForTesting
    internal fun calculateTip(
        amount: Double,
        tipPercent: Double = 15.0,
        roundTip: Boolean = false
    ): String {
        try {
            val tip = tipPercent / 100 * amount
            return NumberFormat.getCurrencyInstance()
                .format(if (roundTip) tip.roundToInt() else tip)
        } catch (e: NumberFormatException) {
            print(e)
            return ""
        }
    }
}