package com.astro.smitesolver.discord.service;

import com.astro.smitesolver.smite.model.characters.GodInfo;
import com.astro.smitesolver.smite.model.gamedata.PatchInfo;
import com.astro.smitesolver.smite.model.gamedata.matches.MatchInfo;
import com.astro.smitesolver.smite.model.items.BaseItemInfo;
import com.astro.smitesolver.smite.model.player.matches.PlayerMatchData;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class APIService {

    private final static Logger LOGGER = Logger.getLogger(APIService.class.getName());
    @Value("${api.url}")
    private String apiURL;

    public HttpURLConnection getConnection(String path) {
        try {
            URL url = new URL(apiURL + path);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setUseCaches(false);
            con.setAllowUserInteraction(false);
            con.connect();
            if(con.getResponseCode()==201 || con.getResponseCode()==200) {
                return con;
            }
        } catch (MalformedURLException e) {
            LOGGER.warning("Was not able to connect to the API");
        } catch (IOException e) {
            return null;
        }
        return null;
    }

    public String readResponse(HttpURLConnection con) {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
            return content.toString();
        } catch (IOException e) {
            return "";
        } catch (Exception e) {
            return null;
        }
    }

    public <T> T translateResponse(String data, Class<T> tClass) {
        return new Gson().fromJson(data, tClass);
    }

    public <T> ArrayList<T> translateListResponse(String data, Class<T> tClass) {
        return new Gson().fromJson(data, ArrayList.class);
    }

    public List<PlayerMatchData> getMultipleMatchData(Integer[] matchIDs) {
        StringBuilder matchIDStr = new StringBuilder();
        for (Integer matchID : matchIDs) {
            matchIDStr.append(matchID);
            matchIDStr.append(",");
        }

        matchIDStr.replace(matchIDStr.length() - 1, matchIDStr.length(), "");
        HttpURLConnection connection = getConnection("/match/id/"+ matchIDStr);
        String response = readResponse(connection);
        if (response != null && !response.equals("")) {
            return translateListResponse(response, PlayerMatchData.class);
        } else {
            return new ArrayList<>();
        }
    }

    public MatchInfo[] getMatchIDs(String modeID, LocalDate date, int hour) {
        HttpURLConnection connection = getConnection("/match/"+modeID+"/"+date+"/"+hour);
        String response = readResponse(connection);
        if (response != null && !response.equals("")) {
            List<MatchInfo> matchInfo = translateListResponse(response, MatchInfo.class);
            return matchInfo.toArray(MatchInfo[]::new);
        } else {
            return new MatchInfo[0];
        }
    }

    public MatchInfo[] getMatchIDs(String modeID, LocalDate date, int hour, int minutes) {
        HttpURLConnection connection = getConnection("/match/"+modeID+"/"+date+"/"+hour+"/"+minutes);
        String response = readResponse(connection);
        if (response != null && !response.equals("")) {
            List<MatchInfo> matchInfo = translateListResponse(response, MatchInfo.class);
            return matchInfo.toArray(MatchInfo[]::new);
        } else {
            return new MatchInfo[0];
        }
    }

    public GodInfo[] getGods(String languageID) {
        HttpURLConnection connection = getConnection("/gods/"+languageID);
        String response = readResponse(connection);
        if (response != null && !response.equals("")) {
            List<GodInfo> baseItemInfo = translateListResponse(response, GodInfo.class);
            return baseItemInfo.toArray(GodInfo[]::new);
        } else {
            return new GodInfo[0];
        }
    }

    public BaseItemInfo[] getItems(String languageID) {
        HttpURLConnection connection = getConnection("/items/"+languageID);
        String response = readResponse(connection);
        if (response != null && !response.equals("")) {
            List<BaseItemInfo> baseItemInfo = translateListResponse(response, BaseItemInfo.class);
            return baseItemInfo.toArray(BaseItemInfo[]::new);
        } else {
            return new BaseItemInfo[0];
        }
    }

    public PatchInfo[] getPatchInfo() {
        HttpURLConnection connection = getConnection("/patch");
        String response = readResponse(connection);
        if (response != null && !response.equals("")) {
            List<PatchInfo> patchInfo = translateListResponse(response, PatchInfo.class);
            return patchInfo.toArray(PatchInfo[]::new);
        } else {
            return new PatchInfo[0];
        }
    }
}
