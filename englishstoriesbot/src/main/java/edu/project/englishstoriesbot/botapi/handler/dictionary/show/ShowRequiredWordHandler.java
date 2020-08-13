package edu.project.englishstoriesbot.botapi.handler.dictionary.show;

import edu.project.englishstoriesbot.botapi.botstate.BotStates;
import edu.project.englishstoriesbot.botapi.handler.MessageInputHandler;
import edu.project.englishstoriesbot.botapi.handler.dictionary.askor.AskForWordHandler;
import edu.project.englishstoriesbot.cache.DataCache;
import edu.project.englishstoriesbot.exeptions.TranslationNotFoundException;
import edu.project.englishstoriesbot.model.Expression;
import edu.project.englishstoriesbot.restapiclient.dictionary.DictionaryApiClient;
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

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

@Component
public class ShowRequiredWordHandler implements MessageInputHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AskForWordHandler.class);

    @Value("${botreply.error}")
    private String error;
    @Value("${botreply.translationnotfound}")
    private String transNotFound;

    private final MessageService messageService;
    private final DataCache userDataCache;
    private final DictionaryApiClient dictionaryApiClient;
    private final InlineKeyboardMarkup inlineKeyboardMarkup;

    @Autowired
    public ShowRequiredWordHandler(MessageService messageService, DataCache userDataCache, DictionaryApiClient dictionaryApiClient,
                                   @Qualifier("AnotherWord") InlineKeyboardMarkup inlineKeyboardMarkup) {
        this.messageService = messageService;
        this.userDataCache = userDataCache;
        this.dictionaryApiClient = dictionaryApiClient;
        this.inlineKeyboardMarkup = inlineKeyboardMarkup;
    }

    @Override
    public SendMessage handle(Message message) {
        int userId = message.getFrom().getId();
        userDataCache.setUsersCurrentBotState(userId, BotStates.SHOW_REQUIRED_WORD);
        return processMessage(message);
    }

    @Override
    public BotStates getHandlerName() {
        return BotStates.SHOW_REQUIRED_WORD;
    }

    private SendMessage processMessage(Message message){
        long chatId = message.getChatId();
        String theWord = message.getText().trim();

        try {
            Expression expression = dictionaryApiClient.translateExpression(theWord);
            if(expression.getOriginalExpression().size() == 0
                    || expression.getOriginalExpression()== null){
                return messageService.getReplyMessage(chatId, transNotFound);
            }
            SendMessage reply = messageService.getReplyMessage(chatId, expression);
            reply.setReplyMarkup(inlineKeyboardMarkup);
            LOGGER.info("expression:{}", expression);
            return reply;
        } catch (InterruptedException | IOException | ExecutionException | URISyntaxException e) {
            LOGGER.error("processMessage(): ", e);
            return messageService.getReplyMessage(chatId, error);
        } catch (TranslationNotFoundException e) {
            LOGGER.error("processMessage(): ", e);
            return messageService.getReplyMessage(chatId, transNotFound);
        }

    }
}
