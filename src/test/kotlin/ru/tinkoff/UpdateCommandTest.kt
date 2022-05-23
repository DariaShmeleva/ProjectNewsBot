package ru.tinkoff

import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import ru.tinkoff.service.NewsBotService


@SpringBootTest
class UpdateCommandTest {

    private var newsBotService: NewsBotService = mock(NewsBotService::class.java)

    @Test
    fun shouldProperlyUpdateCommand() {

        val update = Update()
        val message: Message = spy(Message::class.java)
        update.message = message
        newsBotService.onUpdateReceived(update)

        verify(newsBotService).onUpdateReceived(update)
    }
}