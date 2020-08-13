package edu.project.englishstoriesbot.botapi.handler.poetry.show;

import edu.project.englishstoriesbot.botapi.botstate.BotStates;
import edu.project.englishstoriesbot.botapi.handler.MessageInputHandler;
import edu.project.englishstoriesbot.cache.UserDataCache;
import edu.project.englishstoriesbot.exeptions.FailedToLoadAuthorsException;
import edu.project.englishstoriesbot.restapiclient.poetry.PoetryApiClient;
import edu.project.englishstoriesbot.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Component
public class ShowAllAuthorsHandler implements MessageInputHandler {

    @Value("${botreply.error}")
    private String error;
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowAllAuthorsHandler.class);
    private final MessageService messageService;
    private final UserDataCache userDataCache;
    private final PoetryApiClient poetryApiClient;

    @Autowired
    public ShowAllAuthorsHandler(MessageService messageService, UserDataCache userDataCache, PoetryApiClient poetryApiClient) {
        this.messageService = messageService;
        this.userDataCache = userDataCache;
        this.poetryApiClient = poetryApiClient;
    }

    @Override
    public BotStates getHandlerName() {
        return BotStates.SHOW_ALL_AUTHORS;
    }

    @Override
    public SendMessage handle(Message message) {
        int userId = message.getFrom().getId();
        userDataCache.setUsersCurrentBotState(userId, BotStates.SHOW_ALL_POEMS_BY_AUTHOR);
        return processMessage(message);
    }

    private SendMessage processMessage(Message message){
        long chatId = message.getChatId();
        List<String> authors;

        try {
            authors = poetryApiClient.getAllAuthors();
            return messageService.getReplyMessage(chatId, authors);

        } catch (FailedToLoadAuthorsException e) {
            LOGGER.error("error occurred in processMessage() ",e);
            return messageService.getReplyMessage(chatId, error);
        }
    }
}
