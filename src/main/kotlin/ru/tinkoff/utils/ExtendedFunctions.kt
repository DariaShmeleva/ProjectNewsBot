package ru.tinkoff.utils

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import ru.tinkoff.InfoType

fun getInfo(infType: InfoType, infoLink: String): String {

    val doc: Document = Jsoup.connect("https://yandex.ru/")
        .userAgent("Chrome/4.0.249.0 Safari/532.5")
        .referrer("http://www.google.com")
        .get()

    val listNews: Elements = doc.select(infoLink)

    var result = when (infType) {
        InfoType.NEWS -> {
            "Сейчас в СМИ\n" +
                    "- ${listNews.select("a").get(3).text()} \n" +
                    "- ${listNews.select("a").get(4).text()} \n" +
                    "- ${listNews.select("a").get(5).text()} \n" +
                    "- ${listNews.select("a").get(6).text()} \n" +
                    "- ${listNews.select("a").get(7).text()} \n" +
                    "- ${listNews.select("a").get(8).text()} \n" +
                    "- ${listNews.select("a").get(9).text()} "
        }
        InfoType.WEATHER -> {
            "сегодня ${listNews.select("a").get(2).text()} \n${listNews.select("a").get(3).text()} \n${
                listNews.select("a").get(4).text()
            }"
        }
        InfoType.TRAFFIC -> {
            "Загруженность ${listNews.select("a").get(3).text()}: ${listNews.select("a").get(4).text()}"
        }
    }
    return result
}