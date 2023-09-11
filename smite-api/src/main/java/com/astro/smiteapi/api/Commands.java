package com.astro.smiteapi.api;

import com.astro.smiteapi.models.session.SessionInfo;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownContentTypeException;

@Component
public class Commands {
    private String apiUri;

    private String devID;

    private String authKey;

    @Autowired
    private SessionService sessionService;

    private final Logger LOGGER = Logger.getLogger(Commands.class.getName());

    // Checks to see if access to the API database is valid
    public String ping() {
        try {
            String pingRequest = SessionUtils.makeRequestUri(apiUri, "pingJson");
            return restTemplate(new RestTemplateBuilder()).getForObject(pingRequest, String.class);
        } catch (Exception exception) {
            LOGGER.info("Could not access API");
        }
        return "No information";
    }

    // Sessions are created independently
    private String createSession(RestTemplate template) {
        String timeStamp = SessionUtils.makeAPITimeStamp();
        String createSessionRequest = SessionUtils.makeRequestUri(apiUri, "createsessionJson", devID,
                SessionUtils.makeSignature("createsession", timeStamp, devID, authKey), timeStamp);

        try {
            SessionInfo info = template.getForObject(createSessionRequest, SessionInfo.class);
            sessionService.addConnection(info);
            assert info != null;
            return info.getSession_id();
        } catch (UnknownContentTypeException e) {
            LOGGER.log(Level.INFO, "Could not access API");
        }
        return "-1";
    }

    public String getSessionID() {
        for (SessionInfo connection : sessionService.getConnections()) {
            if (SessionUtils.verifySession(connection.getDate(), connection.getTime()))
                return connection.getSession_id();
            sessionService.deleteConnection(connection);
        }

        return createSession(restTemplate(new RestTemplateBuilder()));
    }

    // Makes any API request, requires POJO
    public <T> T makeRequestCall(Class<T> responseType, String request, String... additionalParams) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String timestamp = SessionUtils.makeAPITimeStamp();
        String[] initialData = {apiUri, request + "json", devID, SessionUtils.makeSignature(request, timestamp, devID, authKey), getSessionID(), timestamp};
        String requestUri = SessionUtils.makeRequestUri(
                Stream.concat(Arrays.stream(initialData),
                        Arrays.stream(additionalParams.clone())).toArray(String[]::new)
        );

        String info = restTemplate(new RestTemplateBuilder()).getForObject(requestUri, String.class);
        return SessionUtils.parseJSONData(responseType, info);
    }

    public String makeRequestCall(String request, String... additionalParams) {
        String timestamp = SessionUtils.makeAPITimeStamp();
        String[] initialData = {apiUri, request + "json", devID, SessionUtils.makeSignature(request, timestamp, devID, authKey), getSessionID(), timestamp};
        String requestUri = SessionUtils.makeRequestUri(
                Stream.concat(Arrays.stream(initialData),
                        Arrays.stream(additionalParams.clone())).toArray(String[]::new)
        );

        return restTemplate(new RestTemplateBuilder()).getForObject(requestUri, String.class);
    }

    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .errorHandler(new ErrorHandlerAPI())
                .build();
    }

    public String getApiUri() {
        return apiUri;
    }

    public String getDevID() {
        return devID;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setApiUri(String apiUri) {
        this.apiUri = apiUri;
    }

    public void setDevID(String devID) {
        this.devID = devID;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }
}
