package com.marcorp.streaming.video.server.websocket.model;

public class SessionInfo  {
    private String sessionId;
    private String username;

    // Constructor, getters y setters
    public SessionInfo(String sessionId, String username) {
        this.sessionId = sessionId;
        this.username = username;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getUsername() {
        return username;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
