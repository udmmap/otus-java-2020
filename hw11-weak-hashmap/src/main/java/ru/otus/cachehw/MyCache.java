package ru.otus.cachehw;

import java.util.HashSet;
import java.util.WeakHashMap;

/**
 * @author sergey
 * @author Korepanov D.
 * created on 14.12.18.
 */
public class MyCache<K, V> implements HwCache<K, V> {

    private WeakHashMap<K,V> cacheMap = new WeakHashMap();
    private HashSet<HwListener> listeners = new HashSet<HwListener>();

    private void notifyListeners(K key, V value, String act){
        listeners.forEach(l->{try{l.notify(key, value, act);}catch (Exception e){}});
    }

    public Integer getSize(){
        return cacheMap.size();
    }

    @Override
    public void put(K key, V value) {
        notifyListeners(key, value, "put");

        cacheMap.put(key, value);
    }

    @Override
    public void remove(K key) {
        notifyListeners(key, null, "remove");
        cacheMap.remove(key);
    }

    @Override
    public V get(K key) {
        var r = cacheMap.getOrDefault(key, null);
        notifyListeners(key, r, "get");
        return r;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }
}
