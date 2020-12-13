package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

public class ListenerSaver implements Listener {
    private final LinkedList lLog
            = new LinkedList<Entry<Message,Message>>();
    @Override
    public void onUpdated(Message oldMsg, Message newMsg) {
        lLog.addLast(
            Map.entry(
                oldMsg.toBuilder().build()
                , newMsg.toBuilder().build()
            ));
    }

    public LinkedList<Entry<Message,Message>> getLog(){
        return new LinkedList<Entry<Message,Message>>(lLog);
    }
}
