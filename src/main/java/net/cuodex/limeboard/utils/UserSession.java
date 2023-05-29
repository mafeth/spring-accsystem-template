package net.cuodex.limeboard.utils;

import lombok.Data;

import java.util.UUID;

@Data
public class UserSession {

    private Long accountId;
    private String sessionId;
    private Long createdAt;
    private Long lastUsed;

    public UserSession(long accountId) {
        sessionId = UUID.randomUUID().toString();
        long time = System.currentTimeMillis();
        createdAt = time;
        lastUsed = time;
        this.accountId = accountId;
    }
}
