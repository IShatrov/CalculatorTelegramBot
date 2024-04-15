package bot;

import bot.expression.ExpressionTree;
import bot.expression.RecursiveParser;
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
            String text = update.getMessage().getText();
            ExpressionTree expressionTree;
            String respone;

            try {
                expressionTree = RecursiveParser.parse(text);

                respone = text + " = " + expressionTree.calculate(0);
            } catch (Exception e) {
                respone = "This is not a valid expression";
            }

            SendMessage message = SendMessage
                    .builder()
                    .chatId(chatID)
                    .text(respone)
                    .build();

            try {
                telegramClient.execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}