package ru.tinkoff.service


import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import ru.tinkoff.utils.InfoType
import ru.tinkoff.utils.getInfo


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
                        InfoType.NEWS, GET_NEWS
                    )
                    messageText.startsWith("Погода") -> getInfo(
                        InfoType.WEATHER, GET_WEATHER
                    )
                    messageText.startsWith("Пробки") -> getInfo(
                        InfoType.TRAFFIC, GET_TRAFFIC
                    )
                    messageText.startsWith("Магия") -> "\uD83E\uDDDA✨"
                    else -> "Вы написали: *$messageText*"
                }
            } else {
                "Я понимаю только текст"
            }
            sendNotification(chatId, responseText)
        }
    }

    fun sendNotification(chatId: Long, responseText: String) {
        val responseMessage = SendMessage(chatId.toString(), responseText)
        responseMessage.enableMarkdown(true)

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

    companion object {
        private const val GET_NEWS = "div#wd-_topnews-1.b-widget-data.b-wrapper.b-wrapper-"
        private const val GET_WEATHER = "div#wd-_weather-1.b-widget-data.b-wrapper.b-wrapper-"
        private const val GET_TRAFFIC = "div#wd-_traffic-1.b-widget-data.b-wrapper.b-wrapper-"
    }
}

