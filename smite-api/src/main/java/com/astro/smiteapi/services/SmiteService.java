package com.astro.smiteapi.services;

import com.astro.smiteapi.api.SmiteAPI;
import com.astro.smiteapi.exceptions.EntityNotFoundException;
import com.astro.smiteapi.models.characters.GodInfo;
import com.astro.smiteapi.models.characters.auxiliary.LeaderboardInfo;
import com.astro.smiteapi.models.gamedata.ClanInfo;
import com.astro.smiteapi.models.gamedata.PatchInfo;
import com.astro.smiteapi.models.gamedata.SeasonInfo;
import com.astro.smiteapi.models.gamedata.ServerInfo;
import com.astro.smiteapi.models.gamedata.UserInfo;
import com.astro.smiteapi.models.gamedata.matches.EsportsMatchInfo;
import com.astro.smiteapi.models.gamedata.matches.LeaderboardRanking;
import com.astro.smiteapi.models.gamedata.matches.MatchInfo;
import com.astro.smiteapi.models.gamedata.matches.MotdInfo;
import com.astro.smiteapi.models.gamedata.matches.TopMatchInfo;
import com.astro.smiteapi.models.items.BaseItemInfo;
import com.astro.smiteapi.models.items.RecommendedItemInfo;
import com.astro.smiteapi.models.player.PlayerID;
import com.astro.smiteapi.models.player.PlayerInfo;
import com.astro.smiteapi.models.player.PlayerNotFoundException;
import com.astro.smiteapi.models.player.PlayerStatistics;
import com.astro.smiteapi.models.player.PlayerStatus;
import com.astro.smiteapi.models.player.auxiliary.ClanMemberInfo;
import com.astro.smiteapi.models.player.auxiliary.FriendsInfo;
import com.astro.smiteapi.models.player.auxiliary.SearchedPlayer;
import com.astro.smiteapi.models.player.matches.PlayTimeStatistics;
import com.astro.smiteapi.models.player.matches.PlayerLiveMatchData;
import com.astro.smiteapi.models.player.matches.PlayerMatchData;
import com.astro.smiteapi.models.player.matches.PlayerMatchHistory;
import com.astro.smiteapi.models.player.matches.PlayerQueueStatistics;
import com.astro.smiteapi.repositories.session.SmiteSessionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmiteService {

    private final static Logger LOGGER = Logger.getLogger(SmiteAPI.class.getName());

    private static final int DATA_DAY_LIMIT = 30;

    @Value("${smite.api}")
    private String apiUri;

    @Value("${smite.dev-id}")
    private String devID;

    @Value("${smite.auth-key}")
    private String authKey;

    @Autowired
    private SmiteAPI api;

    @Autowired
    private SmiteSessionRepository sessionRepository;

    @PostConstruct
    private void initializeAPI() {
        this.api.setCredentials(apiUri, devID, authKey);
    }

    public String getAPIStatus() {
        return api.getAPIStatus();
    }

    public String getSessionStatus() throws EntityNotFoundException {
        return api.getSessionStatus();
    }

    public UserInfo[] getDataUsed() throws EntityNotFoundException {
        return api.getDataUsed();
    }

    public ServerInfo[] getServerStatus() throws EntityNotFoundException {
        return api.getServerStatus();
    }

    public PatchInfo[] getPatchInfo() throws EntityNotFoundException {
        return api.getPatchInfo();
    }

    public GodInfo[] getGods(List<String> names, String languageID) {
        String[] namesArr = new String[names.size()];
        int index = 0;
        for (String name : names) namesArr[index++] = name;

        try {
            if (names.size() == 0) return api.getGods(languageID);
            else return api.getGods(namesArr, languageID);
        } catch (EntityNotFoundException e) {
            return new GodInfo[0];
        }
    }

    public GodInfo getGod(Integer id, String language) {
        try {
            return api.getGod(id, language);
        } catch (Exception e) {
            return null;
        }
    }

    public LeaderboardInfo[] getGodLeaderboard(Integer godID, String modeID) {
        try {
            return api.getGodLeaderboard(godID, modeID);
        } catch (EntityNotFoundException e) {
            return new LeaderboardInfo[0];
        } catch (Exception e) {
            return null;
        }
    }

    public RecommendedItemInfo[] getGodRecommendedItems(Integer godID, String languageID) {
        try {
            return api.getGodRecommendedItems(godID, languageID);
        } catch (EntityNotFoundException e) {
            return new RecommendedItemInfo[0];
        } catch (Exception e) {
            return null;
        }
    }

    public BaseItemInfo getItem(String itemName, String languageID) {
        try {
            return api.getItem(itemName, languageID);
        } catch (EntityNotFoundException e) {
            BaseItemInfo itemNotFound = new BaseItemInfo();
            itemNotFound.setItemName("N/A");
            return itemNotFound;
        } catch (Exception e) {
            return null;
        }
    }

    public BaseItemInfo[] getItems(String languageID) {
        try {
            return api.getItems(languageID);
        } catch (EntityNotFoundException e) {
            return new BaseItemInfo[0];
        } catch (Exception e) {
            return null;
        }
    }

    // God ID is gathered through accessing god information
    public String[] getGodSkins(Integer godID, String languageID) {
        try {
            return api.getGodSkins(godID, languageID);
        } catch (EntityNotFoundException e) {
            return new String[0];
        } catch (Exception e) {
            return null;
        }
    }

    // Accessed through the player's in game name
    public PlayerInfo[] getPlayer(String name) {
        try {
            return api.getPlayer(name);
        } catch (PlayerNotFoundException e) {
            return new PlayerInfo[0];
        } catch (Exception e) {
            return null;
        }
    }

    // Uses 3rd party ID (PS4, XBox, Switch, etc.)
    public PlayerInfo[] getPlayer(String name, String portalID) throws PlayerNotFoundException {
        try {
            return api.getPlayer(name, portalID);
        } catch (PlayerNotFoundException e) {
            return new PlayerInfo[0];
        } catch (Exception e) {
            return null;
        }
    }

    public PlayerID[] getPlayerID(String name) throws PlayerNotFoundException {
        try {
            return api.getPlayerID(name);
        } catch (PlayerNotFoundException e) {
            return new PlayerID[0];
        } catch (Exception e) {
            return null;
        }
    }

    public PlayerID[] getPlayerID(String portalID, String tag, Boolean gamerTag) throws PlayerNotFoundException {
        try {
            return api.getPlayerID(portalID, tag, gamerTag);
        } catch (PlayerNotFoundException e) {
            return new PlayerID[0];
        } catch (Exception e) {
            return null;
        }
    }

    public FriendsInfo[] getFriends(Integer playerID) {
        try {
            return api.getFriends(playerID);
        } catch (EntityNotFoundException e) {
            return new FriendsInfo[0];
        } catch (Exception e) {
            return null;
        }
    }

    public PlayerStatistics[] getCharacterStatistics(Integer playerID) {
        try {
            return api.getCharacterStatistics(playerID);
        } catch (EntityNotFoundException e) {
            return new PlayerStatistics[0];
        } catch (Exception e) {
            return null;
        }
    }

    public PlayTimeStatistics[] getPlayTimeStatistics(Integer playerID) {
        try {
            return api.getPlayTimeStatistics(playerID);
        } catch (EntityNotFoundException e) {
            return new PlayTimeStatistics[0];
        } catch (Exception e) {
            return null;
        }
    }

    public PlayerStatus[] getPlayerStatus(Integer playerID) {
        try {
            return api.getPlayerStatus(playerID);
        } catch (EntityNotFoundException e) {
            return new PlayerStatus[0];
        } catch (Exception e) {
            return null;
        }
    }

    public PlayerMatchHistory[] getMatchHistory(Integer playerID) {
        try {
            return api.getMatchHistory(playerID);
        } catch (EntityNotFoundException e) {
            return new PlayerMatchHistory[0];
        } catch (Exception e) {
            return null;
        }
    }

    public PlayerQueueStatistics[] getPlayerQueueStatistics(Integer playerID, String modeID) {
        try {
            return api.getPlayerQueueStatistics(playerID, modeID);
        } catch (EntityNotFoundException e) {
            return new PlayerQueueStatistics[0];
        } catch (Exception e) {
            return null;
        }
    }

    public SearchedPlayer[] getSearchedPlayers(String searchKey) {
        try {
            return api.getSearchedPlayers(searchKey);
        } catch (EntityNotFoundException e) {
            return new SearchedPlayer[0];
        } catch (Exception e) {
            return null;
        }
    }

    public PlayerMatchData[] getMatchData(Integer matchID) {
        try {
            return api.getMatchData(matchID);
        } catch (EntityNotFoundException e) {
            return new PlayerMatchData[0];
        } catch (Exception e) {
            return null;
        }
    }

    // Data will be kept in a map of matchID and playerMatchData key-value pairs.

    public List<PlayerMatchData> getMultipleMatchData(List<String> matchIDs) {
        try {
            return api.getMultipleMatchData(matchIDs);
        } catch (EntityNotFoundException e) {
            return new ArrayList<>();
        } catch (Exception e) {
            return null;
        }
    }

    public MatchInfo[] getMatchIDs(String ModeID, LocalDate date, Integer hour) {
        try {
            return api.getMatchIDs(ModeID, date, hour);
        } catch (EntityNotFoundException e) {
            return new MatchInfo[0];
        } catch (Exception e) {
            return null;
        }
    }

    public MatchInfo[] getMatchIDs(String ModeID, Integer hour) {
        try {
            return api.getMatchIDs(ModeID, hour);
        } catch (EntityNotFoundException e) {
            return new MatchInfo[0];
        } catch (Exception e) {
            return null;
        }
    }

    public MatchInfo[] getMatchIDs(String ModeID, LocalDate date, Integer hour, Integer minutes) {
        try {
            return api.getMatchIDs(ModeID, date, hour, minutes);
        } catch (EntityNotFoundException e) {
            return new MatchInfo[0];
        } catch (Exception e) {
            return null;
        }
    }

    public MatchInfo[] getMatchIDs(String ModeID, Integer hour, Integer minutes) {
        try {
            return api.getMatchIDs(ModeID, hour, minutes);
        } catch (EntityNotFoundException e) {
            return new MatchInfo[0];
        } catch (Exception e) {
            return null;
        }
    }

    public PlayerLiveMatchData[] getLiveMatchData(Integer liveMatchID) {
        try {
            return api.getLiveMatchData(liveMatchID);
        } catch (EntityNotFoundException e) {
            return new PlayerLiveMatchData[0];
        } catch (Exception e) {
            return null;
        }
    }

    public TopMatchInfo[] getTopMatchData() {
        try {
            return api.getTopMatchData();
        } catch (EntityNotFoundException e) {
            return new TopMatchInfo[0];
        } catch (Exception e) {
            return null;
        }
    }

    public LeaderboardRanking[] getLeaderboardRankings(String modeID, String rankingID, Integer round) {
        try {
            return api.getLeaderboardRankings(modeID, rankingID, round);
        } catch (EntityNotFoundException e) {
            return new LeaderboardRanking[0];
        } catch (Exception e) {
            return null;
        }
    }

    public SeasonInfo[] getLeagueSeasonInfo(String modeID) {
        try {
            return api.getLeagueSeasonInfo(modeID);
        } catch (EntityNotFoundException e) {
            return new SeasonInfo[0];
        } catch (Exception e) {
            return null;
        }
    }

    public ClanInfo[] getClan(Integer clanID) {
        try {
            return api.getClan(clanID);
        } catch (EntityNotFoundException e) {
            return new ClanInfo[0];
        } catch (Exception e) {
            return null;
        }
    }

    public ClanMemberInfo[] getClanMembers(Integer clanID) {
        try {
            return api.getClanMembers(clanID);
        } catch (EntityNotFoundException e) {
            return new ClanMemberInfo[0];
        } catch (Exception e) {
            return null;
        }
    }

    public ClanInfo[] searchClans(String searchKey) {
        try {
            return api.searchClans(searchKey);
        } catch (EntityNotFoundException e) {
            return new ClanInfo[0];
        } catch (Exception e) {
            return null;
        }
    }

    // Gets the clan ID for the closest search result
    public Integer getClanID(String searchKey) {
        try {
            return api.getClanID(searchKey);
        } catch (EntityNotFoundException e) {
            return -1;
        } catch (Exception e) {
            return null;
        }
    }

    public MotdInfo[] getMatchOfTheDayHistory() {
        try {
            return api.getMatchOfTheDayHistory();
        } catch (EntityNotFoundException e) {
            return new MotdInfo[0];
        } catch (Exception e) {
            return null;
        }
    }

    public EsportsMatchInfo[] getEsportsMatches() {
        try {
            return api.getEsportsMatches();
        } catch (EntityNotFoundException e) {
            return new EsportsMatchInfo[0];
        } catch (Exception e) {
            return null;
        }
    }

}
