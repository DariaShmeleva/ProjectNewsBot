package ru.tinkoff

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import ru.tinkoff.service.NewsBotService


@SpringBootTest
class NewsBotServiceTest {

    private lateinit var newsBotService: NewsBotService

    @BeforeEach
    fun init() {
        newsBotService = mock(NewsBotService::class.java)
    }

    @Test
    fun shouldProperlySendMessage() {

        val chatId = 306651848L
        val message = "test"

        newsBotService.sendNotification(chatId, message)

        verify(newsBotService).sendNotification(chatId, message)
    }
}