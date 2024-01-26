package com.example.tcmbkur

import android.content.res.Resources
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun CurrencyScreen(resources : Resources, packageName : String, navController: NavController) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(20.dp))
        Button(shape = RoundedCornerShape(10) ,onClick = { navController.navigate("changecurrency") }) {
            Text(text = "Döviz Hesaplama")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(Modifier.background(Color(0xff63B4D1)).padding(bottom = 10.dp, top = 10.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Döviz Kodu", modifier = Modifier
                .weight(.2f)
                .padding(start = 10.dp))
            TableCell("Birim", .1f)
            TableCell("Döviz Cinsi", .3f)
            TableCell("Döviz\nAlış",.11f)
            TableCell("Döviz\nSatış", .12f)
            TableCell("Efektif\nAlış", .12f)
            TableCell("Efektif\nSatış",.12f)
        }

        XmlReader.currencies.subList(0, XmlReader.currencies.size - 1).forEachIndexed {
                index, it ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(if (index % 2 == 1) 0xff8a8a8a else 0xfff))
                .padding(16.dp)) {
                Column(modifier = Modifier
                    .height(40.dp)
                    .weight(.2f)) {
                    val id = resources.getIdentifier("@drawable/${it.code}", null, packageName)
                    Image(modifier = Modifier
                        .height(20.dp)
                        .width(20.dp),
                        painter = painterResource(id = id), contentDescription = it.name)
                    Text(text = "${it.code.uppercase()}/TRY")
                }
                TableCell(text = it.unit, weight = .1f)
                TableCell(text = it.name, weight = .3f)
                TableCell(text = it.forexBuying, weight = .12f)
                TableCell(text = it.forexSelling, weight = .12f)
                TableCell(text = it.banknoteBuying, weight = .12f)
                TableCell(text = it.banknoteSelling, weight = .12f)
            }
        }

        Spacer(modifier = Modifier.height(25.dp))
        //###
        Row(Modifier.background(Color(0xff63B4D1)), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Çapraz Kurlar / Cross Rates", modifier = Modifier
                .weight(.2f)
                .padding(10.dp))
        }
        Row(Modifier.background(Color(0xff63B4D1)).padding(bottom = 10.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Döviz Kodu", modifier = Modifier
                .weight(.2f)
                .padding(start = 10.dp))
            TableCell("Birim", .1f)
            TableCell("Döviz Cinsi", .3f)
            TableCell("Çapraz Kur",.2f)
            TableCell("Döviz Cinsi", .3f)
        }

        XmlReader.currencies.subList(1, XmlReader.currencies.size - 1).sortedBy { it.crossOrder }.forEachIndexed {
                index, it ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(if (index % 2 == 1) 0xff8a8a8a else 0xfff))
                .padding(16.dp)) {
                Column(modifier = Modifier
                    .height(40.dp)
                    .weight(.2f)) {
                    val id = resources.getIdentifier("@drawable/${it.code}", null, packageName)
                    Image(modifier = Modifier
                        .height(20.dp)
                        .width(20.dp),
                        painter = painterResource(id = id), contentDescription = it.name)
                    Text(text = "${if(it.crossRateUSD.isNotBlank()) "USD" else it.code.uppercase()}/${if(it.crossRateUSD.isNotBlank()) it.code.uppercase() else "USD"}")
                }
                TableCell(text = it.unit, weight = .1f)
                TableCell(text = if(it.crossRateUSD.isNotBlank()) "ABD DOLARI" else it.name, weight = .3f)
                TableCell(text = it.crossRateUSD.ifBlank { it.crossRateOther }, weight = .2f)
                TableCell(text = if(it.crossRateUSD.isBlank()) "ABD DOLARI" else it.name, weight = .3f)
            }
        }

        Spacer(modifier = Modifier.height(25.dp))
        //###
        Row(Modifier.background(Color(0xff63B4D1)), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Bilgi İçin / For Information", modifier = Modifier
                .weight(.2f)
                .padding(10.dp))
        }
        val lastElement = XmlReader.currencies[XmlReader.currencies.size - 1]
        Row(modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xfff))
            .padding(16.dp)) {
            Column(modifier = Modifier
                .height(40.dp)
                .weight(.2f)) {
                Text(text = "SDR/USD")
            }
            TableCell(text = lastElement.unit, weight = .1f)
            TableCell(text = lastElement.name, weight = .3f)
            TableCell(text = lastElement.crossRateOther, weight = .2f)
            TableCell(text = "ABD DOLARI", weight = .3f)
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xff8a8a8a))
            .padding(16.dp)) {
            Column(modifier = Modifier
                .height(40.dp)
                .weight(.2f)) {
                Text(text = "SDR/TRY")
            }
            TableCell(text = lastElement.unit, weight = .1f)
            TableCell(text = lastElement.name, weight = .3f)
            TableCell(text = lastElement.forexBuying, weight = .2f)
            TableCell(text = "TÜRK LİRASI", weight = .3f)
        }
    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    Text(
        text = text,
        Modifier
            .weight(weight)
    )
}