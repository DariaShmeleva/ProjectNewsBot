package ru.tinkoff.utils

import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import ru.tinkoff.client.NewsBotClient

fun getInfoByType(infType: InfoType, infoLink: String): String {

    val scrapNews = ScrapNews()
    val scrapWeather = ScrapWeather()
    val scrapTraffic = ScrapTraffic()
    val newsBotClient = NewsBotClient()

    val doc: Document = newsBotClient.getConnection()

    val informationList: Elements = doc.select(infoLink)

    val result = when (infType) {
        InfoType.NEWS -> scrapNews.getInfo(informationList)
        InfoType.WEATHER -> scrapWeather.getInfo(informationList)
        InfoType.TRAFFIC -> scrapTraffic.getInfo(informationList)
    }
    return result
}
