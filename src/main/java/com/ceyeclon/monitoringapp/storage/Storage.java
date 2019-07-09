package com.ceyeclon.monitoringapp.storage;

import java.util.List;
import java.util.Set;

public interface Storage<T, V> {

    void store(V v);

    List<V> fetchAllEntries(T t);

    List<V> fetchNLastEntries(T t, int lastEntriesNumber);

    Set<T> getStoredKeys();

    void archiveOlderThan(long minutes);

}
