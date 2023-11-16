package org.example.di;

import org.example.annotation.Inject;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.util.Set;

public class BeanFactoryUtils {

    /**
     * Reflection에서 제공해주는 ReflectionsUtils의 getAllConstructors() <br/>
     * 넘겨받은 클래스 타입 객체의 모든 생성자를 가져온다. <br/>
     * 딘, Inject 어노테이션이 붙은 생성자만 가져온다.
     */
    public static Constructor<?> getInjectedConstructor(Class<?> clazz) {
        Set<Constructor> injectedConstructors = ReflectionUtils.getAllConstructors(clazz, ReflectionUtils.withAnnotation(Inject.class));
        if (injectedConstructors.isEmpty()) {
            return null;
        }
        return injectedConstructors.iterator().next();
    }
}
