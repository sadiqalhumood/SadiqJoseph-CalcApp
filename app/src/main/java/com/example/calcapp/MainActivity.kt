package com.example.calcapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calcapp.ui.theme.CalcAppTheme
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalcAppTheme {
                CalcApp()
            }
        }
    }
}

@Composable
fun CalcApp() {
    var input by remember { mutableStateOf("0") }
    var operand1 by remember { mutableStateOf<Double?>(null) }
    var operator by remember { mutableStateOf<String?>(null) }
    var shouldResetInput by remember { mutableStateOf(false) }

    fun onNumberClick(number: String) {
        if (shouldResetInput) {
            input = number
            shouldResetInput = false
        } else {
            input = if (input == "0") number else input + number
        }
    }

    fun onOperatorClick(selectedOperator: String) {
        operand1 = input.toDoubleOrNull()
        operator = selectedOperator
        shouldResetInput = true
    }

    fun onSqrtClick() {
        val number = input.toDoubleOrNull()
        if (number != null && number >= 0) {
            input = kotlin.math.sqrt(number).toString()
        } else {
            input = "Error"
        }
        shouldResetInput = true
    }

    fun onEqualClick() {
        val operand2 = input.toDoubleOrNull()
        val op1 = operand1
        val op = operator

        if (op1 != null && op != null && operand2 != null) {
            val result = when (op) {
                "+" -> op1 + operand2
                "-" -> op1 - operand2
                "*" -> op1 * operand2
                "/" -> {
                    if (operand2 != 0.0) op1 / operand2 else Double.NaN
                }
                else -> null
            }

            input = if (result != null && !result.isNaN()) result.toString() else "Error"
        } else {
            input = "Error"
        }

        operand1 = null
        operator = null
        shouldResetInput = true
    }

    Column (
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = input,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp)
        )

        @Composable
        fun CalculatorButton(
            symbol: String,
            modifier: Modifier = Modifier,
            onClick: () -> Unit
        ){
            Button(
                onClick = onClick,
                modifier = modifier.padding(4.dp)
                    .height(64.dp)
            ){
                Text(text = symbol, style = MaterialTheme.typography.labelSmall)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            CalculatorButton("C", modifier = Modifier.width(64.dp)) {
                input = "0"
                operand1 = null
                operator = null
                shouldResetInput = false
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CalculatorButton("1", modifier = Modifier.weight(1f)) { onNumberClick("1") }
            CalculatorButton("2", modifier = Modifier.weight(1f)) { onNumberClick("2") }
            CalculatorButton("3", modifier = Modifier.weight(1f)) { onNumberClick("3") }
            CalculatorButton("+", modifier = Modifier.weight(1f)) { onOperatorClick("+") }
            CalculatorButton("*", modifier = Modifier.weight(1f)) { onOperatorClick("*") }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CalculatorButton("4", modifier = Modifier.weight(1f)) { onNumberClick("4") }
            CalculatorButton("5", modifier = Modifier.weight(1f)) { onNumberClick("5") }
            CalculatorButton("6", modifier = Modifier.weight(1f)) { onNumberClick("6") }
            CalculatorButton("-", modifier = Modifier.weight(1f)) { onOperatorClick("-") }
            CalculatorButton("/", modifier = Modifier.weight(1f)) { onOperatorClick("/") }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CalculatorButton("7", modifier = Modifier.weight(1f)) { onNumberClick("7") }
            CalculatorButton("8", modifier = Modifier.weight(1f)) { onNumberClick("8") }
            CalculatorButton("9", modifier = Modifier.weight(1f)) { onNumberClick("9") }
            CalculatorButton("sqrt", modifier = Modifier.weight(2f)) { onSqrtClick() }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CalculatorButton("0", modifier = Modifier.weight(2f)) { onNumberClick("0") }
            CalculatorButton(".", modifier = Modifier.weight(1f)) { onNumberClick(".") }
            CalculatorButton("=", modifier = Modifier.weight(2f)) { onEqualClick() }
        }

    }
}
