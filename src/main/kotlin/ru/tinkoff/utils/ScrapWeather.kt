package ru.tinkoff.utils

import org.jsoup.select.Elements

class ScrapWeather : ScrapInformation {
    override fun getInfo(informationList: Elements) =
        "Сейчас ${informationList.select("a").get(2).text()}: \n\uD83C\uDF1E ${
            informationList.select("a").get(3).text()
        }, \n\uD83C\uDF1A ${
            informationList.select("a").get(4).text()
        }"
}