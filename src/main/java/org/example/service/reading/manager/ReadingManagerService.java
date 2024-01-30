package org.example.service.reading.manager;

import org.example.dto.reading.BaseReadingDto;
import org.example.dto.reading.ColdWaterReadingDto;
import org.example.dto.reading.HeatingReadingDto;
import org.example.dto.reading.HotWaterReadingDto;
import org.example.in.reading.ReadingInputHandler;
import org.example.model.auth.User;
import org.example.out.reading.ReadingOutputHandler;
import org.example.service.reading.ColdWaterReadingService;
import org.example.service.reading.HeatingReadingService;
import org.example.service.reading.HotWaterReadingService;
import org.example.utils.ValidationMonth;

import java.util.*;
import java.util.stream.Collectors;

public class ReadingManagerService {


    private final HeatingReadingService heatingReadingService;
    private final HotWaterReadingService hotWaterReadingService;
    private final ColdWaterReadingService coldWaterReadingService;
    private final ReadingInputHandler inputHandler;
    private final ReadingOutputHandler outputHandler;

    public ReadingManagerService() {
        inputHandler = new ReadingInputHandler();
        outputHandler = new ReadingOutputHandler();
        heatingReadingService = new HeatingReadingService();
        hotWaterReadingService = new HotWaterReadingService();
        coldWaterReadingService = new ColdWaterReadingService();
    }


    public void addReadings(User user) {
        HeatingReadingDto heatingReadingDto = inputHandler.getHeatingReading(user.getLogin());
        HotWaterReadingDto hotWaterReadingDto = inputHandler.getHotWaterReading(user.getLogin());
        ColdWaterReadingDto coldWaterReadingDto = inputHandler.getColdWaterReading(user.getLogin());
        heatingReadingService.addData(user, heatingReadingDto);
        hotWaterReadingService.addData(user, hotWaterReadingDto);
        coldWaterReadingService.addData(user, coldWaterReadingDto);
        outputHandler.displaySuccessfulAddingReading(user.getFirstName(), user.getLastName());
    }

    public void actualReadingsByUser(User user) {
        List<BaseReadingDto> data = new ArrayList<>();
        data.add(heatingReadingService.getActualData(user));
        data.add(hotWaterReadingService.getActualData(user));
        data.add(coldWaterReadingService.getActualData(user));
        outputHandler.displayActualReadingsByUser(user.getLogin(), data);
    }

    public void readingsByMonth(User user) {
        outputHandler.displayMassage("Введите месяц числом от 1 до 12");
        int month = ValidationMonth.checkMonth(inputHandler.getInput());
        List<BaseReadingDto> data = new ArrayList<>();
        data.add(heatingReadingService.getDataByMonth(user, month));
        data.add(hotWaterReadingService.getDataByMonth(user, month));
        data.add(coldWaterReadingService.getDataByMonth(user, month));
        outputHandler.displayReadingsByMonth(user.getLogin(), data);
    }

    public void allReadingsByUser(User user) {
        List<BaseReadingDto> data = new ArrayList<>();
        data.addAll(heatingReadingService.getAllDataByUser(user));
        data.addAll(hotWaterReadingService.getAllDataByUser(user));
        data.addAll(coldWaterReadingService.getAllDataByUser(user));
        outputHandler.displayAllReadingByUser(user.getLogin(), new TreeMap<>(data.stream()
                .collect(Collectors.groupingBy(BaseReadingDto::getDate))));
    }

    public void allActualReadings() {
        outputHandler.displayMassage("История актуальных показаний всех пользователей:");
        List<BaseReadingDto> data = new ArrayList<>();
        data.addAll(heatingReadingService.getAllActualData());
        data.addAll(hotWaterReadingService.getAllActualData());
        data.addAll(coldWaterReadingService.getAllActualData());
        outputHandler.displayAllReading(data.stream()
                .collect(Collectors.groupingBy(BaseReadingDto::getLogin)));
    }

    public void allReadingsByMonth() {
        outputHandler.displayMassage("Введите месяц числом от 1 до 12");
        int month = ValidationMonth.checkMonth(inputHandler.getInput());
        List<BaseReadingDto> data = new ArrayList<>();
        data.addAll(heatingReadingService.getAllDataByMonth(month));
        data.addAll(hotWaterReadingService.getAllDataByMonth(month));
        data.addAll(coldWaterReadingService.getAllDataByMonth(month));
        outputHandler.displayAllReadingByMonth(month, data.stream()
                .collect(Collectors.groupingBy(BaseReadingDto::getLogin)));
    }

    public void allReadings() {
        outputHandler.displayMassage("История показаний всех пользователей:");
        List<BaseReadingDto> data = new ArrayList<>();
        data.addAll(heatingReadingService.getAllData());
        data.addAll(hotWaterReadingService.getAllData());
        data.addAll(coldWaterReadingService.getAllData());
        outputHandler.displayAllReading(data.stream()
                .collect(Collectors.groupingBy(BaseReadingDto::getLogin)));
    }


}
