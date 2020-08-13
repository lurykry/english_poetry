package edu.project.englishstoriesbot.botapi.handler.poetry.show;

import edu.project.englishstoriesbot.botapi.botstate.BotStates;
import edu.project.englishstoriesbot.botapi.handler.MessageInputHandler;
import edu.project.englishstoriesbot.cache.DataCache;
import edu.project.englishstoriesbot.cache.UserData;
import edu.project.englishstoriesbot.exeptions.FailedToLoadTitlesException;
import edu.project.englishstoriesbot.restapiclient.poetry.PoetryApiClient;
import edu.project.englishstoriesbot.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

@Component
public class ShowAllPoemsByAuthorHandler implements MessageInputHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShowAllPoemsByAuthorHandler.class);

    @Value("${botreply.error}")
    private String error;
    private final DataCache userDataCache;
    private final MessageService messageService;
    private final PoetryApiClient poetryApiClient;
    private final InlineKeyboardMarkup inlineKeyboardMarkup;

    @Autowired
    public ShowAllPoemsByAuthorHandler(DataCache userDataCache, MessageService messageService, PoetryApiClient poetryApiClient, @Qualifier("ReadPoem")InlineKeyboardMarkup inlineKeyboardMarkup) {
        this.userDataCache = userDataCache;
        this.messageService = messageService;
        this.poetryApiClient = poetryApiClient;
        this.inlineKeyboardMarkup = inlineKeyboardMarkup;
    }

    @Override
    public SendMessage handle(Message message) {
        return processMessage(message);
    }

    @Override
    public BotStates getHandlerName() {
        return BotStates.SHOW_ALL_POEMS_BY_AUTHOR;
    }
    private SendMessage processMessage(Message message){

        int userId = message.getFrom().getId();

        String authorName = message.getText().trim();
        long chatId = message.getChatId();

        UserData userData = userDataCache.getUserData(userId);
        userData.setAuthorName(authorName);
        userDataCache.saveUserData(userId, userData);

        List<String> poems = null;
        try {
            poems = poetryApiClient.getPoemsByAuthorName(authorName);
            SendMessage replyMessage = messageService.getReplyMessage(chatId, poems);

            if(replyMessage.getText().contains("status:404,reason:Not found")){
                userDataCache.setUsersCurrentBotState(userId, BotStates.SHOW_ALL_POEMS_BY_AUTHOR);
                return messageService.getReplyMessage(chatId, "error: unknown author");
            }else{
                userDataCache.setUsersCurrentBotState(userId, BotStates.SHOW_REQUIRED_POEM);
                replyMessage.setReplyMarkup(inlineKeyboardMarkup);
                return replyMessage;
            }

        } catch (FailedToLoadTitlesException e) {
            userDataCache.setUsersCurrentBotState(userId, BotStates.SHOW_ALL_POEMS_BY_AUTHOR);
            LOGGER.error("error occurred in processMessage() ", e);
            return messageService.getReplyMessage(chatId, error);
        }
    }

}
