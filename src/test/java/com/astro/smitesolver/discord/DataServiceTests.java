package com.astro.smitesolver.discord;

import com.astro.smitesolver.discord.entity.auxillary.GodName;
import com.astro.smitesolver.discord.entity.dailydata.DailyGodDataHighMMR;
import com.astro.smitesolver.discord.entity.dailydata.DailyGodDataLowMMR;
import com.astro.smitesolver.discord.entity.totaldata.*;
import com.astro.smitesolver.discord.repository.*;
import com.astro.smitesolver.discord.service.DataCompilationService;
import com.astro.smitesolver.discord.service.DataFetchingService;
import com.astro.smitesolver.discord.service.MatchParserService;
import com.astro.smitesolver.discord.service.UpdateService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DataServiceTests {

    // Services

    @Autowired
    DataCompilationService dataCompilationService;

    @Autowired
    DataFetchingService dataFetchingService;

    @Autowired
    MatchParserService matchParserService;

    @Autowired
    UpdateService updateService;

    // Repositories

    @Autowired
    private GodNameRepository godNameRepository;

    @Autowired
    private DailyLowMMRDailyGodDataRepository dailyLowMMRGodDataRepository;

    @Autowired
    private DailyHighMMRDailyGodDataRepository dailyHighMMRGodDataRepository;

    @Autowired
    private HighMMRPerformanceRepository highMMRPerformanceRepository;

    @Autowired
    private LowMMRPerformanceRepository lowMMRPerformanceRepository;

    @Autowired
    private HighMMRBanRateRepository highMMRBanRateRepository;

    @Autowired
    private HighMMRPickRateRepository highMMRPickRateRepository;

    @Autowired
    private HighMMRWinRateRepository highMMRWinRateRepository;

    @Autowired
    private LowMMRBanRateRepository lowMMRBanRateRepository;

    @Autowired
    private LowMMRPickRateRepository lowMMRPickRateRepository;

    @Autowired
    private LowMMRWinRateRepository lowMMRWinRateRepository;

    // Initializes repositories with some base data

    @BeforeAll
    public void fillRepositories() {
        matchParserService.updateResources();

        godNameRepository.save(new GodName(-18, "1"));
        godNameRepository.save(new GodName(-19, "2"));

        dataCompilationService.addHighMMRGodData(-18,
                new DailyGodDataHighMMR(LocalDate.now(), -18, "1", 5, 3, 1, new HashMap<>(),
                        new HashMap<>(), new HashMap<>(), 50, 50, 50),
                10, 10);

        dataCompilationService.addHighMMRGodData(-19,
                new DailyGodDataHighMMR(LocalDate.now(), -19, "2", 6, 4, 2, new HashMap<>(),
                        new HashMap<>(), new HashMap<>(), 50, 50, 50),
                10, 10);

        dataCompilationService.addLowMMRGodData(-18,
                new DailyGodDataLowMMR(LocalDate.now(), -18, "1", 9, 1, 1, new HashMap<>(),
                        new HashMap<>(), new HashMap<>(), 50, 50, 50),
                10, 10);

        dataCompilationService.addLowMMRGodData(-19,
                new DailyGodDataLowMMR(LocalDate.now(), -19, "2", 1, 0, 9, new HashMap<>(),
                        new HashMap<>(), new HashMap<>(), 50, 50, 50),
                10, 10);

        dataCompilationService.configureLeaderboards();

        Assumptions.assumeFalse(updateService.getMostRecentUpdate() == null);
        Assumptions.assumeFalse(updateService.getUpdatedItemList().size() == 0);
        Assumptions.assumeFalse(updateService.getGods().size() == 0);
    }

    @Test
    public void getGodData1() {
        dataCompilationService.addHighMMRGodData(-5, new DailyGodDataHighMMR(LocalDate.now(), -5, "god5"), 15, 10);
        dataCompilationService.addHighMMRGodData(-6, new DailyGodDataHighMMR(LocalDate.now(), -6, "god6"), 15, 11);
        dataCompilationService.addHighMMRGodData(-7, new DailyGodDataHighMMR(LocalDate.now(), -7, "god7"), 15, 12);

        // Use negative IDs to test, since real IDs will be positive and random.

        dataCompilationService.addHighMMRGodData(-8,
                new DailyGodDataHighMMR(LocalDate.now(), -8, "stats", 3, 2, 2, new HashMap<>(), new HashMap<>(), new HashMap<>(), 50, 50, 50),
                15, 12);

        dataCompilationService.addHighMMRGodData(-9,
                new DailyGodDataHighMMR(LocalDate.now(), -9, "wrong", 5, 7, 10, new HashMap<>(), new HashMap<>(), new HashMap<>(), 50, 50, 50),
                4, 3);

        assertTrue(highMMRPerformanceRepository.findAll().size() > 0);

        List<TotalGodDataHighMMR> highMMRPerformanceRepositoryList = highMMRPerformanceRepository.findAll();

        // Test specific god IDs

        TotalGodDataHighMMR godData1 = dataFetchingService.getPerformanceData(-8, true);
        TotalGodDataHighMMR godData2 = dataFetchingService.getPerformanceData(-9, true);

        assertEquals(godData1.getTotalMatchesPlayed(), 3);
        assertEquals(godData1.getMovingPickRate(), 3.0/15.0);

        assertEquals(godData2.getAverageBasicAttackDamage(), 50);
        assertEquals(godData2.getMovingBanRate(), 10.0/4.0);
    }

    @Test
    public void getGodData2() {
        dataCompilationService.addLowMMRGodData(-5, new DailyGodDataLowMMR(LocalDate.now(), -5, "god5"), 15, 10);
        dataCompilationService.addLowMMRGodData(-6, new DailyGodDataLowMMR(LocalDate.now(), -6, "god6"), 15, 11);
        dataCompilationService.addLowMMRGodData(-7, new DailyGodDataLowMMR(LocalDate.now(), -7, "god7"), 15, 12);

        assertTrue(lowMMRPerformanceRepository.findAll().size() > 0);

    }

    @Test
    public void testRates() {

        List<PickRateRankingHighMMR> rateRankingHighMMRS = (List<PickRateRankingHighMMR>) dataFetchingService.getPickRates(true);
        List<PickRateRankingLowMMR> rateRankingLowMMRS = (List<PickRateRankingLowMMR>) dataFetchingService.getPickRates(false);

        assertEquals(5.0 / 10.0, rateRankingHighMMRS.get(0).getPickRate());
        assertEquals(6.0 / 10.0, rateRankingHighMMRS.get(1).getPickRate());

        assertEquals(1.0 / 10.0, rateRankingLowMMRS.get(0).getPickRate());
        assertEquals(9.0 / 10.0, rateRankingLowMMRS.get(1).getPickRate());

    }

    @Test
    public void testRates2() {

        List<WinRateRankingHighMMR> rateRankingHighMMRS = (List<WinRateRankingHighMMR>) dataFetchingService.getWinRates(true);
        List<WinRateRankingLowMMR> rateRankingLowMMRS = (List<WinRateRankingLowMMR>) dataFetchingService.getWinRates(false);

        assertEquals(3.0 / 5.0, rateRankingHighMMRS.get(0).getWinRate());
        assertEquals(4.0 / 6.0, rateRankingHighMMRS.get(1).getWinRate());

        assertEquals(0.0, rateRankingLowMMRS.get(0).getWinRate());
        assertEquals(1.0 / 9.0, rateRankingLowMMRS.get(1).getWinRate());

    }

    @Test
    public void testRates3() {

        List<BanRateRankingHighMMR> rateRankingHighMMRS = (List<BanRateRankingHighMMR>) dataFetchingService.getBanRates(true);
        List<BanRateRankingLowMMR> rateRankingLowMMRS = (List<BanRateRankingLowMMR>) dataFetchingService.getBanRates(false);

        assertEquals(1.0 / 10.0, rateRankingHighMMRS.get(0).getBanRate());
        assertEquals(2.0 / 10.0, rateRankingHighMMRS.get(1).getBanRate());

        assertEquals(1.0 / 10.0, rateRankingLowMMRS.get(0).getBanRate());
        assertEquals(9.0 / 10.0, rateRankingLowMMRS.get(1).getBanRate());

    }

    @AfterAll
    public void emptyRepositories() {

        for (DailyGodDataLowMMR data : dailyLowMMRGodDataRepository.findAll()) {
            if (data.getGodID() < 0) dailyLowMMRGodDataRepository.delete(data);
        }

        for (DailyGodDataHighMMR data : dailyHighMMRGodDataRepository.findAll()) {
            if (data.getGodID() < 0) dailyHighMMRGodDataRepository.delete(data);
        }

        for (TotalGodDataHighMMR data : highMMRPerformanceRepository.findAll()) {
            if (data.getGodID() < 0) highMMRPerformanceRepository.delete(data);
        }

        for (TotalGodDataLowMMR data : lowMMRPerformanceRepository.findAll()) {
            if (data.getGodID() < 0) lowMMRPerformanceRepository.delete(data);
        }

        for (BanRateRankingHighMMR data : highMMRBanRateRepository.findAll()) {
            if (data.getGodID() < 0) highMMRBanRateRepository.delete(data);
        }

        for (PickRateRankingHighMMR data : highMMRPickRateRepository.findAll()) {
            if (data.getGodID() < 0) highMMRPickRateRepository.delete(data);
        }

        for (WinRateRankingHighMMR data : highMMRWinRateRepository.findAll()) {
            if (data.getGodID() < 0) highMMRWinRateRepository.delete(data);
        }

        for (BanRateRankingLowMMR data : lowMMRBanRateRepository.findAll()) {
            if (data.getGodID() < 0) lowMMRBanRateRepository.delete(data);
        }

        for (PickRateRankingLowMMR data : lowMMRPickRateRepository.findAll()) {
            if (data.getGodID() < 0) lowMMRPickRateRepository.delete(data);
        }

        for (WinRateRankingLowMMR data : lowMMRWinRateRepository.findAll()) {
            if (data.getGodID() < 0) lowMMRWinRateRepository.delete(data);
        }

        for (GodName data : godNameRepository.findAll()) {
            if (data.getGodID() < 0) godNameRepository.delete(data);
        }

    }
}
