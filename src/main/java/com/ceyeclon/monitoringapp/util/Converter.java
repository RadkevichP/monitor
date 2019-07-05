package com.ceyeclon.monitoringapp.util;

public interface Converter<T, V> {

    V convert(T t);
}
