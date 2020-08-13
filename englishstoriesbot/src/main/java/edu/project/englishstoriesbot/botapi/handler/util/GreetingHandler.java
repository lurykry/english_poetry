package edu.project.englishstoriesbot.botapi.handler.util;

import edu.project.englishstoriesbot.botapi.botstate.BotStates;
import edu.project.englishstoriesbot.botapi.handler.MessageInputHandler;
import edu.project.englishstoriesbot.cache.DataCache;
import edu.project.englishstoriesbot.service.MenuService;
import edu.project.englishstoriesbot.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
@Component
public class GreetingHandler implements MessageInputHandler {

    @Value("${botreply.greeting}")
    private String replyText;
    private final DataCache userDataCache;
    private final MessageService messageService;
    private final MenuService menuService;


    @Autowired
    public GreetingHandler(DataCache userDataCache, MessageService messageService, MenuService menuService) {
        this.userDataCache = userDataCache;
        this.messageService = messageService;
        this.menuService = menuService;
    }

    @Override
    public SendMessage handle(Message message) {
        int userId = message.getFrom().getId();
        userDataCache.setUsersCurrentBotState(userId, BotStates.WAITING);
        return processMessage(message);
    }

    @Override
    public BotStates getHandlerName() {
        return BotStates.GREETINGS;
    }

    private SendMessage processMessage(Message message){
        long chatId = message.getChatId();
        return menuService.getMenuSendMessage(chatId, replyText);
    }
}
