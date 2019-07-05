package com.ceyeclon.monitoringapp.service;

import java.util.List;
import java.util.Set;

public interface CacheService<T, V> {

    void cache(V v);

    void clearOutdatedData(long periodToLeave);

    List<V> fetchNLastEntries(T t, int lastEntriesNumber);

    Set<T> getDevices();
}
