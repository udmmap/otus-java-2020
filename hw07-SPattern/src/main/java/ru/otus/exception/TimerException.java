package ru.otus.exception;

import java.time.LocalDateTime;

public class TimerException extends Exception {
    private final LocalDateTime creationTime = LocalDateTime.now();

    public LocalDateTime getCreationTime() {
        return creationTime;
    }
}
