package net.javaguides.todo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class SavingEntityException extends RuntimeException{

    public SavingEntityException(String message) {
        super(message);
    }
}
