package com.astro.smitesolver.bot;

import com.astro.smitesolver.discord.entity.totaldata.TotalGodData;
import com.astro.smitesolver.discord.service.DataFetchingService;
import com.astro.smitesolver.discord.repository.GodNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SolverBot {

    @Autowired
    private DataFetchingService dataFetchingService;

    @Autowired
    private GodNameRepository godNameRepository;

    public TotalGodData getRequestedGod(String name, boolean isHighMMR) {
        int godID = godNameRepository.findByName(name).getGodID();
        return dataFetchingService.getPerformanceData(godID, isHighMMR);
    }

}
