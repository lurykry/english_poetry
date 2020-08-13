package edu.project.englishstoriesbot.cache;

import edu.project.englishstoriesbot.botapi.botstate.BotStates;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserDataCache implements DataCache{

    private final Map<Integer,BotStates> userBotStates = new HashMap<>();
    private final Map<Integer,UserData> userData = new HashMap<>();

    @Override
    public void setUsersCurrentBotState(int userId, BotStates botState) {
        userBotStates.put(userId, botState);
    }

    @Override
    public BotStates getUsersCurrentBotState(int userId) {
        return userBotStates.get(userId) != null ? userBotStates.get(userId) : BotStates.GREETINGS;
    }

    @Override
    public UserData getUserData(int userId) {
        return userData.get(userId) == null ? new UserData() : userData.get(userId);
    }

    @Override
    public void saveUserData(int userId, UserData uUserData) {
        userData.put(userId, uUserData);
    }
}
