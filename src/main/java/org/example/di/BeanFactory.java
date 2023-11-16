package org.example.di;

import org.example.annotation.Inject;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.*;

public class BeanFactory {
    private final Set<Class<?>> preInstantiatedClazz;
    private Map<Class<?>, Object> beans = new HashMap<>();

    public BeanFactory(Set<Class<?>> preInstantiatedClazz) {
        this.preInstantiatedClazz = preInstantiatedClazz;
        initialize(); // beans 필드 초기화
    }

    /**
     * beans 필드 초기화 <br/>
     * 리플랙션을 통해 초기화 된 preInstantiatedClazz로부터 <br/>
     * 루프를 통해 순차적으로 beans 맵 겍체에 추가한다.
     */
    private void initialize() {
        for (Class<?> clazz : preInstantiatedClazz) {
            Object instance = createInstance(clazz);
            beans.put(clazz, instance);
        }
    }

    /**
     * 인스턴스 생성 메소드 <br/>
     * 생성한 인스턴스를 기준으로 Map타입 beans필드를 초기화 해야 하므로
     * beans 필드를 초기화하는 initialize() 메소드 내부에서 호출된다. <br/>
     * @param clazz
     * @return
     */
    private Object createInstance(Class<?> clazz) {

        //생성자
        Constructor<?> constructor = findConstructor(clazz); // clazz타입 객체로 Counstructor 조회
        //파라미터
        List<Object> parameters = new ArrayList<>();
        for (Class<?> typeClass : constructor.getParameterTypes()) {
            parameters.add(getParameterByClass(typeClass)); // 순차적인 parameterTypes에 대한 typeClass 객체에 대한 인스턴스를 가져온다.
        }
        //인스턴스 생성
        try {
            return constructor.newInstance(parameters.toArray());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 매개값 클래스타입 객체로 부터 @Inject 어노테이션이 붙은 생성자를 한개 반환해준다.
     * @param clazz
     * @return
     */
    private Constructor<?> findConstructor(Class<?> clazz) {

        Constructor<?> constructor = BeanFactoryUtils.getInjectedConstructor(clazz); // 생성자 반환

        if (Objects.nonNull(constructor)) { //counstructor가 존재하면 return
            return constructor;
        }
        return clazz.getConstructors()[0]; // counstructor가 존재하지 않으면 클래스타입 객체로부터 첫번째 생성자 return
    }

    /**
     * 파라미터(필드) 정보를 가져올 때 호출한다. <br/>
     * 전달받은 클래스타입 객체로부터 instance 빈을 받아온 뒤  <br/>
     * bean이 존재한다면 해당 빈을 반환, 존재하지않는다면 타입 클래스에 대한 인스턴스를 재생성해서 반환한다.
     * @param typeClass
     * @return
     */
    private Object getParameterByClass(Class<?> typeClass) {
        Object instanceBean = getBean(typeClass);
        if (Objects.nonNull(instanceBean)) {
            return instanceBean;
        }
        return createInstance(typeClass); // 타입클래스 재 반환
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
