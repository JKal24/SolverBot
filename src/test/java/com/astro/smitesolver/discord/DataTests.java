package com.astro.smitesolver.discord;

import com.astro.smitesolver.discord.entity.dailydata.DailyGodDataHighMMR;
import com.astro.smitesolver.discord.entity.totaldata.TotalGodDataHighMMR;
import com.astro.smitesolver.discord.repository.HighMMRPerformanceRepository;
import com.astro.smitesolver.discord.service.DataCompilationService;
import com.astro.smitesolver.discord.service.DataFetchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataTests {

    @MockBean
    HighMMRPerformanceRepository highMMRPerformanceRepository;

    @Autowired
    private DataCompilationService dataCompilationService;

    @Autowired
    private DataFetchingService dataFetchingService;

    public void getGodData1() {
        dataCompilationService.addHighMMRGodData(5, new DailyGodDataHighMMR(LocalDate.now(), 5, "god5"), 15, 10);
        dataCompilationService.addHighMMRGodData(6, new DailyGodDataHighMMR(LocalDate.now(), 6, "god6"), 15, 11);
        dataCompilationService.addHighMMRGodData(7, new DailyGodDataHighMMR(LocalDate.now(), 7, "god7"), 15, 12);

        assertEquals(highMMRPerformanceRepository.findAll().size(), 1);

        List<TotalGodDataHighMMR> highMMRPerformanceRepositoryList = highMMRPerformanceRepository.findAll();

        for (TotalGodDataHighMMR totalGodDataHighMMR : highMMRPerformanceRepositoryList) {
            assertEquals(totalGodDataHighMMR.getTotalMatchesPlayed(), 15);
        }
    }

}
