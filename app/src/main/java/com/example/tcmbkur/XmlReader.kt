package com.example.tcmbkur

import android.util.Log
import org.w3c.dom.Element
import org.w3c.dom.Node
import java.net.HttpURLConnection
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory

class XmlReader {

    data class Currency(val unit : String = "", val name : String = "", val forexBuying : String = "",
                        val forexSelling : String = "", val banknoteBuying : String = "", val banknoteSelling : String = "",
                        val crossRateUSD : String = "", val crossRateOther : String = "", val code : String = "",
                        val crossOrder : Int = -1)

    companion object {
        var currencies = mutableListOf<Currency>()
    }

    fun readXml(url : String) {
        try {
            val url = URL(url)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000
            connection.connect()
            val inputStream = connection.inputStream
            val builderFactory = DocumentBuilderFactory.newInstance()
            val docBuilder = builderFactory.newDocumentBuilder()
            val doc = docBuilder.parse(inputStream)
            val currencies = doc.getElementsByTagName("Currency")
            for (i in 0 until currencies.length) {
                if (currencies.item(0).nodeType == Node.ELEMENT_NODE) {
                    val element = currencies.item(i) as Element
                    val unit = getNodeValue("Unit", element)
                    val name = getNodeValue("Isim", element)
                    val forexBuying = getNodeValue("ForexBuying", element)
                    val forexSelling = getNodeValue("ForexSelling", element)
                    val banknoteBuying = getNodeValue("BanknoteBuying", element)
                    val banknoteSelling = getNodeValue("BanknoteSelling", element)
                    val crossRateUSD = getNodeValue("CrossRateUSD", element)
                    val crossRateOther = getNodeValue("CrossRateOther", element)
                    val code = element.getAttribute("Kod").lowercase()
                    val crossOrder = element.getAttribute("CrossOrder").toInt()
                    val currency = Currency(unit, name, forexBuying, forexSelling
                        , banknoteBuying, banknoteSelling, crossRateUSD, crossRateOther, code, crossOrder)
                    XmlReader.currencies.add(currency)
                }
            }
            inputStream.close()
            connection.disconnect()

        } catch (e: Exception) {
            Log.e("XML", e.toString())
        }
    }

    private fun getNodeValue(tag: String, element: Element): String {
        val nodeList = element.getElementsByTagName(tag)
        val node = nodeList.item(0)
        if (node != null) {
            if (node.hasChildNodes()) {
                val child = node.firstChild
                while (child != null) {
                    if (child.nodeType === Node.TEXT_NODE) {
                        return child.nodeValue
                    }
                }
            }
        }
        return ""
    }

}