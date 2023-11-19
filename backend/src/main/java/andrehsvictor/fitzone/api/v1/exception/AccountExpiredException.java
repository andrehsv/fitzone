package andrehsvictor.fitzone.api.v1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AccountExpiredException extends Exception {

    public AccountExpiredException(String msg) {
        super(msg);
    }
    
}
