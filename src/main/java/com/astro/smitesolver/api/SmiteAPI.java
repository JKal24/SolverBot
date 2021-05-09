package com.astro.smitesolver.api;

import com.astro.smitesolver.exception.EntityNotFoundException;
import com.astro.smitesolver.smite.entity.characters.GodNotFoundException;
import com.astro.smitesolver.smite.entity.characters.GodInfo;
import com.astro.smitesolver.smite.entity.characters.auxiliary.LeaderboardInfo;
import com.astro.smitesolver.smite.entity.gamedata.*;
import com.astro.smitesolver.smite.entity.gamedata.matches.*;
import com.astro.smitesolver.smite.entity.items.BaseItemInfo;
import com.astro.smitesolver.smite.entity.items.RecommendedItemInfo;
import com.astro.smitesolver.smite.entity.player.*;
import com.astro.smitesolver.smite.entity.player.auxiliary.ClanMemberInfo;
import com.astro.smitesolver.smite.entity.player.auxiliary.FriendsInfo;
import com.astro.smitesolver.smite.entity.player.auxiliary.SearchedPlayer;
import com.astro.smitesolver.smite.entity.player.matches.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class SmiteAPI {

    private final static Logger LOGGER = Logger.getLogger(SmiteAPI.class.getName());

    @Autowired
    private Commands commands;

    public void setCredentials(String apiUri, String devID, String authKey) {
        commands.setApiUri(apiUri);
        commands.setDevID(devID);
        commands.setAuthKey(authKey);
    }

    /**
     * @param type the entity type which is being accessed through the API, can be an array
     * @param request the extension to the url
     * @param additionalParams any addition information to add to the request
     * @param <T> entity type
     * @return API data which reflects the entity type
     * @throws EntityNotFoundException thrown if the API does not contain the given entity type
     */
    private <T> T handleRequest(Class<T> type, String request, String... additionalParams) throws EntityNotFoundException {
        try {
            return commands.makeRequestCall(type, request, additionalParams);
        } catch (InvocationTargetException invocationTargetException) {
            LOGGER.log(Level.WARNING, "Encountered a problem in entity constructor");

        } catch (NoSuchMethodException noSuchMethodException) {
            LOGGER.log(Level.WARNING, "Not an applicable entity for the Smite API");

        } catch (InstantiationException instantiationException) {
            LOGGER.log(Level.WARNING, "Found unsuitable abstract class");

        } catch (IllegalAccessException illegalAccessException) {
            LOGGER.log(Level.WARNING, "Could not access constructor due to limited visibility");

        }
        throw new EntityNotFoundException("Could not find any data for the specified data");
    }

    public String getAPIStatus() {
        return commands.ping();
    }

    public String getSessionStatus() throws EntityNotFoundException {
        return handleRequest(String.class, "testsession");
    }

    public UserInfo[] getDataUsed() throws EntityNotFoundException {
        return handleRequest(UserInfo[].class, "getdataused");
    }

    public ServerInfo[] getServerStatus() throws EntityNotFoundException {
        return handleRequest(ServerInfo[].class, "gethirezserverstatus");
    }

    public PatchInfo[] getPatchInfo() throws EntityNotFoundException {
        return handleRequest(PatchInfo[].class, "getpatchinfo");
    }

    public GodInfo getGod(String name, String languageID) {
        try {
            GodInfo[] characters = this.getGods(languageID);
            for (GodInfo character : characters) {
                if (name.equals(character.getName())) {
                    return character;
                }
            }
            throw new GodNotFoundException();
        } catch(GodNotFoundException | EntityNotFoundException e) {
            LOGGER.log(Level.INFO, String.format("Could not find character: %s", name));
        }
        return null;
    }

    public GodInfo getGod(Integer ID, String languageID) {
        try {
            GodInfo[] characters = this.getGods(languageID);
            for (GodInfo character : characters) {
                if (ID.equals(character.getGodID())) {
                    return character;
                }
            }
            throw new GodNotFoundException();
        } catch(GodNotFoundException | EntityNotFoundException e) {
            LOGGER.log(Level.INFO, String.format("Could not find character: %s", ID));
        }
        return null;
    }

    public GodInfo[] getGods(String[] names, String languageID) throws EntityNotFoundException {
        GodInfo[] characters = this.getGods(languageID);
        List<GodInfo> customCharacters = new ArrayList<GodInfo>();
        try {
            for (GodInfo character : characters) {
                if (Arrays.stream(names).anyMatch(name -> name.equals(character.getName()))) {
                    customCharacters.add(character);
                }
            }
            if (customCharacters.size() == 0)
                throw new GodNotFoundException();
            return customCharacters.toArray(new GodInfo[characters.length]);
        } catch (GodNotFoundException e) {
            LOGGER.log(Level.INFO, "Could not find any of the characters provided");
        }
        return customCharacters.toArray(new GodInfo[characters.length]);
    }

    public GodInfo[] getGods(String languageID) throws EntityNotFoundException {
        return handleRequest(GodInfo[].class, "getgods", languageID);
    }

    public LeaderboardInfo[] getGodLeaderboard(Integer godID, String modeID) throws EntityNotFoundException {
        return handleRequest(LeaderboardInfo[].class, "getgodleaderboard", godID.toString(), modeID);
    }

    public RecommendedItemInfo[] getGodRecommendedItems(Integer godID, String languageID) throws EntityNotFoundException {
        return handleRequest(RecommendedItemInfo[].class, "getgodrecommendeditems", godID.toString(), languageID);
    }

    public BaseItemInfo getItem(String itemName, String languageID) {
        try {
            BaseItemInfo[] items = this.getItems(languageID);
            for (BaseItemInfo item : items) {
                if (itemName.equals(item.getItemName())) {
                    return item;
                }
            }
            throw new GodNotFoundException();
        } catch(Exception e) {
            // Implement item exception...
            LOGGER.log(Level.INFO, String.format("Could not find item: %s", itemName));
        }
        return null;
    }

    public BaseItemInfo[] getItems(String languageID) throws EntityNotFoundException {
        return handleRequest(BaseItemInfo[].class, "getitems", languageID);
    }

    // God ID is gathered through accessing god information
    public String[] getGodSkins(Integer godID, String languageID) throws EntityNotFoundException {
        return handleRequest(String[].class, "getgodskins", godID.toString(), languageID);
    }

    // Accessed through the player's in game name
    public PlayerInfo[] getPlayer(String name) throws EntityNotFoundException, PlayerNotFoundException {
        PlayerInfo[] playerInfos = handleRequest(PlayerInfo[].class, "getplayer", name);

        if (playerInfos.length > 0)
            return playerInfos;
        throw new PlayerNotFoundException(String.format("Could not find the player with the name: %s", name));
    }

    // Uses 3rd party ID (PS4, XBox, Switch, etc.)
    public PlayerInfo[] getPlayer(String name, String portalID) throws EntityNotFoundException, PlayerNotFoundException {
        PlayerInfo[] playerInfos = handleRequest(PlayerInfo[].class,"getplayer", name, portalID);

        if (playerInfos.length > 0)
            return playerInfos;
        throw new PlayerNotFoundException(String.format("Could not find the player with the name and portalID: %s, %s", name, portalID));
    }

    public PlayerID[] getPlayerID(String name) throws EntityNotFoundException, PlayerNotFoundException {
        PlayerID[] playerIDs = handleRequest(PlayerID[].class, "getplayeridbyname", name);

        if (playerIDs.length > 0)
            return playerIDs;
        throw new PlayerNotFoundException(String.format("Could not find the player's ID, with the name: %s", name));
    }

    public PlayerID[] getPlayerID(String portalID, String tag, Boolean gamerTag) throws EntityNotFoundException, PlayerNotFoundException {
        PlayerID[] playerIDs = gamerTag ? handleRequest(PlayerID[].class, "getplayeridsbygamertag", portalID, tag) :
                handleRequest(PlayerID[].class, "getplayeridbyportaluserid", portalID, tag);

        if (playerIDs.length > 0)
            return playerIDs;
        throw new PlayerNotFoundException(String.format("Could not find the player's ID, with the portalID and tag: %s, %s", portalID, tag));
    }

    public FriendsInfo[] getFriends(Integer playerID) throws JsonProcessingException, NoSuchAlgorithmException, EntityNotFoundException {
        return handleRequest(FriendsInfo[].class, "getfriends", playerID.toString());
    }

    public PlayerStatistics[] getCharacterStatistics(Integer playerID) throws EntityNotFoundException {
        return handleRequest(PlayerStatistics[].class, "getgodranks", playerID.toString());
    }

    public PlayTimeStatistics[] getPlayTimeStatistics(Integer playerID) throws EntityNotFoundException {
        return handleRequest(PlayTimeStatistics[].class, "getplayerachievements", playerID.toString());
    }

    public PlayerStatus[] getPlayerStatus(Integer playerID) throws EntityNotFoundException {
        return handleRequest(PlayerStatus[].class, "getplayerstatus", playerID.toString());
    }

    public PlayerMatchHistory[] getMatchHistory(Integer playerID) throws EntityNotFoundException {
        return handleRequest(PlayerMatchHistory[].class, "getmatchhistory", playerID.toString());
    }

    public PlayerQueueStatistics[] getPlayerQueueStatistics(Integer playerID, String modeID) throws EntityNotFoundException {
        return handleRequest(PlayerQueueStatistics[].class, "getqueuestats", playerID.toString(), modeID);
    }

    public SearchedPlayer[] getSearchedPlayers(String searchKey) throws EntityNotFoundException {
        return (Arrays.stream(handleRequest(SearchedPlayer[].class, "searchplayers", searchKey))
                .filter(searchedPlayer -> {
            return searchedPlayer.getHzPlayerName() != null && searchedPlayer.getPlayerName() != null;
        })).toArray(SearchedPlayer[]::new);
    }

    public PlayerMatchData[] getMatchData(Integer matchID) throws EntityNotFoundException {
        return handleRequest(PlayerMatchData[].class, "getmatchdetails", matchID.toString());
    }

    // Data will be kept in a map of matchID and playerMatchData key-value pairs.

    public List<PlayerMatchData> getMultipleMatchData(Integer... matchID) throws EntityNotFoundException {
        // Transform matchIDs into strings to be used for HTTP request
        String[] parseMatchID = Arrays.stream(matchID)
                .map(Object::toString).toArray(String[]::new);

        // Get data for each match for each player's perspective
        List<PlayerMatchData> allMatchData = new ArrayList<>();
        String[] tempIDs = new String[10];
        int parse = 0;

        for (String ID : parseMatchID) {
            if (parse >= 10) {
                PlayerMatchData[] tempMatchData = handleRequest(PlayerMatchData[].class, "getmatchdetailsbatch",
                        String.join(",", tempIDs));
                allMatchData.addAll(Arrays.asList(tempMatchData));

                tempIDs = new String[10];
                parse = 0;
            }
            tempIDs[parse] = ID;
            parse++;
        }

        // Similar matches will all be added from every player's perspective.
        return allMatchData;
    }

    public MatchInfo[] getMatchIDs(String ModeID, LocalDate date, Integer hour) throws EntityNotFoundException {
        return handleRequest(MatchInfo[].class ,"getmatchidsbyqueue",
                ModeID, SessionUtils.makeAPIDate(date), hour.toString());
    }

    public MatchInfo[] getMatchIDs(String ModeID, Integer hour) throws EntityNotFoundException {
        return handleRequest(MatchInfo[].class ,"getmatchidsbyqueue",
                ModeID, SessionUtils.makeAPIDate(), hour.toString());
    }

    public MatchInfo[] getMatchIDs(String ModeID, LocalDate date, Integer hour, Integer minutes) throws EntityNotFoundException {
        return handleRequest(MatchInfo[].class ,"getmatchidsbyqueue",
                ModeID, SessionUtils.makeAPIDate(date), String.join(",", hour.toString(), minutes.toString()));
    }

    public MatchInfo[] getMatchIDs(String ModeID, Integer hour, Integer minutes) throws EntityNotFoundException {
        return handleRequest(MatchInfo[].class ,"getmatchidsbyqueue",
                ModeID, SessionUtils.makeAPIDate(), String.join(",", hour.toString(), minutes.toString()));
    }

    public PlayerLiveMatchData[] getLiveMatchData(Integer liveMatchID) throws EntityNotFoundException {
        return handleRequest(PlayerLiveMatchData[].class, "getmatchplayerdetails", liveMatchID.toString());
    }

    public TopMatchInfo[] getTopMatchData() throws EntityNotFoundException {
        return handleRequest(TopMatchInfo[].class, "gettopmatches");
    }

    public LeaderboardRanking[] getLeaderboardRankings(String modeID, String rankingID, Integer round) throws EntityNotFoundException {
        return handleRequest(LeaderboardRanking[].class, "getleagueleaderboard", modeID, rankingID, round.toString());
    }

    public SeasonInfo[] getLeagueSeasonInfo(String modeID) throws EntityNotFoundException {
        return handleRequest(SeasonInfo[].class, "getleagueseasons", modeID);
    }

    public ClanInfo[] getClan(Integer clanID) throws EntityNotFoundException {
        return handleRequest(ClanInfo[].class, "getteamdetails", clanID.toString());
    }

    public ClanMemberInfo[] getClanMembers(Integer clanID) throws EntityNotFoundException {
        return handleRequest(ClanMemberInfo[].class, "getteamplayers", clanID.toString());
    }

    public ClanInfo[] searchClans(String searchKey) throws EntityNotFoundException {
        return handleRequest(ClanInfo[].class, "searchteams", searchKey);
    }

    // Gets the clan ID for the closest search result
    public Integer getClanID(String searchKey) throws EntityNotFoundException {
        return searchClans(searchKey)[0].getTeamID();
    }

    public MotdInfo[] getMatchOfTheDayHistory() throws EntityNotFoundException {
        return handleRequest(MotdInfo[].class, "getmotd");
    }

    public EsportsMatchInfo[] getEsportsMatches() throws EntityNotFoundException {
        return handleRequest(EsportsMatchInfo[].class, "getesportsproleaguedetails");
    }
}
