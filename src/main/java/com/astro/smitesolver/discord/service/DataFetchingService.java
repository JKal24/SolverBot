package com.astro.smitesolver.discord.service;

import com.astro.smitesolver.discord.entity.totaldata.TotalGodData;
import com.astro.smitesolver.discord.entity.totaldata.TotalGodDataHighMMR;
import com.astro.smitesolver.discord.entity.totaldata.TotalGodDataLowMMR;
import com.astro.smitesolver.exception.GodNotFoundException;
import com.astro.smitesolver.discord.repository.HighMMRPerformanceRepository;
import com.astro.smitesolver.discord.repository.LowMMRPerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataFetchingService {

    @Autowired
    private HighMMRPerformanceRepository highMMRPerformanceRepository;

    @Autowired
    private LowMMRPerformanceRepository lowMMRPerformanceRepository;

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

        } else {
            for (TotalGodDataLowMMR totalGodDataLowMMR : lowMMRPerformanceRepository.findAll()) {
                if (godID == totalGodDataLowMMR.getGodID()) {
                    return (T) totalGodDataLowMMR;
                }
            }
            throw new GodNotFoundException(String.format("Could not find the given god for the ID, %s", godID));

        }
    }

    public void requestUpdate(int numDays) {
        matchParserService.updateData(numDays);
    }
}
