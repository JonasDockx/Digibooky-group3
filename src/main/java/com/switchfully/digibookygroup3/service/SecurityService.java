package com.switchfully.digibookygroup3.service;

import com.switchfully.digibookygroup3.domain.user.Feature;
import com.switchfully.digibookygroup3.domain.user.User;
import com.switchfully.digibookygroup3.domain.user.UserRepository;
import com.switchfully.digibookygroup3.exception.ForbiddenException;
import com.switchfully.digibookygroup3.exception.UnauthorizedException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;


@Service
public class SecurityService {
    UserRepository userRepository;

    public SecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateAuthorization(String authorization, Feature feature) {
        if(authorization == null || !authorization.startsWith("Basic")) {
            throw new UnauthorizedException();
        }

        String encodedCredentials = authorization.replaceFirst("^Basic\\s+", "");
        String decodedCredentials = new String(Base64.getDecoder().decode(encodedCredentials), StandardCharsets.UTF_8);

        if (!decodedCredentials.contains(":")) {
            throw new UnauthorizedException();

        }

        String[] emailAndPassword = decodedCredentials.split( ":", 2);

        String email = emailAndPassword[0];
        String password = emailAndPassword[1];

        User user = userRepository.getUserByEmail(email)
                .orElseThrow(UnauthorizedException::new);

        if(!userRepository.doesPasswordMatch(user.getId(), password)){
            throw new UnauthorizedException();
        }

        if(!user.canHaveAccessTo(feature)){
            throw new ForbiddenException();
        }
    }
}
