package org.example.repository.reading;

import org.example.model.auth.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ReadingRepository<T> {

    private final Map<User, Map<Integer, T>> userDataByMonthMap;

    public ReadingRepository() {
        userDataByMonthMap = new HashMap<>();
    }


    public Map<Integer, T> getByUser(User user) {
        return userDataByMonthMap.get(user);
    }

    public Collection<Map<Integer, T>> getAll() {
        return userDataByMonthMap.values();
    }

    public void add(User user, Map<Integer, T> dataMonthMap) {
        userDataByMonthMap.put(user, dataMonthMap);
    }

    public boolean empty() {
        return userDataByMonthMap.isEmpty();
    }

    public boolean exist(User user) {
        return userDataByMonthMap.containsKey(user);
    }
}
