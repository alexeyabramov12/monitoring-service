package org.example.service.reading;

import org.example.dto.reading.BaseReadingDto;
import org.example.exception.reading.ReadingException;
import org.example.mapper.reading.ReadingMapper;
import org.example.model.auth.Role;
import org.example.model.auth.User;
import org.example.model.reading.BaseReading;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class ReadingService<T extends BaseReading, V extends BaseReadingDto> {

    private final Map<User, Map<Integer, T>> userDataByMonthMap;
    private final ReadingMapper<T, V> mapper;
    private static final String NO_READINGS = "Показания отсутствуют";


    public ReadingService() {
        userDataByMonthMap = new HashMap<>();
        mapper = new ReadingMapper();
    }

    public BaseReadingDto getActualData(User user) {
        String errorMassage = "\nАктуальные показания для пользователя \"".concat(user.getLogin()).concat("\" отсутствуют\n");
        if (!userDataByMonthMap.containsKey(user)) {
            throw new ReadingException(errorMassage);
        }

        Collection<T> readings = userDataByMonthMap.get(user).values();
        Optional<T> readingOptional = readings.stream().max(Comparator.comparing(BaseReading::getDate));

        if (readingOptional.isPresent()) {
            return mapper.readingToDto(readings.stream().max(Comparator.comparing(BaseReading::getDate)).get());
        } else throw new ReadingException(errorMassage);
    }

    public List<V> getAllActualData() {
        if (userDataByMonthMap.isEmpty()) {
            throw new ReadingException(NO_READINGS);
        }
        List<T> readingList = new ArrayList<>();

        for (Map.Entry<User, Map<Integer, T>> entry : userDataByMonthMap.entrySet()) {
            Map<Integer, T> monthReadingMap = entry.getValue();
            readingList.add(monthReadingMap.values().stream().max(Comparator.comparing(BaseReading::getDate)).get());
        }
        return readingList.stream()
                .map(mapper::readingToDto)
                .toList();
    }

    public List<V> getAllDataByUser(User user) {
        String errorMassage = "\nПоказания для пользователя \"".concat(user.getLogin()).concat("\" отсутствуют\n");
        if (!userDataByMonthMap.containsKey(user)) {
            throw new ReadingException(errorMassage);
        }

        return userDataByMonthMap.get(user).values().stream().map(mapper::readingToDto).toList();
    }

    public List<V> getAllData() {
        if (userDataByMonthMap.isEmpty()) {
            throw new ReadingException(NO_READINGS);
        }
        List<T> readingList = new ArrayList<>();

        for (Map.Entry<User, Map<Integer, T>> entry : userDataByMonthMap.entrySet()) {
            Map<Integer, T> monthReadingMap = entry.getValue();
            readingList.addAll(monthReadingMap.values());
        }
        return readingList.stream()
                .map(mapper::readingToDto)
                .toList();
    }

    public V getDataByMonth(User user, int month) {
        String errorMassage = "Показания для пользователя \"".concat(user.getLogin()).concat("\" за месяц ").concat(Month.of(month).toString()).concat(" отсутствуют");

        if (!userDataByMonthMap.containsKey(user)) {
            throw new ReadingException(errorMassage);
        }

        T reading = userDataByMonthMap.get(user).get(month);

        if (reading == null) {
            throw new ReadingException(errorMassage);
        } else return mapper.readingToDto(reading);
    }

    public List<V> getAllDataByMonth(int month) {
        String errorMassage = "Показания за месяц ".concat(Month.of(month).toString()).concat(" отсутствуют");

        if (userDataByMonthMap.isEmpty()) {
            throw new ReadingException(NO_READINGS);
        }
        List<T> readingList = new ArrayList<>();

        for (Map.Entry<User, Map<Integer, T>> entry : userDataByMonthMap.entrySet()) {
            Map<Integer, T> monthReadingMap = entry.getValue();
            if (monthReadingMap.containsKey(month)) {
                readingList.addAll(monthReadingMap.values());
            }
        }
        if (readingList.isEmpty()) {
            throw new ReadingException(errorMassage);
        }

        return readingList.stream()
                .map(mapper::readingToDto)
                .toList();
    }

    public void addData(User user, V dto) {
        T reading = mapper.dtoToReading(dto);
        String errorMassage = "ОШИБКА! Показания можно подавать один раз в месяц.\n";
        if (!checkToAdd(user, reading.getDate()) || user.getRole().equals(Role.ADMIN)) {
            throw new ReadingException(errorMassage);
        }

        LocalDate localDate = reading.getDate();
        Integer month = localDate.getMonth().getValue();
        Map<Integer, T> dataByMonth = new HashMap<>();
        dataByMonth.put(month, reading);
        userDataByMonthMap.put(user, dataByMonth);
    }

    private boolean checkToAdd(User user, LocalDate localDate) {
        if (!userDataByMonthMap.containsKey(user)) {
            return true;
        }

        Map<Integer, T> monthDataMap = userDataByMonthMap.get(user);
        Integer month = localDate.getMonth().getValue();
        T readingByCurrentMonth = monthDataMap.get(month);

        if (readingByCurrentMonth == null) {
            LocalDate lastLocalDate = monthDataMap.get(localDate.getMonth().minus(1).getValue()).getDate();
            return lastLocalDate.plusMonths(1).isBefore(localDate);
        } else {
            return readingByCurrentMonth.getDate().plusMonths(1).isBefore(localDate);
        }
    }
}
