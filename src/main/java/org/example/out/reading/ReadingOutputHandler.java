package org.example.out.reading;

import org.example.dto.reading.BaseReadingDto;
import org.example.dto.reading.ColdWaterReadingDto;
import org.example.dto.reading.HeatingReadingDto;
import org.example.dto.reading.HotWaterReadingDto;
import org.example.out.OutputHandler;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ReadingOutputHandler extends OutputHandler {

    public void displaySuccessfulAddingReading(String firstName, String lastName) {
        String message = "Добавление показания для пользователя \"".concat(firstName.concat(" ").concat(lastName).concat("\" прошло успешно"));
        System.out.println(message);
    }

    public void displayActualReadingsByUser(String user, List<BaseReadingDto> data) {
        System.out.println("Актуальные показания для пользователя \"".concat(user).concat("\":"));
        System.out.println("Дата: ".concat(data.get(0).getDate().toString()));
        System.out.println(getDataForDisplay(data));
    }

    public void displayReadingsByMonth(String user, List<BaseReadingDto> data) {
        System.out.println("Показания для пользователя \"".concat(user).concat("\":"));
        System.out.println("За месяц: ".concat(data.get(0).getDate().getMonth().toString()));
        System.out.println(getDataForDisplay(data));
    }

    public void displayAllReadingByUser(String user, TreeMap<LocalDate, List<BaseReadingDto>> localDateDataMap) {
        System.out.println("Все показания для пользователя \"".concat(user).concat("\":"));
        for (Map.Entry<LocalDate, List<BaseReadingDto>> entry : localDateDataMap.entrySet()) {
            LocalDate date = entry.getKey();
            System.out.println("Месяц: ".concat(date.getMonth().toString()).concat(" Дата: ").concat(date.toString()));
            System.out.println(getDataForDisplay(entry.getValue()));
        }
    }

    public void displayAllReading(Map<String, List<BaseReadingDto>> userDataMap) {
        for (Map.Entry<String, List<BaseReadingDto>> entry : userDataMap.entrySet()) {
            String user = entry.getKey();
            displayAllReadingByUser(user, new TreeMap<>(entry.getValue().stream()
                    .collect(Collectors.groupingBy(BaseReadingDto::getDate))));
        }
    }

    public void displayAllReadingByMonth(int month, Map<String, List<BaseReadingDto>> userDataMap) {
        System.out.println("История показаний всех пользователей за месяц: ".concat(Month.of(month).toString()));
        displayAllReading(userDataMap);
    }

    private String getDataForDisplay(List<BaseReadingDto> data) {
        StringBuilder builder = new StringBuilder();
        for (BaseReadingDto dto : data) {
            if (dto instanceof HeatingReadingDto) {
                builder.append("\t\t\tПоказания счетчика отопления: ");
                builder.append(dto.getIndication());
                builder.append("\n");
            }

            if (dto instanceof HotWaterReadingDto) {
                builder.append("\t\t\tПоказания счетчика горячей воды: ");
                builder.append(dto.getIndication());
                builder.append("\n");
            }

            if (dto instanceof ColdWaterReadingDto) {
                builder.append("\t\t\tПоказания счетчика холодной воды: ");
                builder.append(dto.getIndication());
                builder.append("\n");
            }
        }
        return builder.toString();
    }


}
