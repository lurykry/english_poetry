package edu.project.englishstoriesbot.config;

import edu.project.englishstoriesbot.EnglishPoetryBot;
import edu.project.englishstoriesbot.botapi.BotFacade;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@Configuration
@ConfigurationProperties(prefix = "telegrambot")
public class BotConfig {

    private String botName;
    private String botToken;
    private String webHookPath;


    @Bean
    EnglishPoetryBot englishPoetryBot(BotFacade botFacade, ReplyKeyboardMarkup replyKeyboardMarkup){

        EnglishPoetryBot poetryBot = new EnglishPoetryBot(botFacade);
        poetryBot.setBotToken(botToken);
        poetryBot.setBotName(botName);

        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(poetryBot);
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }


        return poetryBot;
    }

    public String getBotName() {
        return botName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }

    public String getBotToken() {
        return botToken;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    public String getWebHookPath() {
        return webHookPath;
    }

    public void setWebHookPath(String webHookPath) {
        this.webHookPath = webHookPath;
    }
}
