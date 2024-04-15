package bot;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class CalculatorBot implements LongPollingSingleThreadUpdateConsumer {
    private final TelegramClient telegramClient;

    public CalculatorBot(String botToken) {
        telegramClient = new OkHttpTelegramClient(botToken);
    }

    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatID = update.getMessage().getChatId();

            SendMessage message = SendMessage
                    .builder()
                    .chatId(chatID)
                    .text("Hello")
                    .build();

            try {
                telegramClient.execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}