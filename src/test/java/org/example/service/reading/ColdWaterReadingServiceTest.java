package org.example.service.reading;

import org.example.dto.reading.ColdWaterReadingDto;
import org.example.exception.reading.ReadingException;
import org.example.model.auth.Role;
import org.example.model.auth.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ColdWaterReadingServiceTest {

    private ColdWaterReadingService readingService;
    private User user1;
    private User user2;
    private ColdWaterReadingDto dto1;
    private ColdWaterReadingDto dto2;
    private ColdWaterReadingDto dto3;
    private List<ColdWaterReadingDto> expect;


    @BeforeEach
    public void setUp() {
        readingService = new ColdWaterReadingService();
        user1 = User.builder()
                .firstName("Алекс")
                .lastName("Иванов")
                .login("Рокосовского 12")
                .password("123")
                .role(Role.USER)
                .build();

        user2 = User.builder()
                .firstName("Алексей")
                .lastName("сидоров")
                .login("Крылова 12")
                .password("123")
                .role(Role.USER)
                .build();


        dto1 = new ColdWaterReadingDto();
        dto1.setDate(LocalDate.now());
        dto1.setIndication("123.2");
        dto1.setLogin(user1.getLogin());

        dto2 = new ColdWaterReadingDto();
        dto2.setDate(LocalDate.now());
        dto2.setIndication("123.2");
        dto2.setLogin(user1.getLogin());

        dto3 = new ColdWaterReadingDto();
        dto3.setDate(LocalDate.now());
        dto3.setIndication("123.2");
        dto3.setLogin(user1.getLogin());

        expect = new ArrayList<>();
    }

    @Test
    @DisplayName("Test add one dto with normal date")
    public void ifOneDtoWithNormalDate_addData_thenAddingData() {
        readingService.addData(user1, dto1);
        expect.add(dto1);
        assertEquals(expect, readingService.getAllDataByUser(user1));
    }

    @Test
    @DisplayName("Test add two dto with normal date")
    public void ifTwoDtoWithNormalDate_addData_thenAddingData() {
        dto2.setDate(LocalDate.now().plusMonths(1));

        readingService.addData(user1, dto1);
        readingService.addData(user1, dto2);
        expect.add(dto1);
        expect.add(dto2);

        assertEquals(expect, readingService.getAllDataByUser(user1));
    }

    @Test
    @DisplayName("Test add three dto with normal date")
    public void ifThreeDtoWithNormalDate_addData_thenAddingData() {
        dto2.setDate(LocalDate.now().plusMonths(1));
        dto3.setDate(LocalDate.now().plusMonths(2));
        readingService.addData(user1, dto1);
        readingService.addData(user1, dto2);
        readingService.addData(user1, dto3);

        expect.add(dto1);
        expect.add(dto2);
        expect.add(dto3);

        assertEquals(expect, readingService.getAllDataByUser(user1));
    }

    @Test
    @DisplayName("Test add two dto with the same date")
    public void ifTwoDtoWithSameData_addData_thenException() {
        readingService.addData(user1, dto1);
        assertThrows(ReadingException.class, () -> readingService.addData(user1, dto2));
    }

    @Test
    @DisplayName("Test add two dto with the same month")
    public void ifTwoDtoWithSameMonth_addData_thenException() {
        dto1.setDate(LocalDate.of(24, 1, 12));
        dto2.setDate(LocalDate.of(24, 1, 20));

        readingService.addData(user1, dto1);
        assertThrows(ReadingException.class, () -> readingService.addData(user1, dto2));
    }

    @Test
    @DisplayName("Test get all data by user with normal adding one dto")
    public void ifAddOneDto_getAllDataByUser_thenDataByUser() {
        readingService.addData(user1, dto1);
        expect.add(dto1);
        assertEquals(expect, readingService.getAllDataByUser(user1));
    }

    @Test
    @DisplayName("Test get all data by user with normal adding two dto")
    public void ifAddTwoDto_getAllDataByUser_thenDataByUser() {
        dto2.setDate(LocalDate.now().plusMonths(1));

        readingService.addData(user1, dto1);
        readingService.addData(user1, dto2);
        expect.add(dto1);
        expect.add(dto2);

        assertEquals(expect, readingService.getAllDataByUser(user1));
    }

    @Test
    @DisplayName("Test get all data by user without adding")
    public void ifWithoutAddingDto_getAllDataByUser_thenException() {
        assertThrows(ReadingException.class, () -> readingService.getAllDataByUser(user1));
    }

    @Test
    @DisplayName("Test get actual data by user with normal adding one dto")
    public void ifAddOneDto_getActualDataByUser_thenActualDataByUser() {
        readingService.addData(user1, dto1);
        assertEquals(dto1, readingService.getActualDataByUser(user1));
    }

    @Test
    @DisplayName("Test get actual data by user with normal adding two dto")
    public void ifAddTwoDto_getActualDataByUser_thenActualDataByUser() {
        readingService.addData(user1, dto1);
        dto2.setDate(LocalDate.now().plusMonths(1));
        readingService.addData(user1, dto2);
        assertEquals(dto2, readingService.getActualDataByUser(user1));
    }

    @Test
    @DisplayName("Test get all data by user without adding")
    public void ifWithoutAddingDto_getActualDataByUser_thenException() {
        assertThrows(ReadingException.class, () -> readingService.getActualDataByUser(user1));
    }

    @Test
    @DisplayName("Test get actual data by user with normal adding one user")
    public void ifAdd0neUser_getAllActualData_thenAllActual() {
        readingService.addData(user1, dto1);
        expect.add(dto1);
        assertEquals(expect, readingService.getAllActualData());
    }

    @Test
    @DisplayName("Test get actual data by user with normal adding two users")
    public void ifAddTwoUser_getAllActualData_thenAllActualData() {
        dto2.setLogin(user2.getLogin());
        readingService.addData(user1, dto1);
        readingService.addData(user2, dto2);
        expect.add(dto1);
        expect.add(dto2);
        assertEquals(expect, readingService.getAllActualData());
    }

    @Test
    @DisplayName("Test get all data by user without adding")
    public void ifWithoutAddingDto_getAllActualData_thenException() {
        assertThrows(ReadingException.class, () -> readingService.getAllActualData());
    }

    @Test
    @DisplayName("Test get data by month with normal adding one dto")
    public void ifAddOneDto_getDataByMonth_thenDataByMonth() {
        readingService.addData(user1, dto1);
        int month = dto1.getDate().getMonth().getValue();
        assertEquals(dto1, readingService.getDataByMonth(user1, month));
    }

    @Test
    @DisplayName("Test get data by month with normal adding two dto")
    public void ifAddTwoDto_getDataByMonth_thenDataByMonth() {
        readingService.addData(user1, dto1);
        dto2.setDate(LocalDate.now().plusMonths(1));
        readingService.addData(user1, dto2);
        int month = dto2.getDate().getMonth().getValue();
        assertEquals(dto2, readingService.getDataByMonth(user1, month));
    }

    @Test
    @DisplayName("Test get data by month without adding")
    public void ifWrongMonth_getDataByMonth_thenException() {
        readingService.addData(user1, dto1);
        int month = dto2.getDate().plusMonths(1).getMonth().getValue();
        assertThrows(ReadingException.class, () -> readingService.getDataByMonth(user1, month));
    }

    @Test
    @DisplayName("Test get data by month without adding")
    public void ifWithoutAdding_getDataByMonth_thenException() {
        assertThrows(ReadingException.class, () -> readingService.getDataByMonth(user1, 1));
    }

    @Test
    @DisplayName("Test get all data by month with normal adding one user")
    public void ifAddOneUser_getAllDataByMonth_thenAllDataByMonth() {
        readingService.addData(user1, dto1);
        expect.add(dto1);
        int month = dto1.getDate().getMonth().getValue();
        assertEquals(expect, readingService.getAllDataByMonth(month));
    }

    @Test
    @DisplayName("Test get all data by month with normal adding two user")
    public void ifAddTwoUser_getAllDataByMonth_thenAllDataByMonth() {
        dto2.setLogin(user2.getLogin());
        readingService.addData(user1, dto1);
        readingService.addData(user2, dto2);
        expect.add(dto1);
        expect.add(dto2);
        int month = dto1.getDate().getMonth().getValue();
        assertEquals(expect, readingService.getAllDataByMonth(month));
    }

    @Test
    @DisplayName("Test get all data by month with wrong month")
    public void ifWrongMonth_getAllDataByMonth_thenException() {
        readingService.addData(user1, dto1);
        int month = dto2.getDate().plusMonths(1).getMonth().getValue();
        assertThrows(ReadingException.class, () -> readingService.getAllDataByMonth(month));
    }

    @Test
    @DisplayName("Test get all data by month without adding")
    public void ifWithoutAdding_getAllDataByMonth_thenException() {
        assertThrows(ReadingException.class, () -> readingService.getAllDataByMonth(1));
    }
}
