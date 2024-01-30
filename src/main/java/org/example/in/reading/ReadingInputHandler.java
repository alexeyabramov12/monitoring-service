package org.example.in.reading;

import org.example.dto.reading.ColdWaterReadingDto;
import org.example.dto.reading.HeatingReadingDto;
import org.example.dto.reading.HotWaterReadingDto;
import org.example.exception.reading.ReadingException;
import org.example.in.InputHandler;
import org.example.out.reading.ReadingOutputHandler;

import java.time.LocalDate;
import java.util.Scanner;

public class ReadingInputHandler extends InputHandler {

    private final ReadingOutputHandler outputHandler;

    public ReadingInputHandler() {
        outputHandler = new ReadingOutputHandler();
    }

    private final Scanner scanner = new Scanner(System.in);

    public HeatingReadingDto getHeatingReading(String login) {
        outputHandler.displayMassage("Введите показания счётчика отопления:");
        HeatingReadingDto dto = new HeatingReadingDto();
        dto.setIndication(checkIndication(scanner.nextLine().trim()));
        dto.setDate(LocalDate.now());
        dto.setLogin(login);
        return dto;
    }

    public HotWaterReadingDto getHotWaterReading(String login) {
        outputHandler.displayMassage("Введите показания счётчика горячей воды:");
        HotWaterReadingDto dto = new HotWaterReadingDto();
        dto.setIndication(checkIndication(scanner.nextLine().trim()));
        dto.setDate(LocalDate.now());
        dto.setLogin(login);
        return dto;
    }

    public ColdWaterReadingDto getColdWaterReading(String login) {
        outputHandler.displayMassage("Введите показания счётчика холодной воды:");
        ColdWaterReadingDto dto = new ColdWaterReadingDto();
        dto.setIndication(checkIndication(scanner.nextLine().trim()));
        dto.setDate(LocalDate.now());
        dto.setLogin(login);
        return dto;
    }

    private String checkIndication(String indication) {
        if (indication.isEmpty() || !indication.matches("[0-9.,]+"))
            throw new ReadingException("Не коректный ввод показаний");
        else return indication.trim();
    }
}
