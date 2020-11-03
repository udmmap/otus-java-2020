package ru.otus;

import ru.otus.framework.After;
import ru.otus.framework.Before;
import ru.otus.framework.Test;

public class Example<T> {

    @Before
    public String firstBefore(String arg){
        return arg;
    }

    @Test
    public Integer firstTest(Integer arg){
        return arg;
    }

    @Test
    public T secondTest(T arg, String arg2){
        return arg;
    }

    @Test
    public char thirdTest(char arg){
        return arg;
    }

    @Test
    public int fourTest(int arg){
        return arg;
    }

    @After
    public void firstAfter(){
    }

    @After
    public Object[] secondAfter(Object[] arg){
        return arg;
    }

}
