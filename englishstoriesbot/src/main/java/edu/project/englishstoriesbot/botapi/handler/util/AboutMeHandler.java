package edu.project.englishstoriesbot.botapi.handler.util;

import edu.project.englishstoriesbot.botapi.botstate.BotStates;
import edu.project.englishstoriesbot.botapi.handler.MessageInputHandler;
import edu.project.englishstoriesbot.cache.DataCache;
import edu.project.englishstoriesbot.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class AboutMeHandler implements MessageInputHandler {

    @Value("${botreply.aboutme}")
    private String replyText;
    private final MessageService messageService;
    private final DataCache userDataCache;

    @Autowired
    public AboutMeHandler(MessageService messageService, DataCache userDataCache) {
        this.messageService = messageService;
        this.userDataCache = userDataCache;
    }

    @Override
    public SendMessage handle(Message message) {
        int userId = message.getFrom().getId();
        userDataCache.setUsersCurrentBotState(userId, BotStates.WAITING);
        return processMessage(message);
    }

    @Override
    public BotStates getHandlerName() {
        return BotStates.ABOUT_ME;
    }

    private SendMessage processMessage(Message message){
        long chatId = message.getChatId();
        SendMessage replyMessage = messageService.getReplyMessage(chatId,replyText);
        return replyMessage;
    }
}
