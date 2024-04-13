package restaurant.menu.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


import restaurant.menu.entities.User;
import restaurant.menu.entities.dto.AuthenticationResponse;
import restaurant.menu.entities.dto.OrderRequestDTO;
import restaurant.menu.service.imp.UserService;
import restaurant.menu.service.token.JwtService;

import java.util.Arrays;

/**
 "This behavior of the AuthenticationAspect class is to retrieve the Authorization token from the call, perform some checks on the header, and then verify if the role associated with the caller's ID is allowed to access the specific endpoint."
 */

@Aspect
@Component
@Order(0)
@Slf4j
@RequiredArgsConstructor
public class AuthenticationAspect {

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final UserService userService;

    @Autowired
    HttpServletRequest request;


    @Pointcut("@annotation(Authorized)")
    private void AuthenticateMethod() {
    }

    @Around("AuthenticateMethod()")

    /**
     * This method has the rule to check if the role of user is allow to call the specific endpoint if the result is positive returns the user entity to the controller
     */
    public Object AuthenticationMethod(ProceedingJoinPoint joinPoint ) throws Throwable {
        Authorized auth = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(Authorized.class);
        log.info("Inizio dell'aspect : " +getClass() );
        log.info("Prendo la richiesta dall header");
        String authHeader = request.getHeader("Authorization");
        if(authHeader==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(AuthenticationResponse.builder().message("Token is missing").build());
        }
        var jwt = authHeader.substring(7); //substring per escludere bearer più uno spazio "bearer "
        if(!jwtService.isTokenValid(jwt)){
            log.error("The Token is not valid. Verified in class: " + getClass());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(AuthenticationResponse.builder().message("Token not valid!").build());
        }
        log.info("Il token è valido!");
        log.info("Extracting the id from the token");
        int id = Integer.parseInt(jwtService.extractStringId(jwt));
        log.info("Finding the user with the extracted id!");
        jwtService.extractEmail(jwt);
         User userById = null;
        try{
            userById = userService.findById(id);
            if(!Arrays.asList(auth.roles()).isEmpty() && !Arrays.stream(auth.roles()).toList().contains(userById.getRole())){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(AuthenticationResponse.builder().message("User role not match").build());
            }
            log.info("Ho trovato l'user!");
        }catch(NullPointerException e){
            log.error("User not found!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(AuthenticationResponse.builder().message("User not found").build());
        }
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof User) {
                args[i] = userById;
            }
            //TODO: VEDERE COME UTILIZZARE L'EMAIL RECUPERATA DAL USER PER PASSARLA NELL'ORDER CHE SERVE PER CREARE IL CART FINALE, probabilmente prendi e modfichi nellaspect quando è un istanza di order cosi che il controller ha gia tutto popolato
        }
        log.info("Passo l'user al controller");
        return joinPoint.proceed(args);
    }
}
