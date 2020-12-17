package ru.otus.processor;

import ru.otus.exception.TimerException;
import ru.otus.model.Message;

public interface Processor {

    Message process(Message message) throws Exception;

    //todo: 2. Сделать процессор, который поменяет местами значения field11 и field12
}
