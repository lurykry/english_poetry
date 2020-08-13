package edu.project.englishstoriesbot.botapi.handler.poetry.askfor;

import edu.project.englishstoriesbot.botapi.botstate.BotStates;
import edu.project.englishstoriesbot.botapi.handler.CallbackInputHandler;
import edu.project.englishstoriesbot.botapi.handler.MessageInputHandler;
import edu.project.englishstoriesbot.cache.DataCache;
import edu.project.englishstoriesbot.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import static edu.project.englishstoriesbot.botapi.botstate.BotStates.SHOW_ALL_POEMS_BY_AUTHOR;

@Component
public class AskForAuthorNameHandler implements MessageInputHandler, CallbackInputHandler {

    @Value("${botreply.askforname}")
    private String askForAuthorName;
    private final DataCache userDataCache;
    private final MessageService messageService;


    @Autowired
    public AskForAuthorNameHandler(DataCache userDataCache, MessageService messageService) {
        this.userDataCache = userDataCache;
        this.messageService = messageService;
    }

    @Override
    public SendMessage process(CallbackQuery callbackQuery) {

        int userId = callbackQuery.getFrom().getId();
        long chatId = callbackQuery.getMessage().getChatId();
        SendMessage reply = new SendMessage();

        userDataCache.setUsersCurrentBotState(userId,SHOW_ALL_POEMS_BY_AUTHOR);
        reply.setChatId(chatId);
        reply.setText(askForAuthorName);
        return reply;
    }

    @Override
    public SendMessage handle(Message message) {
        int userId = message.getFrom().getId();
        userDataCache.setUsersCurrentBotState(userId, BotStates.SHOW_ALL_POEMS_BY_AUTHOR);
        return processMessage(message);
    }

    @Override
    public BotStates getHandlerName() {
        return BotStates.ASK_FOR_AUTHOR_NAME;
    }

    private SendMessage processMessage(Message message){
        long chatId = message.getChatId();

        SendMessage replyMessage = messageService.getReplyMessage(chatId, askForAuthorName);
        return replyMessage;
    }
}
