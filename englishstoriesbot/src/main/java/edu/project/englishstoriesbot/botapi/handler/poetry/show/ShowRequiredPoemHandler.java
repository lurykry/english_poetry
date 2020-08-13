package edu.project.englishstoriesbot.botapi.handler.poetry.show;

import edu.project.englishstoriesbot.botapi.botstate.BotStates;
import edu.project.englishstoriesbot.botapi.handler.CallbackInputHandler;
import edu.project.englishstoriesbot.botapi.handler.MessageInputHandler;
import edu.project.englishstoriesbot.cache.DataCache;
import edu.project.englishstoriesbot.cache.UserData;
import edu.project.englishstoriesbot.exeptions.FailedToLoadPoemException;
import edu.project.englishstoriesbot.exeptions.FailedToLoadTitlesException;
import edu.project.englishstoriesbot.exeptions.PoemNotFoundException;
import edu.project.englishstoriesbot.model.Poem;
import edu.project.englishstoriesbot.restapiclient.poetry.PoetryApiClient;
import edu.project.englishstoriesbot.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import static edu.project.englishstoriesbot.botapi.botstate.BotStates.SHOW_REQUIRED_POEM;

@Component
public class ShowRequiredPoemHandler implements MessageInputHandler, CallbackInputHandler {

    @Value("${botreply.error}")
    private String error;
    @Value("${botreply.poemnotfound}")
    private String poemNotFound;

    private final DataCache userDataCache;
    private final MessageService messageService;
    private final PoetryApiClient poetryApiClient;
    private final InlineKeyboardMarkup inlineKeyboardMarkupAnotherPoem;

    @Autowired
    public ShowRequiredPoemHandler(DataCache userDataCache, MessageService messageService, PoetryApiClient poetryApiClient,
                                   @Qualifier("AnotherPoemOrAuthor")InlineKeyboardMarkup inlineKeyboardMarkupAnotherPoem) {
        this.userDataCache = userDataCache;
        this.messageService = messageService;
        this.poetryApiClient = poetryApiClient;
        this.inlineKeyboardMarkupAnotherPoem = inlineKeyboardMarkupAnotherPoem;
    }

    @Override
    public SendMessage handle(Message message) {
        return processMessage(message);
    }

    @Override
    public SendMessage process(CallbackQuery callbackQuery) {
        int userId = callbackQuery.getFrom().getId();
        long chatId = callbackQuery.getMessage().getChatId();
        SendMessage reply = new SendMessage();

        userDataCache.setUsersCurrentBotState(userId,SHOW_REQUIRED_POEM);
        reply.setChatId(chatId);
        reply.setText("Enter a title of the poem");
        return reply;
    }

    @Override
    public BotStates getHandlerName() {
        return BotStates.SHOW_REQUIRED_POEM;
    }

    private SendMessage processMessage(Message message) {

        String poemTitle = message.getText().trim();
        long chatId = message.getChatId();
        int userId = message.getFrom().getId();

        userDataCache.setUsersCurrentBotState(userId, BotStates.SHOW_REQUIRED_POEM);
        UserData userData = userDataCache.getUserData(userId);
        userData.setPoemTitle(poemTitle);
        userDataCache.saveUserData(userId, userData);

        Poem poem = null;
        try {
            poem = poetryApiClient.getUserRequiredPoem(userData);
            SendMessage replyMessage = messageService.getReplyMessage(chatId, poem.getContent());

            if(replyMessage.getText().contains("status:404,reason:Not found")){
                return messageService.getReplyMessage(chatId, poemNotFound);
            }
            replyMessage.setReplyMarkup(inlineKeyboardMarkupAnotherPoem);
            return replyMessage;

        } catch (FailedToLoadPoemException | FailedToLoadTitlesException e) {
            return messageService.getReplyMessage(chatId, error);
        } catch (PoemNotFoundException e) {
            return messageService.getReplyMessage(chatId, poemNotFound);
        }
    }
}
