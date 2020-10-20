package ru.otus;
import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;


public class DIYArrayListTest {
    @Test
    void DIYArrayListTest(){
        List<Integer> example = new ArrayList<>();
        int min = 0;
        int max = 100;
        for (int i = min; i < max; i++) {
            example.add(i);
        }
        DIYArrayList aDiy = new DIYArrayList<Integer>();
        aDiy.addAll(example);
        Object[] aNative = aDiy.toArray();
        assertThat(example.toArray()).isEqualTo(aNative);
    }
}
