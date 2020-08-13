package edu.project.englishstoriesbot.botapi.handler.dictionary.askor;

import edu.project.englishstoriesbot.botapi.botstate.BotStates;
import edu.project.englishstoriesbot.botapi.handler.CallbackInputHandler;
import edu.project.englishstoriesbot.botapi.handler.MessageInputHandler;
import edu.project.englishstoriesbot.cache.DataCache;
import edu.project.englishstoriesbot.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import static edu.project.englishstoriesbot.botapi.botstate.BotStates.SHOW_ALL_POEMS_BY_AUTHOR;
import static edu.project.englishstoriesbot.botapi.botstate.BotStates.SHOW_REQUIRED_WORD;

@Component
public class AskForWordHandler implements MessageInputHandler, CallbackInputHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AskForWordHandler.class);

    @Value("${botreply.askforword}")
    private String askForWord;
    private final MessageService messageService;
    private final DataCache userDataCache;

    @Autowired
    public AskForWordHandler(MessageService messageService, DataCache userDataCache) {
        this.messageService = messageService;
        this.userDataCache = userDataCache;
    }

    @Override
    public SendMessage process(CallbackQuery callbackQuery) {
        int userId = callbackQuery.getFrom().getId();
        long chatId = callbackQuery.getMessage().getChatId();
        SendMessage reply = new SendMessage();

        userDataCache.setUsersCurrentBotState(userId,SHOW_REQUIRED_WORD);
        reply.setChatId(chatId);
        reply.setText(askForWord);
        return reply;
    }

    @Override
    public SendMessage handle(Message message) {
        int userId = message.getFrom().getId();
        userDataCache.setUsersCurrentBotState(userId, BotStates.SHOW_REQUIRED_WORD);
        return processMessage(message);
    }

    @Override
    public BotStates getHandlerName() {
        return BotStates.ASK_FOR_WORD;
    }

    private SendMessage processMessage(Message message){
        long chatId = message.getChatId();
        SendMessage reply = messageService.getReplyMessage(chatId, askForWord);
        return reply;
    }
}
