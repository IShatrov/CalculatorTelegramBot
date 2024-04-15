package bot;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Please copy bot token:");

        String botToken = new Scanner(System.in).nextLine();

        try (TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication()) {
            botsApplication.registerBot(botToken, new CalculatorBot(botToken));
            System.out.println("Bot successfully started");

            Thread.currentThread().join();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}