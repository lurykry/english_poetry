package edu.project.englishstoriesbot.botapi.handler;

import edu.project.englishstoriesbot.botapi.botstate.BotStates;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageInputHandler {

    SendMessage handle(Message message);
    BotStates getHandlerName();
}
