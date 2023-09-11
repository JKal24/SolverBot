package com.astro.smiteapi.controllers;

import com.astro.smiteapi.api.SmiteAPI;
import com.astro.smiteapi.exceptions.EntityNotFoundException;
import com.astro.smiteapi.models.characters.GodInfo;
import com.astro.smiteapi.models.characters.auxiliary.LeaderboardInfo;
import com.astro.smiteapi.models.gamedata.PatchInfo;
import com.astro.smiteapi.models.gamedata.ServerInfo;
import com.astro.smiteapi.models.gamedata.UserInfo;
import com.astro.smiteapi.models.gamedata.matches.LeaderboardRanking;
import com.astro.smiteapi.models.items.BaseItemInfo;
import com.astro.smiteapi.models.items.RecommendedItemInfo;
import com.astro.smiteapi.models.player.matches.PlayerMatchData;
import com.astro.smiteapi.repositories.session.SmiteSessionRepository;
import com.astro.smiteapi.services.SmiteService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmiteController {

    @Autowired
    SmiteService smiteService;

    @GetMapping("/api")
    public String getAPIStatus() {
        return smiteService.getAPIStatus();
    }

    @GetMapping("/session")
    public String getSessionStatus() throws EntityNotFoundException {
        return smiteService.getSessionStatus();
    }

    @GetMapping("/data")
    public UserInfo[] getDataUsed() throws EntityNotFoundException {
        return smiteService.getDataUsed();
    }

    @GetMapping("/server")
    public ServerInfo[] getServerStatus() throws EntityNotFoundException {
        return smiteService.getServerStatus();
    }

    @GetMapping("/patch")
    public PatchInfo[] getPatchInfo() throws EntityNotFoundException {
        return smiteService.getPatchInfo();
    }

    @GetMapping("/gods/{languageID}/{names}")
    public GodInfo[] getGods(@PathVariable String languageID, @PathVariable List<String> names) {
        return smiteService.getGods(names, languageID);
    }

    @GetMapping("/god/{godID}/{languageID}")
    public GodInfo getGod(@PathVariable Integer godID, @PathVariable String languageID) {
        return smiteService.getGod(godID, languageID);
    }

    @GetMapping("/leaderboard/{godID}/{modeID}")
    public LeaderboardInfo[] getGodLeaderboard(@PathVariable Integer godID, @PathVariable String modeID) {
        return smiteService.getGodLeaderboard(godID, modeID);
    }

    @GetMapping("/item/recommended/{godID}/{languageID}")
    public RecommendedItemInfo[] getGodRecommendedItems(@PathVariable Integer godID, @PathVariable String languageID) {
        return smiteService.getGodRecommendedItems(godID, languageID);
    }

    @GetMapping("/item/{itemName}/{languageID}")
    public BaseItemInfo getItem(@PathVariable String itemName, @PathVariable String languageID) {
        return smiteService.getItem(itemName, languageID);
    }

    @GetMapping("/items/{languageID}")
    public BaseItemInfo[] getItems(@PathVariable String languageID) {
        return smiteService.getItems(languageID);
    }

    @GetMapping("/god/skins/{godID}/{languageID}")
    public String[] getGodSkins(@PathVariable Integer godID, @PathVariable String languageID) {
        return smiteService.getGodSkins(godID, languageID);
    }

    @GetMapping("/leaderboard/rankings/{modeID}/{rankingID}/{round}")
    public LeaderboardRanking[] getLeaderboardRankings(@PathVariable String modeID, @PathVariable String rankingID, @PathVariable Integer round) {
        return smiteService.getLeaderboardRankings(modeID, rankingID, round);
    }

    @GetMapping("/match/id/{matchIDs}")
    public List<PlayerMatchData> getMultipleMatchData(@PathVariable List<String> matchIDs) {
        return smiteService.getMultipleMatchData(matchIDs);
    }
}
