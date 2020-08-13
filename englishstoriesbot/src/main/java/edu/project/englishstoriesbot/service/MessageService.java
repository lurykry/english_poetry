package edu.project.englishstoriesbot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

public interface MessageService {

    SendMessage getReplyMessage(long chatId, String text);
    SendMessage getReplyMessage(long chatId, List<String> list);
    SendMessage getReplyMessage(long chatId, String text, Object... args);
}
