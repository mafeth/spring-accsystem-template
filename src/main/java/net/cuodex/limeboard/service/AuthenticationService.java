package net.cuodex.limeboard.service;

import net.cuodex.limeboard.entity.LimeUser;
import net.cuodex.limeboard.repository.LimeUserRepository;
import net.cuodex.limeboard.LimeboardBackendApplication;
import net.cuodex.limeboard.utils.UserSession;
import net.cuodex.limeboard.utils.DefaultReturnable;
import net.cuodex.limeboard.utils.OtherUtils;
import net.cuodex.limeboard.utils.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationService {

    private final List<UserSession> activeSessions;

    @Autowired
    private LimeUserRepository userRepository;

    public AuthenticationService() {
        this.activeSessions = new ArrayList<>();
    }


    public UserSession authenticate(String username, String password) {

        if (!userRepository.existsByUsername(username))
            return null;


        LimeUser userAccount = userRepository.findByUsername(username).get();

//
        String userPassword = userAccount.getPassword();

        if (!password.equals(userPassword)) {
            return null;
        }

        // Generate session which is not in use
        UserSession session;
        List<String> activeSessionIds = OtherUtils.getSessionIdList(activeSessions);
        do {
            session = new UserSession(userAccount.getId());
        }while (activeSessionIds.contains(session.getSessionId()));

        // Deactivate old sessions
        activeSessions.removeIf(activeSession -> activeSession.getAccountId() == userAccount.getId());

        // Add new session
        this.activeSessions.add(session);


        userAccount.setLastSeen(OtherUtils.getTimestamp());
        userRepository.save(userAccount);

        LimeboardBackendApplication.LOGGER.info("User '" + username + "' successfully authenticated. ("+session.getSessionId()+")");
        return session;
    }

    public boolean isSessionValid(String sessionId, String ipAddress) {
        if (OtherUtils.getSessionIdList(activeSessions).contains(sessionId)) {
            if (System.currentTimeMillis() - getSession(sessionId).getLastUsed() > Variables.SESSION_TIMEOUT) {
                invalidateSession(sessionId);
                return false;
            }

            return true;
        }
        return false;
    }

    public void invalidateSession(String sessionId) {
        activeSessions.removeIf(activeSession -> activeSession.getSessionId().equals(sessionId));
    }

    public LimeUser getUser(String sessionId, String ipAddress) {
        if (isSessionValid(sessionId, ipAddress)) {
            getSession(sessionId).setLastUsed(System.currentTimeMillis());
            return userRepository.getById(getSession(sessionId).getAccountId());
        }else {
            return null;
        }
    }

    public UserSession getSession(String sessionId) {
        return activeSessions.stream().filter(activeSession -> activeSession.getSessionId().equals(sessionId)).toList().get(0);
    }

    public DefaultReturnable createUser(final String username, final String email, final String password) {

        if (!(username.matches("^[a-zA-Z0-9_]*$") && username.length() >= 3 && username.length() <= 16))
            return new DefaultReturnable(HttpStatus.BAD_REQUEST, "Invalid username.");


        if ((email != null && !email.isEmpty()) &&!OtherUtils.isEmailValid(email))
            return new DefaultReturnable(HttpStatus.BAD_REQUEST, "Invalid email address.");


        // add check for username exists in a DB
        if (userRepository.existsByUsername(username))
            return new DefaultReturnable(HttpStatus.BAD_REQUEST, "Username already taken.");


        // create user object
        LimeUser user = new LimeUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setCreatedAt(OtherUtils.getTimestamp());
        user.setLastSeen(OtherUtils.getTimestamp());

        userRepository.save(user);
        LimeboardBackendApplication.LOGGER.info("User '" + username + "' successfully created.");

        return new DefaultReturnable(HttpStatus.CREATED, "User successfully created.").addData("user", user);
    }


}
