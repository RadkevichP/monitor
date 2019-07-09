package com.ceyeclon.monitoringapp.service;

import java.util.List;

public interface CacheService<T, V> {

    void cache(V v);

    List<V> fetchNLastEntries(T t, int lastEntriesNumber);

    List<T> getDevices();

    void archiveNotesOlderThan(long archiveTimeMinutes);
}
