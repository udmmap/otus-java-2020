package ru.otus.framework;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Tester {

    // Вспомогательный метод для создания списка объектов-параметров и запуска метода
    private static void runMethod(Object o, Method m)
            throws InvocationTargetException, IllegalAccessException, InstantiationException, RuntimeException {
        List<Object> aP = new ArrayList<Object>(); // Массив параметров
        for (Class<?> cP:m.getParameterTypes()){
            int i = aP.size();
            Constructor<?> crP = null;
            try {
                crP = cP.getConstructor();
                aP.add(crP.newInstance());
            } catch (NoSuchMethodException e) {
                //Для подбора конструктора параметра в самых простых случаях
            }
            if (i==aP.size()) {
                // Предыдущая попытка подобрать конструктор оказалась неудачной
                // Делаем попытку использовать конструктор с другой сигнатурой
                try {
                    crP = cP.getConstructor(int.class);
                    aP.add(crP.newInstance(0));
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(
                            String.format("Не удалось инициировать параметры метода %s", m.getName()));
                }
            }
        }
        m.invoke(o, aP.toArray());
    }

    // Запуск теста
    public static String run(String className) throws ClassNotFoundException {
        StringBuilder stLog = new StringBuilder();
        String Result="class: %s\nruns: %d\npassed: %d\nfailed: %d";
        int iR=0;
        int iP=0;
        int iE=0;

        Class cT = Class.forName(className);

        List<Method> alBefore = new ArrayList<Method>();
        List<Method> alAfter = new ArrayList<Method>();
        List<Method> alTest = new ArrayList<Method>();

        for (Method m : cT.getDeclaredMethods()) {
            if (m.isAnnotationPresent(Test.class)) {
                alTest.add(m);
            } else if (m.isAnnotationPresent(Before.class)) {
                alBefore.add(m);
            } else if (m.isAnnotationPresent(After.class)) {
                alAfter.add(m);
            }
        }

        for(Method mT : alTest){
            try {
                iR++;

                Object oT = cT.getDeclaredConstructor().newInstance();
                for (Method mB : alBefore) {
                    stLog.append(String.format("\n@Before %s",mB.getName()));
                    runMethod(oT, mB);
                }

                stLog.append(String.format("\n@Test %s",mT.getName()));
                runMethod(oT, mT);

                for (Method mA : alAfter) {
                    stLog.append(String.format("\n@After %s",mA.getName()));
                    runMethod(oT, mA);
                }

                iP++;
            } catch (Exception e) {
                iE++;
                stLog.append("\n"+e.getMessage());
            }
        }
        return stLog.append("\n\n"+String.format(Result, className, iR, iP, iE)).toString();
    }
}
