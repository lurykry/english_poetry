package edu.project.englishstoriesbot.service;

import edu.project.englishstoriesbot.model.Sendable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService{

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);
    private static final String[] EXPRESSION_FIELD_NAMES = {"Word/Expression: ", "Part of speech: ", "Translation: "};
    private static final String[] POEM_FIELD_NAMES = {"Author: ", "Title: ", "\n"};

    @Override
    public SendMessage getReplyMessage(long chatId, String text) {
        return new SendMessage(chatId, text);
    }

    @Override
    public SendMessage getReplyMessage(long chatId, String text, Object... args) {
        return new SendMessage(chatId, text);
    }

    @Override
    public SendMessage getReplyMessage(long chatId, List<String> list) {
        StringBuilder stringBuilder = new StringBuilder();
        list.forEach(string -> stringBuilder.append(string).append("\n"));
       return new SendMessage(chatId, stringBuilder.toString());
    }

    @Override
    public SendMessage getReplyMessage(long chatId, Sendable sendable) {
        Class<? extends Sendable> clazz = sendable.getClass();
        Field[] fields =  clazz.getDeclaredFields();
       StringBuilder stringBuilder = new StringBuilder();
       int exprCounter = 0;
       int poemCounter = 0;
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object o = field.get(sendable);
                if(o instanceof Collection){
                    Collection<String> collection =  (Collection)o;
                    stringBuilder.append("\n").append(EXPRESSION_FIELD_NAMES[exprCounter++]);
                    collection
                            .forEach(str -> stringBuilder.append(str).append(", "));
                }else{
                    stringBuilder.append(POEM_FIELD_NAMES[poemCounter++])
                            .append((String)field.get(sendable))
                            .append("\n");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        LOGGER.info("sendable: {}", stringBuilder.toString());
        return new SendMessage(chatId, stringBuilder.toString());
    }
}
