package ru.otus;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SerializerTest {
    @Test
    @DisplayName("Сериализация null")
    public void NullObjectSerializationTest(){
        Gson gson = new Gson();
        Example exmp = null;
        assertThat(gson.fromJson(Serializer.toJson(exmp), Example.class)==null);
    }

    @Test
    @DisplayName("Сериализация объекта")
    public void ObjectSerializationTest(){
        Gson gson = new Gson();
        Example exmp = new Example();
        assertThat(exmp.equals(gson.fromJson(Serializer.toJson(exmp), Example.class)));
    }

}
