package org.example.service.reading;

import org.example.dto.reading.BaseReadingDto;
import org.example.exception.reading.ReadingException;
import org.example.mapper.reading.ReadingMapper;
import org.example.model.auth.Role;
import org.example.model.auth.User;
import org.example.model.reading.BaseReading;
import org.example.repository.reading.ReadingRepository;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class ReadingService<T extends BaseReading, V extends BaseReadingDto> {

    private final ReadingMapper<T, V> mapper;
    private final ReadingRepository<T> repository;
    private static final String NO_READINGS = "Показания отсутствуют";


    public ReadingService() {
        mapper = new ReadingMapper();
        repository = new ReadingRepository<>();
    }

    public BaseReadingDto getActualDataByUser(User user) throws ReadingException {
        String errorMassage = "\nАктуальные показания для пользователя \"".concat(user.getLogin()).concat("\" отсутствуют\n");
        if (!repository.exist(user)) {
            throw new ReadingException(errorMassage);
        }

        Collection<T> readings = repository.getByUser(user).values();
        Optional<T> readingOptional = readings.stream().max(Comparator.comparing(BaseReading::getDate));

        if (readingOptional.isPresent()) {
            return mapper.readingToDto(readings.stream().max(Comparator.comparing(BaseReading::getDate)).get());
        } else throw new ReadingException(errorMassage);
    }

    public List<V> getAllActualData() throws ReadingException {
        checkEmpty();
        List<T> readingList = new ArrayList<>();

        for (Map<Integer, T> monthReadingMap : repository.getAll()) {
            readingList.add(monthReadingMap.values().stream()
                    .max(Comparator.comparing(BaseReading::getDate))
                    .orElseThrow(() -> new ReadingException(NO_READINGS)));
        }
        return readingList.stream()
                .map(mapper::readingToDto)
                .toList();
    }

    public List<V> getAllDataByUser(User user) {
        String errorMassage = "\nПоказания для пользователя \"".concat(user.getLogin()).concat("\" отсутствуют\n");
        if (!repository.exist(user)) {
            throw new ReadingException(errorMassage);
        }

        return repository.getByUser(user).values().stream().map(mapper::readingToDto).toList();
    }

    public List<V> getAllData() throws ReadingException {
        checkEmpty();
        List<T> readingList = new ArrayList<>();

        for (Map<Integer, T> monthReadingMap : repository.getAll()) {
            readingList.addAll(monthReadingMap.values());
        }
        return readingList.stream()
                .map(mapper::readingToDto)
                .toList();
    }

    public V getDataByMonth(User user, int month) throws ReadingException {
        String errorMassage = "Показания для пользователя \"".concat(user.getLogin()).concat("\" за месяц ").concat(Month.of(month).toString()).concat(" отсутствуют");

        if (!repository.exist(user)) {
            throw new ReadingException(errorMassage);
        }

        T reading = repository.getByUser(user).get(month);

        if (reading == null) {
            throw new ReadingException(errorMassage);
        } else return mapper.readingToDto(reading);
    }

    public List<V> getAllDataByMonth(int month) throws ReadingException {
        String errorMassage = "Показания за месяц ".concat(Month.of(month).toString()).concat(" отсутствуют");
        checkEmpty();
        List<T> readingList = new ArrayList<>();

        for (Map<Integer, T> monthReadingMap : repository.getAll()) {
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

    public void addData(User user, V dto) throws ReadingException {
        T reading = mapper.dtoToReading(dto);
        String errorMassage = "ОШИБКА! Показания можно подавать один раз в месяц.\n";

        if (!checkToAdd(user, reading.getDate()) || user.getRole().equals(Role.ADMIN)) {
            throw new ReadingException(errorMassage);
        }

        LocalDate localDate = reading.getDate();
        Integer month = localDate.getMonth().getValue();
        Map<Integer, T> dataMonthMap = repository.getByUser(user) == null ? new HashMap<>() : repository.getByUser(user);
        dataMonthMap.put(month, reading);
        repository.add(user, dataMonthMap);
    }

    private boolean checkToAdd(User user, LocalDate localDate) {
        if (!repository.exist(user)) {
            return true;
        }

        Map<Integer, T> monthDataMap = repository.getByUser(user);
        Integer month = localDate.getMonth().getValue();
        return !monthDataMap.containsKey(month);
    }

    private void checkEmpty() {
        if (repository.empty()) {
            throw new ReadingException(NO_READINGS);
        }
    }
}
