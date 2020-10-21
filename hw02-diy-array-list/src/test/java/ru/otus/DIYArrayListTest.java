package ru.otus;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DIYArrayListTest {
    @Test
    void DIYArrayListTest1(){
        List<Integer> example = new ArrayList<>();
        int min = 0;
        int max = 100;
        for (int i = min; i < max; i++) {
            example.add(i);
        }
        DIYArrayList aDiy = new DIYArrayList<Integer>();
        //Проверка addAll
        aDiy.addAll(example);
        Object[] aNative = aDiy.toArray();
        assertThat(example.toArray()).isEqualTo(aNative);
    }
    @Test
    void DIYArrayListTest2(){
        List<Integer> example = new ArrayList<>();
        int min = 0;
        int max = 100;
        for (int i = min; i < max; i++) {
            example.add(i);
        }
        DIYArrayList aDiy = new DIYArrayList<Integer>();
        //Инициализация массива
        aDiy.addAll(example);
        Collections.reverse(example);
        //Копирование example -> aDiy
        Collections.copy(aDiy, example);
        Object[] aNative = aDiy.toArray();
        assertThat(example.toArray()).isEqualTo(aNative);
    }

    @Test
    void DIYArrayListTest3(){
        List<Integer> example = new ArrayList<>();
        int min = 0;
        int max = 100;
        //Числа в обратном порядке
        for (int i = max; i > min; i--) {
            example.add(i);
        }
        DIYArrayList aDiy = new DIYArrayList<Integer>();
        //Инициализация массива
        aDiy.addAll(example);
        //Сортировка с компаратором по умолчанию
        Collections.sort(aDiy);
        Object[] aNative = aDiy.toArray();
        //Сортировка тестового листа
        Collections.sort(example);
        assertThat(example.toArray()).isEqualTo(aNative);
    }

}
