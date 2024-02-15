package org.example.repository.auth;

import org.example.model.auth.User;

import java.util.HashMap;
import java.util.Map;

public class AuthRepository {

    private final Map<String, User> userMap;

    public AuthRepository() {
        userMap = new HashMap<>();
    }


    public User get(String login) {
        return userMap.get(login);
    }

    public void add(User user) {
        userMap.put(user.getLogin(), user);
    }

    public boolean exist(String login) {
        return userMap.containsKey(login);
    }
}
