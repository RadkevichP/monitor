package com.ceyeclon.monitoringapp.service;

import java.util.List;

public interface CacheService<T, V> {

    void cache(V v);

    void clearOutdatedData(long periodToLeave);

    List<V> fetchNLastEntries(T t, int lastEntriesNumber);

    List<T> getDevices();
}
