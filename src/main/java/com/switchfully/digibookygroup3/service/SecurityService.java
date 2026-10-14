package com.switchfully.digibookygroup3.service;

import com.switchfully.digibookygroup3.domain.user.Feature;
import com.switchfully.digibookygroup3.domain.user.User;
import com.switchfully.digibookygroup3.domain.user.UserRepository;
import com.switchfully.digibookygroup3.exception.ForbiddenException;
import com.switchfully.digibookygroup3.exception.UnauthorizedException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

import static org.springframework.util.StringUtils.split;

@Service
public class SecurityService {
    UserRepository userRepository;

    public SecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateAuthorization(String authorization, Feature feature) {
        if(authorization == null || !authorization.startsWith("Basic")) {
            throw new UnauthorizedException("authorization == null || !authorization.startsWith(\"Basic\")");
        }

        String encodedCredentials = authorization.replaceFirst("^Basic\\s+", "");
        String decodedCredentials = new String(Base64.getDecoder().decode(encodedCredentials), StandardCharsets.UTF_8);

        String[] emailAndPassword = split(decodedCredentials, ":");

        if(emailAndPassword == null || emailAndPassword[0] == null || emailAndPassword[1] == null) {
            throw new UnauthorizedException("emailAndPassword[0] == null || emailAndPassword[1] == null");
        }

        String email = emailAndPassword[0];
        String password = emailAndPassword[1];

        Optional<User> optionalUser = userRepository.getUserByEmail(email);

        if(optionalUser.isEmpty()){
            throw new UnauthorizedException("optionalUser.isEmpty()");
        }

        User user = optionalUser.get();

        if(!userRepository.passMatch(user.getId(), password)){
            throw new UnauthorizedException("!userRepository.passMatch(password)");
        }

        if(!user.canHaveAccessTo(feature)){
            throw new ForbiddenException("!user.canHaveAccesTo(feature)");
        }
    }
}
