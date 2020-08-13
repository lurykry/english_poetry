package edu.project.englishstoriesbot.botapi.handler.poetry.askfor;

import edu.project.englishstoriesbot.botapi.botstate.BotStates;
import edu.project.englishstoriesbot.botapi.handler.CallbackInputHandler;
import edu.project.englishstoriesbot.botapi.handler.MessageInputHandler;
import edu.project.englishstoriesbot.cache.UserDataCache;
import edu.project.englishstoriesbot.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.security.auth.callback.CallbackHandler;

import static edu.project.englishstoriesbot.botapi.botstate.BotStates.SHOW_ALL_POEMS_BY_AUTHOR;
import static edu.project.englishstoriesbot.botapi.botstate.BotStates.SHOW_REQUIRED_POEM;

@Component
public class AskForPoemHandler implements MessageInputHandler, CallbackInputHandler {

    @Value("${botreply.askforpoem}")
    private String askForPoem;
    private final MessageService messageService;
    private final UserDataCache userDataCache;

    @Autowired
    public AskForPoemHandler(MessageService messageService, UserDataCache userDataCache) {
        this.messageService = messageService;
        this.userDataCache = userDataCache;
    }

    @Override
    public SendMessage process(CallbackQuery callbackQuery) {

        int userId = callbackQuery.getFrom().getId();
        long chatId = callbackQuery.getMessage().getChatId();
        SendMessage reply = new SendMessage();

        userDataCache.setUsersCurrentBotState(userId,SHOW_REQUIRED_POEM);
        reply.setChatId(chatId);
        reply.setText(askForPoem);
        return reply;
    }

    @Override
    public SendMessage handle(Message message) {
        int userId = message.getFrom().getId();
        userDataCache.setUsersCurrentBotState(userId, BotStates.ASK_FOR_AUTHOR_NAME);
        return processMessage(message);
    }


    @Override
    public BotStates getHandlerName() {
        return BotStates.ASK_FOR_POEM;
    }

    private SendMessage processMessage(Message message) {
        long chatId = message.getChatId();

        SendMessage replyMessage = messageService.getReplyMessage(chatId, askForPoem);
        return replyMessage;
    }
}
