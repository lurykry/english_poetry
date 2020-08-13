package edu.project.englishstoriesbot.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class KeyboardsConfig {

    @Bean
    @Qualifier("ReadPoem")
    InlineKeyboardMarkup inlineKeyboardMarkupReadPoem(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonFindPoem = new InlineKeyboardButton().setText("read poem");
        buttonFindPoem.setCallbackData("/read_poem");
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(buttonFindPoem);
        List<List<InlineKeyboardButton>> row = new ArrayList<>();
        row.add(keyboardButtonsRow);

        inlineKeyboardMarkup.setKeyboard(row);
        return inlineKeyboardMarkup;
    }

    @Bean
    @Qualifier("AnotherPoemOrAuthor")
    InlineKeyboardMarkup inlineKeyboardMarkupAnotherPoem(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonAnotherPoem = new InlineKeyboardButton().setText("another poem");
        InlineKeyboardButton buttonAnotherAuthor = new InlineKeyboardButton().setText("another author");
        buttonAnotherPoem.setCallbackData("/another_poem");
        buttonAnotherAuthor.setCallbackData("/another_author");
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(buttonAnotherPoem);
        keyboardButtonsRow.add(buttonAnotherAuthor);

        List<List<InlineKeyboardButton>> row = new ArrayList<>();
        row.add(keyboardButtonsRow);

        inlineKeyboardMarkup.setKeyboard(row);
        return inlineKeyboardMarkup;
    }

    @Bean
    ReplyKeyboardMarkup replyKeyboardMarkup(){

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("all authors"));
        row1.add(new KeyboardButton("poems by author"));

        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("about me"));

        keyboard.add(row1);
        keyboard.add(row2);

        return replyKeyboardMarkup.setKeyboard(keyboard);
    }
}
