package restaurant.menu.service.token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import restaurant.menu.entities.User;
import restaurant.menu.entities.dto.AuthenticationRequest;
import restaurant.menu.entities.dto.AuthenticationResponse;
import restaurant.menu.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private  final PasswordEncoder passwordEncoder;

    @Autowired
    private final JwtService jwtService;

    /**
     *
     * @param request
     * @return  {@link AuthenticationResponse}
     * the role of this method is to retrieve the token and return to the caller
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        log.info("Looking for the user");
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(()->new RuntimeException("not found user with this email)"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Bad credential");
        }
        log.info("Calling method Generate Token in JwtService");
        String jwtToken = jwtService.generateToken(user);
        log.info("The token is generated and the user is authenticated!");
        log.info(String.valueOf(user));
        return AuthenticationResponse.builder().token(jwtToken).message("Login successful!").build();
    }












}
