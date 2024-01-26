package com.example.tcmbkur

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeCurrency(navController: NavController) {
    var tryCurrency by remember { mutableStateOf(XmlReader.Currency(code="TRY", forexSelling = "1")) }
    var valueState by remember { mutableStateOf("") }
    var fromExpanded by remember { mutableStateOf(false) }
    var fromCurrency by remember { mutableStateOf(XmlReader.currencies[0]) }
    var toExpanded by remember { mutableStateOf(false) }
    var toCurrency by remember { mutableStateOf(tryCurrency) }
    var amount by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            shape = RoundedCornerShape(5),
            onClick = { navController.popBackStack() }) {
            Text(text = "Geri Dön")
        }
        Spacer(modifier = Modifier.height(30.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            ExposedDropdownMenuBox(
                modifier = Modifier.width(100.dp),
                expanded = fromExpanded,
                onExpandedChange = {
                    fromExpanded = !fromExpanded
                }
            ) {
                TextField(
                    value = fromCurrency.code.uppercase(),
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = fromExpanded) },
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = fromExpanded,
                    onDismissRequest = { fromExpanded = false }
                ) {
                    XmlReader.currencies.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item.code.uppercase()) },
                            onClick = {
                                fromCurrency = item
                                fromExpanded = false
                                if(amount.isNotBlank()) valueState = fromCurrency.forexSelling.toFloat().div(toCurrency.forexSelling.toFloat()).times(amount.toFloat()).toString() + " " + toCurrency.code.uppercase()
                            }
                        )
                    }
                    DropdownMenuItem(
                        text = { Text(text = "TRY") },
                        onClick = {
                            fromCurrency = tryCurrency
                            fromExpanded = false
                            if(amount.isNotBlank()) valueState = fromCurrency.forexSelling.toFloat().div(toCurrency.forexSelling.toFloat()).times(amount.toFloat()).toString() + " " + toCurrency.code.uppercase()
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.width(30.dp))
            Text(text = "-->", fontSize = 24.sp)
            Spacer(modifier = Modifier.width(30.dp))

            ExposedDropdownMenuBox(
                modifier = Modifier.width(100.dp),
                expanded = toExpanded,
                onExpandedChange = {
                    toExpanded = !toExpanded
                }
            ) {
                TextField(
                    value = toCurrency.code.uppercase(),
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = toExpanded) },
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = toExpanded,
                    onDismissRequest = { toExpanded = false }
                ) {
                    XmlReader.currencies.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item.code.uppercase()) },
                            onClick = {
                                toCurrency = item
                                toExpanded = false
                                if(amount.isNotBlank()) valueState = fromCurrency.forexSelling.toFloat().div(toCurrency.forexSelling.toFloat()).times(amount.toFloat()).toString() + " " + toCurrency.code.uppercase()
                            }
                        )
                    }
                    DropdownMenuItem(
                        text = { Text(text = "TRY") },
                        onClick = {
                            toCurrency = tryCurrency
                            toExpanded = false
                            if(amount.isNotBlank()) valueState = fromCurrency.forexSelling.toFloat().div(toCurrency.forexSelling.toFloat()).times(amount.toFloat()).toString() + " " + toCurrency.code.uppercase()
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
        TextField(
            modifier = Modifier.width(250.dp),
            value = amount,
            onValueChange = {
                amount = it
                valueState = fromCurrency.forexSelling.toFloat().div(toCurrency.forexSelling.toFloat()).times(amount.toFloat()).toString() + " " + toCurrency.code.uppercase()
            },
            trailingIcon = {
                Text(text = fromCurrency.code.uppercase())
            }
        )
        Text(text = valueState)
    }
}