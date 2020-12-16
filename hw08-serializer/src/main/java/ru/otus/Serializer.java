package ru.otus;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.Gson;

public class Serializer {
    private final static List wrapperTypes = Arrays.asList(
            Character.class
            , Byte.class
            , Short.class
            , Integer.class
            , Long.class
            , Float.class
            , Double.class
            , Boolean.class
    );
    private static String getScalarJsonString(Object obj) throws UnsupportedOperationException {
        String res;
        Class clazz = obj.getClass();
        if (clazz.isPrimitive() || wrapperTypes.contains(clazz)) {
            res = String.valueOf(obj);
        } else if (clazz.equals(String.class)) {
            res = "\"" + String.valueOf(obj) + "\"";
        } else {
            throw new UnsupportedOperationException("Преобразование данного типа не поддерживается, "
                    + clazz.getCanonicalName());
        }
        return res;
    }
    public static String toJson(Object obj){
        if (obj==null){return "null";}
        Class clazz = obj.getClass();
        final String delim = ",";
        var fields = clazz.getDeclaredFields();
        return "{"+Arrays.stream(fields).filter(f-> {
            try {
                return f.get(obj)!=null;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
        }).map(f->{
            String res;
            Class fClazz = f.getType();
            try {
                Object fObj = f.get(obj);
                if (Collection.class.isAssignableFrom(fClazz) || fClazz.isArray()) {
                    Stream smArray;
                    if (fClazz.isArray()) {
                        StringBuilder sbArray = new StringBuilder();
                        sbArray.append("[");

                        int iLen = Array.getLength(fObj);

                        for (int i=0; i<iLen; i++) {
                            sbArray.append(getScalarJsonString(Array.get(fObj,i)));
                            sbArray.append(delim);
                        }
                        sbArray.setLength(sbArray.length() - delim.length());
                        sbArray.append("]");
                        res = sbArray.toString();
                    } else {
                        res = "["+((Collection)fObj)
                                .stream()
                                .map(Serializer::getScalarJsonString)
                                .collect(Collectors.joining(delim))+"]";
                    }
                } else {
                    res=getScalarJsonString(fObj);
                }
            } catch (Exception e) {
                res="";
                e.printStackTrace();
            }

            return "\""+f.getName()+"\":"+res;
        })
        .collect(Collectors.joining(delim))+"}";
    }
}