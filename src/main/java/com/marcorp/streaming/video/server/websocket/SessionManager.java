package com.marcorp.streaming.video.server.websocket;

import com.marcorp.streaming.video.server.websocket.model.SessionInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {
    private final Map<String, WebSocketSession> sessionsByNickname = new ConcurrentHashMap<>();

    public void addSession(String nickname, WebSocketSession session) {
        sessionsByNickname.put(nickname, session);
    }

    public void removeSession(String nickname) {
        sessionsByNickname.remove(nickname);
    }

    public WebSocketSession getSession(String nickname) {
        return sessionsByNickname.get(nickname);
    }

    public Map<String, WebSocketSession> getAllSessions() {
        return sessionsByNickname;
    }

}
