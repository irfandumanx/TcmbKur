package com.example.tcmbkur

import android.os.Bundle
import android.os.StrictMode
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tcmbkur.ui.theme.TcmbKurTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
        if(XmlReader.currencies.size == 0) XmlReader().readXml("https://www.tcmb.gov.tr/kurlar/today.xml")
        setContent {
            TcmbKurTheme {
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "currency") {
                        composable("currency") {
                            CurrencyScreen(resources, packageName, navController)
                        }
                        composable("changecurrency") {
                            ChangeCurrency(navController = navController)
                        }
                    }
            }
        }
    }
}