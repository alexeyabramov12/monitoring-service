package org.example.mapper.reading;

import org.example.dto.reading.BaseReadingDto;
import org.example.dto.reading.ColdWaterReadingDto;
import org.example.dto.reading.HeatingReadingDto;
import org.example.dto.reading.HotWaterReadingDto;
import org.example.model.reading.BaseReading;
import org.example.model.reading.ColdWaterReading;
import org.example.model.reading.HeatingReading;
import org.example.model.reading.HotWaterReading;

public class ReadingMapper<T extends BaseReading, V extends BaseReadingDto> {

    public V readingToDto(T reading) {
        BaseReadingDto dto = null;
        if (reading instanceof HeatingReading) {
            dto = new HeatingReadingDto();
            dto.setDate(reading.getDate());
            dto.setIndication(reading.getIndication());
            dto.setLogin(reading.getLogin());
        }

        if (reading instanceof HotWaterReading) {
            dto = new HotWaterReadingDto();
            dto.setDate(reading.getDate());
            dto.setIndication(reading.getIndication());
            dto.setLogin(reading.getLogin());
        }
        if (reading instanceof ColdWaterReading) {
            dto = new ColdWaterReadingDto();
            dto.setDate(reading.getDate());
            dto.setIndication(reading.getIndication());
            dto.setLogin(reading.getLogin());
        }

        return (V) dto;
    }

    public T dtoToReading(V dto) {
        BaseReading reading = null;
        if (dto instanceof HeatingReadingDto) {
            reading = new HeatingReading();
            reading.setDate(dto.getDate());
            reading.setIndication(dto.getIndication());
            reading.setLogin(dto.getLogin());
        }

        if (dto instanceof HotWaterReadingDto) {
            reading = new HotWaterReading();
            reading.setDate(dto.getDate());
            reading.setIndication(dto.getIndication());
            reading.setLogin(dto.getLogin());
        }

        if (dto instanceof ColdWaterReadingDto) {
            reading = new ColdWaterReading();
            reading.setDate(dto.getDate());
            reading.setIndication(dto.getIndication());
            reading.setLogin(dto.getLogin());
        }

        return (T) reading;
    }
}
