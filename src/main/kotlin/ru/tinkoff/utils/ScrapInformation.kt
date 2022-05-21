package ru.tinkoff.utils

import org.jsoup.select.Elements

interface ScrapInformation {
    fun getInfo(informationList: Elements): String
}

