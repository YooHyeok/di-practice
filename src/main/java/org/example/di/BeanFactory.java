package org.example.di;

import java.util.Set;

public class BeanFactory {
    private final Set<Class<?>> preInstantiatedClazz;

    public BeanFactory(Set<Class<?>> preInstantiatedClazz) {
        this.preInstantiatedClazz = preInstantiatedClazz;
    }
}
