package ru.tinkoff.utils

import org.jsoup.select.Elements

class ScrapTraffic : ScrapInformation {
    override fun getInfo(informationList: Elements) =
        "\uD83D\uDE98 Загруженность ${informationList.select("a").get(3).text()}: ${
            informationList.select("a").get(4).text()
        }"
}