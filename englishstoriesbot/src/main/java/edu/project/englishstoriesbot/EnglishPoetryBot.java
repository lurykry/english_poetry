package edu.project.englishstoriesbot;

import edu.project.englishstoriesbot.botapi.BotFacade;
import edu.project.englishstoriesbot.util.Rounder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class EnglishPoetryBot extends TelegramLongPollingBot{

    private static final Logger LOGGER = LoggerFactory.getLogger(EnglishPoetryBot.class);

    private String botName;
    private String botToken;
    private final BotFacade botFacade;

    @Autowired
    public EnglishPoetryBot(BotFacade botFacade) {
        this.botFacade = botFacade;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage message = botFacade.handleUpdate(update);
        if(message.getText().length() <= 4096)
            sendMessage(message);
        else
            sendMessageByParts(message);

    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }
    private void processBigMessage(SendMessage message){

        int messageLength = message.getText().length();
        String chatId = message.getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        ReplyKeyboard replyKeyboard = message.getReplyMarkup();

        if(replyKeyboard != null){
            sendMessage.setReplyMarkup(replyKeyboard);
        }


    }

    private void sendMessageByParts(SendMessage message){
        double length = message.getText().length();
        int k = Rounder.round(length / 4096);
        String messageText = message.getText();
        int startingIndx = 0;
        int endingIndx = 4096;

        LOGGER.info("length of the message: " + length);
        LOGGER.info("k: " + k);

        for (int i = 0; i < k; i++) {
            String partOfText = messageText.substring(startingIndx, endingIndx);
            startingIndx = endingIndx + 1;
            endingIndx = Math.min((endingIndx + 4096), (int)length);
            message.setText(partOfText);
            sendMessage(message);
        }
    }

    private void sendMessage(SendMessage sendMessage){
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
