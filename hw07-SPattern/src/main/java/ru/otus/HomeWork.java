package ru.otus;

import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.ListenerPrinter;
import ru.otus.listener.homework.ListenerSaver;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;
import ru.otus.processor.LoggerProcessor;
import ru.otus.processor.ProcessorConcatFields;
import ru.otus.processor.ProcessorUpperField10;
import ru.otus.processor.homework.ProcessorExchange11and12;
import ru.otus.processor.homework.ProcessorTimer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeWork {
    public static void main(String[] args) {
        var processors = List.of(new ProcessorExchange11and12(),
                new ProcessorTimer());

        var complexProcessor = new ComplexProcessor(processors
                , (ex) -> {ex.printStackTrace();});
        var complexProcessor2 = new ComplexProcessor(processors
                , (ex) -> {ex.printStackTrace();});

        var listenerSaver = new ListenerSaver();

        complexProcessor.addListener(listenerSaver);
        complexProcessor2.addListener(listenerSaver);

        var objectForMessage = new ObjectForMessage();
        objectForMessage.setData(Arrays.asList("field","13"));

        var message = new Message.Builder(1L)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field6("field6")
                .field10("field10")
                .field11("field11")
                .field12("field12")
                .field13(objectForMessage)
                .build();

        var result = complexProcessor.handle(message);
        System.out.println("result:" + result);

        result = complexProcessor2.handle(result);
        System.out.println("result2:" + result);

        complexProcessor.removeListener(listenerSaver);
        complexProcessor2.removeListener(listenerSaver);

        System.out.println("LOG");
        listenerSaver.getLog().forEach((ent)->{
            System.out.print("OLD: ");
            System.out.println(ent.getKey());
            System.out.print("NEW: ");
            System.out.println(ent.getValue());
            System.out.println("-------------------");
        });
    }
}
