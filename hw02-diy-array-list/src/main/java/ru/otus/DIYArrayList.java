package ru.otus;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import java.util.Collections;


/**
 * Лист, в основе которого лежит массив из блоков {@code ArrayBlock}
 * @see List
 */
public class DIYArrayList<T> implements List<T> {
    private ArrayBlock<T>[] dataBlocks = null;
    private int size = 0;

    public DIYArrayList() {
        super();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public T get(int index) {
        int blockNum=0;
        int aggSize=0;
        while (aggSize+dataBlocks[blockNum].getSize() <= index){
            aggSize+=dataBlocks[blockNum].getSize();
            blockNum++;
        }
        return dataBlocks[blockNum].get(index-aggSize);
    }

    @Override
    public T set(int index, T element) {
        int blockNum=0;
        int aggSize=0;
        while (aggSize+dataBlocks[blockNum].getSize() <= index){
            aggSize+=dataBlocks[blockNum].getSize();
            blockNum++;
        }
        return dataBlocks[blockNum].set(index-aggSize,element);
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException("Invalid operation for DIY ArrayList.");
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException("Invalid operation for DIY ArrayList.");
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException("Invalid operation for DIY ArrayList.");
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("Invalid operation for DIY ArrayList.");
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ListIter(0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException("Invalid operation for DIY ArrayList.");
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Invalid operation for DIY ArrayList.");
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Invalid operation for DIY ArrayList.");
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException("Invalid operation for DIY ArrayList.");
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException("Invalid operation for DIY ArrayList.");
    }

    @Override
    public Object[] toArray() {
        return Arrays.stream(dataBlocks).flatMap(block->Stream.of(block.toArray()))
                .toArray(Object[]::new);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException("Invalid operation for DIY ArrayList.");
    }

    @Override
    public boolean add(T t) {
        throw new UnsupportedOperationException("Invalid operation for DIY ArrayList.");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Invalid operation for DIY ArrayList.");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Invalid operation for DIY ArrayList.");
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        Object[] aSrc = c.toArray();
        ArrayBlock<T> curBlock;
        int iSrcSize = aSrc.length;
        int iOverSize = iSrcSize;
        int iLastSize=0;
        int iFrBeg=0;
        int iFrEnd=0;
        int iBlockNum;
        if (iSrcSize==0){
            return false;
        }
        if (iSrcSize > 0) {
            if (dataBlocks == null) {
                iLastSize = iSrcSize % ArrayBlock.defaultBlockSize;
                dataBlocks = new ArrayBlock[
                        iSrcSize / ArrayBlock.defaultBlockSize
                                + ((iLastSize > 0) ? 1 : 0)];
                iBlockNum = 0;
                dataBlocks[iBlockNum] = new ArrayBlock();
            } else {
                iBlockNum = dataBlocks.length - 1;
                iOverSize = (iSrcSize - dataBlocks[iBlockNum].getSizeFree());
                iLastSize = iSrcSize % ArrayBlock.defaultBlockSize;
                Object[] aTmp = Arrays.copyOf(
                        dataBlocks
                        , dataBlocks.length
                        + iOverSize / ArrayBlock.defaultBlockSize
                                + ((iLastSize > 0) ? 1 : 0)
                );
                if (dataBlocks[iBlockNum].getSizeFree()==0){
                    iBlockNum++;
                }
            }
            while (iFrEnd < iSrcSize) {
                if (dataBlocks[iBlockNum]==null) {
                    curBlock = new ArrayBlock<T>();
                    dataBlocks[iBlockNum] = curBlock;
                } else {
                    curBlock = dataBlocks[iBlockNum];
                }
                iFrEnd = (iSrcSize<(iFrBeg+curBlock.getSizeFree()))?
                        iSrcSize : iFrBeg+curBlock.getSizeFree();
                curBlock.add(Arrays.copyOfRange(aSrc,iFrBeg,iFrEnd));
                iFrBeg = iFrEnd;
                iBlockNum++;
            }
            size+=iSrcSize;
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException("Invalid operation for DIY ArrayList.");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Invalid operation for DIY ArrayList.");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Invalid operation for DIY ArrayList.");
    }

    @Override
    public void replaceAll(UnaryOperator<T> operator) {
        throw new UnsupportedOperationException("Invalid operation for DIY ArrayList.");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Invalid operation for DIY ArrayList.");
    }

    @Override
    public <T1> T1[] toArray(IntFunction<T1[]> generator) {
        throw new UnsupportedOperationException("Invalid operation for DIY ArrayList.");
    }

    @Override
    public boolean removeIf(Predicate<? super T> filter) {
        throw new UnsupportedOperationException("Invalid operation for DIY ArrayList.");
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        throw new UnsupportedOperationException("Invalid operation for DIY ArrayList.");
    }

    @Override
    public Spliterator<T> spliterator() {
        throw new UnsupportedOperationException("Invalid operation for DIY ArrayList.");
    }

    @Override
    public Stream<T> stream() {
        throw new UnsupportedOperationException("Invalid operation for DIY ArrayList.");
    }

    @Override
    public Stream<T> parallelStream() {
        throw new UnsupportedOperationException("Invalid operation for DIY ArrayList.");
    }

    private class ListIter implements ListIterator<T> {
        int cursor;
        int cursorBlock;
        int cursorElement; // index of next element to return
        int lastRet = -1;
        int lastRetBlock = -1;
        int lastRetElement = -1; // index of last element returned; -1 if no such

        ListIter (int index){
            int blockNum=0;
            int aggSize=0;
            while (aggSize+dataBlocks[blockNum].getSize() <= index){
                aggSize+=dataBlocks[blockNum].getSize();
                blockNum++;
            }
            cursorBlock = blockNum;
            cursorElement = index-aggSize;
            cursor = index;
        }

        @Override
        public boolean hasNext() {
            return cursor < DIYArrayList.this.size;
        }

        @Override
        public T next() {
            int i = cursor;
            int iB = cursorBlock;
            int iE = cursorElement;
            if (i >= size)
                throw new NoSuchElementException();
            cursor++;
            cursorElement++;
            if (cursor<size) {
                while (dataBlocks[cursorBlock].getSize() == cursorElement) {
                    cursorBlock++;
                    cursorElement = 0;
                }
            }
            lastRet = i;
            lastRetBlock = iB;
            lastRetElement = iE;
            return dataBlocks[iB].get(iE);
        }

        @Override
        public boolean hasPrevious() {
            return cursor != 0;
        }

        @Override
        public T previous() {
            throw new UnsupportedOperationException("Invalid operation for DIY ArrayList.");
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Invalid operation for DIY ArrayList.");
        }

        @Override
        public void forEachRemaining(Consumer<? super T> action) {
            throw new UnsupportedOperationException("Invalid operation for DIY ArrayList.");
        }

        @Override
        public void set(T t) {
            dataBlocks[lastRetBlock].set(lastRetElement,t);
        }

        @Override
        public void add(T t) {
            throw new UnsupportedOperationException("Invalid operation for DIY ArrayList.");
        }
    }
}
