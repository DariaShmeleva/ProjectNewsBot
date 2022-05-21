package ru.tinkoff

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import ru.tinkoff.service.NewsBotService
import java.lang.Compiler.command





class NewsBotTest {

    private var sendBotMessageService: NewsBotService? = null
    private var newsBotService: NewsBotService? = null

    @BeforeEach
    fun init() {
        newsBotService = Mockito.mock(NewsBotService::class.java)
        //sendBotMessageService = SendBotMessageServiceImpl(newsBotService)
    }

    @Test
    @Throws(TelegramApiException::class)
    fun shouldProperlySendMessage() {
        //given
        val chatId = 306651848
        val message = "🧚✨"
        val sendMessage = SendMessage()
        sendMessage.text = message
        sendMessage.chatId = chatId.toString()
        sendMessage.enableHtml(true)

        //when
        val result = sendBotMessageService?.sendNotification(chatId.toLong(), message)

        //then
        //Mockito.verify<Any?>(newsBotService). sendNotification(sendMessage)
        assertEquals(sendBotMessageService.onUpdateReceived("Магия"), result )
    }
}