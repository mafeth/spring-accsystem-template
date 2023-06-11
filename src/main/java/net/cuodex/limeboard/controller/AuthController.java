package net.cuodex.limeboard.controller;


import net.cuodex.limeboard.dto.LoginDto;
import net.cuodex.limeboard.dto.RegisterDto;
import net.cuodex.limeboard.entity.LimeUser;
import net.cuodex.limeboard.service.AuthenticationService;
import net.cuodex.limeboard.utils.DefaultReturnable;
import net.cuodex.limeboard.utils.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping("/login")
    public ResponseEntity<DefaultReturnable> authenticateUser(@Valid @RequestBody LoginDto loginDto, HttpServletRequest request) {
        UserSession session = authenticationService.authenticate(
                loginDto.getUsername(), loginDto.getPassword());

        if (session == null)
            return new DefaultReturnable(HttpStatus.UNAUTHORIZED, "Username or password is invalid").getResponseEntity();

        DefaultReturnable returnable = new DefaultReturnable(HttpStatus.OK, "Successfully logged in.");
        LimeUser user = authenticationService.getUser(session.getSessionId());
        returnable.addData("sessionId", session.getSessionId());
        returnable.addData("username", user.getUsername());
        returnable.addData("email", user.getEmail());

        return returnable.getResponseEntity();
    }


    @PostMapping("/register")
    public ResponseEntity<DefaultReturnable> registerUser(@Valid @RequestBody RegisterDto signUpDto, HttpServletRequest request){
        return authenticationService.createUser(signUpDto.getUsername(), signUpDto.getEmail().toLowerCase(), signUpDto.getPassword()).getResponseEntity();

    }

    @PostMapping("/logout")
    public ResponseEntity<DefaultReturnable> logoutUser(@RequestHeader(value = "Authorization") String sessionId, HttpServletRequest request){
        sessionId = sessionId.split(" ")[sessionId.split(" ").length - 1];
        LimeUser user = authenticationService.getUser(sessionId);
        if (user == null)
            return new DefaultReturnable(HttpStatus.UNAUTHORIZED, "Session id is invalid or expired.").getResponseEntity();

        authenticationService.invalidateSession(sessionId);
        return new DefaultReturnable("Successfully logged out.").getResponseEntity();
    }

    @PostMapping("/check-session")
    public ResponseEntity<DefaultReturnable> checkSession(@RequestHeader(value = "Authorization") String sessionId, HttpServletRequest request) {
        sessionId = sessionId.split(" ")[sessionId.split(" ").length - 1];
        return authenticationService.checkSession(sessionId).getResponseEntity();
    }


}
