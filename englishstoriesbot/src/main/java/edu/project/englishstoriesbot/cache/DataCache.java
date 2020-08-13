package edu.project.englishstoriesbot.cache;

import edu.project.englishstoriesbot.botapi.botstate.BotStates;

public interface DataCache {

    void setUsersCurrentBotState(int userId, BotStates botState);
    BotStates getUsersCurrentBotState(int userId);
    UserData getUserData(int userId);
    void saveUserData(int userId, UserData userData);
}
