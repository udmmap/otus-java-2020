package ru.otus;

import java.util.Arrays;

class ArrayBlock<T> {
    public static final int defaultBlockSize = 16;
    private Object[] block;
    private int size;//Размер информативной части блока

    ArrayBlock(T[] aInitial){
        //Если инициирующий массив превышает дефолтный размер блока, блок увеличивается до размеров массива
        block = new Object[(aInitial.length < defaultBlockSize)?defaultBlockSize:aInitial.length];
        System.arraycopy(aInitial,0, block,0,aInitial.length);
        size = (aInitial.length < block.length)?aInitial.length:block.length;
    }

    ArrayBlock(){
        block = new Object[defaultBlockSize];
        size = 0;
    }

    public void add(Object[] aAdd){
        int iSizeFree = getSizeFree();
        if (iSizeFree<aAdd.length){
            //Если оказалось, что добавляемый массив больше доступного в блоке пространства
            Object[] aTmp = Arrays.copyOf(block, block.length + aAdd.length);
            System.arraycopy(aAdd, 0, aTmp, block.length, aAdd.length);
            block = aTmp;
            size = block.length;
        } else {
            System.arraycopy(aAdd, 0, block, size, aAdd.length);
            size+=aAdd.length;
        }
    }

    public int getSize(){
        return size;
    }

    public Object[] toArray(){
        return Arrays.copyOfRange(block,0,size);
    }

    public int getSizeFree(){
        return block.length - size;
    }

    public T get(int index) {return (T)block[index];}
}
