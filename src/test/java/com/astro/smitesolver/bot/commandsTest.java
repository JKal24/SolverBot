package com.astro.smitesolver.bot;

import com.astro.smitesolver.discord.entity.totaldata.*;
import com.astro.smitesolver.discord.repository.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class commandsTest {


    @MockBean
    HighMMRPerformanceRepository highMMRPerformanceRepository;

    @MockBean
    LowMMRPerformanceRepository lowMMRPerformanceRepository;

    @MockBean
    HighMMRBanRateRepository highMMRBanRateRepository;

    @MockBean
    HighMMRPickRateRepository highMMRPickRateRepository;

    @MockBean
    HighMMRWinRateRepository highMMRWinRateRepository;

    @MockBean
    LowMMRBanRateRepository lowMMRBanRateRepository;

    @MockBean
    LowMMRPickRateRepository lowMMRPickRateRepository;

    @MockBean
    LowMMRWinRateRepository lowMMRWinRateRepository;

    @MockBean
    SolverBot bot = new SolverBot();

    @BeforeAll
    public void fillRepositories() {
        highMMRWinRateRepository.save(new WinRateRankingHighMMR(1, "god1", 0.54));
        highMMRWinRateRepository.save(new WinRateRankingHighMMR(2, "god2", 0.55));
        highMMRPickRateRepository.save(new PickRateRankingHighMMR(1, "god1", 0.54));
        highMMRPickRateRepository.save(new PickRateRankingHighMMR(2, "god2", 0.55));
        highMMRBanRateRepository.save(new BanRateRankingHighMMR(1, "god1", 0.54));
        highMMRBanRateRepository.save(new BanRateRankingHighMMR(2, "god2", 0.55));


        lowMMRWinRateRepository.save(new WinRateRankingLowMMR(1, "god1", 0.54));
        lowMMRWinRateRepository.save(new WinRateRankingLowMMR(2, "god2", 0.55));
        lowMMRPickRateRepository.save(new PickRateRankingLowMMR(1, "god1", 0.54));
        lowMMRPickRateRepository.save(new PickRateRankingLowMMR(2, "god2", 0.55));
        lowMMRBanRateRepository.save(new BanRateRankingLowMMR(1, "god1", 0.54));
        lowMMRBanRateRepository.save(new BanRateRankingLowMMR(2, "god2", 0.55));
    }

    @Test
    public void getGod1() {
        TotalGodData godData = bot.getRequestedGod("Sol", true);

        // Must be a win rate between 1.0 and 0.0 unless pick rate is 0, then it is 0.0.

        assertTrue((godData.getMovingPickRate() == 0 && godData.getMovingWinRate() == 0) ||
                (godData.getMovingPickRate() != 0 && godData.getMovingWinRate() >= 0 && godData.getMovingWinRate() <= 1.0));

    }

    @Test
    public void getLeaderboard1() {

        List<WinRateRankingHighMMR> winRateLeaderboard = (List<WinRateRankingHighMMR>) bot.getWinRateLeaderboard(true);

        for (WinRateRankingHighMMR winRateRankingHighMMR : winRateLeaderboard) {
            assertTrue(winRateRankingHighMMR.getWinRate() >= 0.0);
        }
    }

    @Test
    public void getLeaderboard2() {
        List<BanRateRankingLowMMR> banRateRankingLowMMRS = (List<BanRateRankingLowMMR>) bot.getBanRateLeaderboard(false);

        for (BanRateRankingLowMMR banRateRankingLowMMR : banRateRankingLowMMRS) {
            assertTrue(banRateRankingLowMMR.getBanRate() >= 0.0);
        }
    }

    @Test
    public void getLeaderboard3() {
        List<PickRateRankingHighMMR> pickRateLeaderboard = (List<PickRateRankingHighMMR>) bot.getPickRateLeaderboard(true);

        for (PickRateRankingHighMMR pickRateRankingHighMMR : pickRateLeaderboard) {
            assertTrue(pickRateRankingHighMMR.getPickRate() >= 0.0);
        }
    }

}
