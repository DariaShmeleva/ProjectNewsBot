package ru.tinkoff.client

import org.jsoup.Jsoup

class NewsBotClient {

    fun getConnection() = Jsoup.connect("https://yandex.ru/")
        .userAgent("Chrome/4.0.249.0 Safari/532.5")
        .referrer("http://www.google.com")
        .get()
}