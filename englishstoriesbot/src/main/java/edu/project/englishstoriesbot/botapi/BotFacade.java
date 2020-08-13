package edu.project.englishstoriesbot.botapi;

import edu.project.englishstoriesbot.botapi.botstate.BotStates;
import edu.project.englishstoriesbot.cache.DataCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static edu.project.englishstoriesbot.botapi.botstate.BotStates.*;

@Component
public class BotFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(BotFacade.class);
    private final BotStateContext botStateContext;
    private final DataCache userDataCache;


    @Autowired
    public BotFacade(BotStateContext botStateContext, DataCache userDataCache) {
        this.botStateContext = botStateContext;
        this.userDataCache = userDataCache;
    }

    public SendMessage handleUpdate(Update update){
        SendMessage reply = null;
        if(update.hasCallbackQuery()){
            CallbackQuery callbackQuery = update.getCallbackQuery();
            return processCallbackQuery(callbackQuery);
        }
        Message message = update.getMessage();
        if(message != null && message.hasText()){
            LOGGER.info("Message from user: {}, chatId: {}, text: {}", message.getFrom().getUserName(),
                    message.getChatId(), message.getText());
            reply = handleMessage(message);
        }
        return reply;
    }

    private SendMessage handleMessage(Message message){
        int userId = message.getFrom().getId();
        String text = message.getText();
        BotStates state;

        switch (text){
            case "/start" -> state = GREETINGS;
            case "about me" -> state = ABOUT_ME;
            case "all authors" -> state  = SHOW_ALL_AUTHORS;
            case "poems by author" -> state = ASK_FOR_AUTHOR_NAME;

            case "dictionary" -> state = ASK_FOR_WORD;

            default -> state = userDataCache.getUsersCurrentBotState(userId);
        }
        userDataCache.setUsersCurrentBotState(userId, state);

        return botStateContext.processInputMessage(state, message);
    }

    private SendMessage processCallbackQuery(CallbackQuery callbackQuery) {
        int userId = callbackQuery.getFrom().getId();
        String callbackData = callbackQuery.getData();
        BotStates state;


        switch (callbackData) {
            case "/read_poem" -> state = SHOW_REQUIRED_POEM;
            case "/another_poem" -> state = ASK_FOR_POEM;
            case "/another_author", "/choose_author" -> state = ASK_FOR_AUTHOR_NAME;
            case "/another_word" -> state = ASK_FOR_WORD;
            default -> state = userDataCache.getUsersCurrentBotState(userId);
        }

        userDataCache.setUsersCurrentBotState(userId, state);
        return botStateContext.processCallback(state, callbackQuery);
    }
}
