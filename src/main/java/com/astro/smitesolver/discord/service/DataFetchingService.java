package com.astro.smitesolver.discord.service;

import com.astro.smitesolver.config.BanRateComparator;
import com.astro.smitesolver.config.PickRateComparator;
import com.astro.smitesolver.config.WinRateComparator;
import com.astro.smitesolver.discord.entity.totaldata.*;
import com.astro.smitesolver.discord.repository.*;
import com.astro.smitesolver.exception.GodNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class DataFetchingService {

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

    @Autowired
    private MatchParserService matchParserService;

    public <T extends TotalGodData> T getPerformanceData(int godID, boolean highMMR) {
        if (highMMR) {
            for (TotalGodDataHighMMR totalGodDataHighMMR : highMMRPerformanceRepository.findAll()) {
                if (godID == totalGodDataHighMMR.getGodID()) {
                    return (T) totalGodDataHighMMR;
                }
            }
            throw new GodNotFoundException(String.format("Could not find the given god for the ID, %s", godID));

        }
        for (TotalGodDataLowMMR totalGodDataLowMMR : lowMMRPerformanceRepository.findAll()) {
            if (godID == totalGodDataLowMMR.getGodID()) {
                return (T) totalGodDataLowMMR;
            }
        }
        throw new GodNotFoundException(String.format("Could not find the given god for the ID, %s", godID));

    }

    public List<? extends WinRateRanking> getWinRates(boolean highMMR) {
        if (highMMR) {
            List<WinRateRankingHighMMR> highMMRList = highMMRWinRateRepository.findAll();
            highMMRList.sort(new WinRateComparator<>());
            return highMMRList;
        }

        List<WinRateRankingLowMMR> lowMMRList = lowMMRWinRateRepository.findAll();
        lowMMRList.sort(new WinRateComparator<>());
        return lowMMRList;
    }

    public List<? extends PickRateRanking> getPickRates(boolean highMMR) {
        if (highMMR) {
            List<PickRateRankingHighMMR> highMMRList = highMMRPickRateRepository.findAll();
            highMMRList.sort(new PickRateComparator<>());
            return highMMRList;
        }

        List<PickRateRankingLowMMR> lowMMRList = lowMMRPickRateRepository.findAll();
        lowMMRList.sort(new PickRateComparator<>());
        return lowMMRList;
    }

    public List<? extends BanRateRanking> getBanRates(boolean highMMR) {
        if (highMMR) {
            List<BanRateRankingHighMMR> highMMRList = highMMRBanRateRepository.findAll();
            highMMRList.sort(new BanRateComparator<>());
            return highMMRList;
        }

        List<BanRateRankingLowMMR> lowMMRList = lowMMRBanRateRepository.findAll();
        lowMMRList.sort(new BanRateComparator<>());
        return lowMMRList;
    }

    public void requestUpdate(int numDays) {
        matchParserService.updateData(numDays);
    }
}
