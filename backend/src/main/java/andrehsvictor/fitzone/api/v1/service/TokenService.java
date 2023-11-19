package andrehsvictor.fitzone.api.v1.service;

import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import andrehsvictor.fitzone.api.v1.model.Customer;
import andrehsvictor.fitzone.api.v1.util.Claims;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class TokenService {

    private final String BEARER_PREFIX = "Bearer ";

    @Value("${fitzone.security.token.secret-key}")
    private String secretKey;

    @Value("${fitzone.security.token.validity-in-miliseconds}")
    private long expirationInMiliseconds;

    @Autowired
    private Environment env;

    private Algorithm algorithm = null;

    @Autowired
    private CustomerService customerService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        algorithm = Algorithm.HMAC256(secretKey.getBytes());
    }

    public String generateToken(Customer customer) {
        return JWT.create()
                .withSubject(customer.getFullName())
                .withClaim(Claims.ACCESS_CODE, customer.getAccessCode())
                .withClaim(Claims.ROLE, customer.getRole())
                .withIssuer(String.format("Fitzone Academy API v%s", env.getProperty("fitzone.api.version")))
                .withExpiresAt(calculeTokenExpiration())
                .sign(algorithm);
    }

    private Date calculeTokenExpiration() {
        Date now = new Date();
        return new Date(now.getTime() + expirationInMiliseconds);
    }

    public DecodedJWT decodedToken(String token) {
        return JWT.require(algorithm).build().verify(token);
    }

    public Authentication getAuthentication(String token) {
        DecodedJWT decodedToken = decodedToken(token);
        UserDetails userDetails = customerService.loadUserByUsername(decodedToken.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String formatToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(bearerToken != null)
            return bearerToken.replace(BEARER_PREFIX, "");
        return null;
    }

    public String formatToken(String bearerToken) {
        return bearerToken.replace(BEARER_PREFIX, "");
    }

    public boolean tokenIsValid(String token) {
        DecodedJWT decodedToken = decodedToken(token);
        try {
            if (decodedToken.getExpiresAt().before(new Date()))
                return false;
            return true;
        } catch (RuntimeException exception) {
            throw new RuntimeException("Token is invalid");
        }
    }
}
