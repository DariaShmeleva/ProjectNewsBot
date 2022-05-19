package ru.tinkoff.utils

import org.jsoup.select.Elements

class ScrapNews : ScrapInformation {
    override fun getInfo(informationList: Elements) = "Сейчас в СМИ:\n" +
            "⟡ ${informationList.select("a").get(3).text()}; \n" +
            "⟡ ${informationList.select("a").get(4).text()}; \n" +
            "⟡ ${informationList.select("a").get(5).text()}; \n" +
            "⟡ ${informationList.select("a").get(6).text()}; \n" +
            "⟡ ${informationList.select("a").get(7).text()}; \n" +
            "⟡ ${informationList.select("a").get(8).text()}; \n" +
            "⟡ ${informationList.select("a").get(9).text()}. "
}