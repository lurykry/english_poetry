package edu.project.englishstoriesbot.botapi;

import edu.project.englishstoriesbot.botapi.botstate.BotStates;
import edu.project.englishstoriesbot.botapi.handler.CallbackInputHandler;
import edu.project.englishstoriesbot.botapi.handler.MessageInputHandler;
import edu.project.englishstoriesbot.cache.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.security.auth.callback.CallbackHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BotStateContext {

    private final Map<BotStates, MessageInputHandler> inputHandlerMessages = new HashMap<>();
    private final Map<BotStates, CallbackInputHandler> callbackHandlerMessages = new HashMap<>();

    private final Map<BotStates, UserData> userData = new HashMap<>();


    @Autowired
    public BotStateContext(List<MessageInputHandler> messageHandlers, List<CallbackInputHandler> callbackHandlers) {
        messageHandlers.forEach(handler -> inputHandlerMessages.put(handler.getHandlerName(), handler));
        callbackHandlers.forEach(handler -> callbackHandlerMessages.put(handler.getHandlerName(), handler));
    }

    public SendMessage processInputMessage(BotStates currentState, Message message){
        MessageInputHandler handler = findCurrentInputMessageHandler(currentState);
        return handler.handle(message);
    }

    public SendMessage processCallback(BotStates currentState, CallbackQuery callbackQuery){
        CallbackInputHandler handler = findCurrentCallbackHandler(currentState);
        return handler.process(callbackQuery);
    }

    private CallbackInputHandler findCurrentCallbackHandler(BotStates currentState) {
        return callbackHandlerMessages.get(currentState);
    }

    private MessageInputHandler findCurrentInputMessageHandler(BotStates currentState) {
        return inputHandlerMessages.get(currentState);
    }
}
