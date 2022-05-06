package ru.tinkoff.service

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow


@Service
class NewsBotService : TelegramLongPollingBot() {

    @Value("\${telegram.botName}")
    private val botName: String = ""

    @Value("\${telegram.token}")
    private val token: String = ""

    override fun getBotUsername(): String = botName

    override fun getBotToken(): String = token

    override fun onUpdateReceived(update: Update) {
        if (update.hasMessage()) {
            val message = update.message
            val chatId = message.chatId
            val responseText = if (message.hasText()) {
                val messageText = message.text
                when {
                    messageText == "/start" -> "Добро пожаловать! \uD83D\uDC4B"
                    messageText.startsWith("Новости") -> getInfo(
                        InfoType.NEWS,
                        "div#wd-_topnews-1.b-widget-data.b-wrapper.b-wrapper-"
                    )
                    messageText.startsWith("Погода") -> getInfo(
                        InfoType.WEATHER,
                        "div#wd-_weather-1.b-widget-data.b-wrapper.b-wrapper-"
                    )
                    messageText.startsWith("Пробки") -> getInfo(
                        InfoType.TRAFFIC,
                        "div#wd-_traffic-1.b-widget-data.b-wrapper.b-wrapper-"
                    )
                    messageText.startsWith("Магия") -> getInfo(
                        InfoType.MAGIC, "\uD83E\uDDDA✨"
                    )
                    else -> "Вы написали: *$messageText*"
                }
            } else {
                "Я понимаю только текст"
            }
            sendNotification(chatId, responseText as String)
        }
    }

    fun sendNotification(chatId: Long, responseText: String) {
        val responseMessage = SendMessage(chatId.toString(), responseText)
        responseMessage.enableMarkdown(true)
        // добавляем 4 кнопки
        responseMessage.replyMarkup = getReplyMarkup(
            listOf(
                listOf("Новости", "Погода"),
                listOf("Пробки", "Магия")
            )
        )
        execute(responseMessage)
    }

    fun getReplyMarkup(allButtons: List<List<String>>): ReplyKeyboardMarkup {
        val markup = ReplyKeyboardMarkup()
        markup.keyboard = allButtons.map { rowButtons ->
            val row = KeyboardRow()
            rowButtons.forEach { rowButton -> row.add(rowButton) }
            row
        }
        return markup
    }

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
                "Сейчас ${listNews.select("a").get(2).text()} \n${listNews.select("a").get(3).text()} \n${
                    listNews.select("a").get(4).text()
                }"
            }
            InfoType.TRAFFIC -> {
                " ${listNews.select("a").get(3).text()} баллов: ${listNews.select("a").get(4).text()}"
            }
            InfoType.MAGIC -> {
                "\uD83E\uDDDA✨" //не робит
            }
        }

        return result
    }

    enum class InfoType {
        NEWS, WEATHER, TRAFFIC, MAGIC
    }
}

