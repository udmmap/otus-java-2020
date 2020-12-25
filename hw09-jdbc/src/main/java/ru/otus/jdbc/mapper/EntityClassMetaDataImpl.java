package ru.otus.jdbc.mapper;

import ru.otus.jdbc.annotation.ID;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData{
    private Class<T> clazz;
    private final List<Field> idFields;
    private final List<Field> nIdFields;

    public EntityClassMetaDataImpl(Class<T> clazz){
        this.clazz = clazz;
        idFields = Arrays.stream(clazz.getDeclaredFields()).filter(f->f.isAnnotationPresent(ID.class))
        .collect(Collectors.toList());
        nIdFields = Arrays.stream(clazz.getDeclaredFields()).filter(f->!f.isAnnotationPresent(ID.class))
                .collect(Collectors.toList());
    }

    @Override
    public String getName() {
        return clazz.getSimpleName();
    }

    @Override
    public Constructor getConstructor() {
        try {
            return clazz.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Field getIdField() {
        if(idFields!=null && idFields.size()==1){
            return idFields.get(0);
        }
        return null;
    }

    @Override
    public List<Field> getAllFields() {
        List<Field> fAll = new ArrayList(nIdFields);
        fAll.addAll(idFields);
        return fAll;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return List.copyOf(nIdFields);
    }
}
