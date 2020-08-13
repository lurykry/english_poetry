package edu.project.englishstoriesbot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService{

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
}
