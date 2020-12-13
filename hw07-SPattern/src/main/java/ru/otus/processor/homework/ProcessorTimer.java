package ru.otus.processor.homework;

import ru.otus.exception.TimerException;
import ru.otus.model.Message;
import ru.otus.processor.LoggerProcessor;
import ru.otus.processor.Processor;

import java.time.LocalDateTime;

public class ProcessorTimer implements Processor {

    private LocalDateTime dtLastProcess = null;

    private void setDtLastProcess(LocalDateTime t){
        this.dtLastProcess = t;
    }

    public LocalDateTime getDtLastProcess(){
        return dtLastProcess;
    }

    @Override
    public Message process(Message message) throws TimerException {
        setDtLastProcess(LocalDateTime.now());

        System.out.println("Moment:" + this.getDtLastProcess().toString());

        if (getDtLastProcess().getSecond()%2 == 0){
            throw new TimerException();
        }

        return message;
    }
}
