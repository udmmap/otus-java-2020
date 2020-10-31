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
    public int firstTest(Integer arg){
        return arg;
    }

    @Test
    public T secondTest(T arg){
        return arg;
    }

    @Test
    public Object[] thirdTest(Object[] arg){
        return arg;
    }

    @After
    public void firstAfter(){

    }

}
