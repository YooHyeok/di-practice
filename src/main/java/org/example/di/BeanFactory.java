package org.example.di;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BeanFactory {
    private final Set<Class<?>> preInstantiatedClazz;
    private Map<Class<?>, Object> beans = new HashMap<>();

    public BeanFactory(Set<Class<?>> preInstantiatedClazz) {
        this.preInstantiatedClazz = preInstantiatedClazz;
    }

    /**
     * 인자로 넘어온 클래스타입 객체를 key값으로 Map타입 멤버변수 beans로부터 Object를 반환받는다.
     * @param requiredType
     * @return
     * @param <T>
     */
    public <T> T getBean(Class<T> requiredType) {
        return (T) beans.get(requiredType);
    }
}
