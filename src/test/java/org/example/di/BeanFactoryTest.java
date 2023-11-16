package org.example.di;

import org.example.annotation.Controller;
import org.example.annotation.Service;
import org.example.controller.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BeanFactoryTest {
    private Reflections reflections;
    private BeanFactory beanFactory;

    /**
     * 테스트 메소드가 호출되기 전에 처음 호출되는 코드.
     */
    @BeforeEach
    void setUp() {
        reflections = new Reflections("org.example"); // org.example 패키지 하위 모든 클래스들을 대상으로 Reflections를 사용한다.
        
        /* Top Down 방식 : 현재 테스트코드를 작성하는 동안 존재하지 않지만 이미 있다고 가정하고 미리 작성하는 기법 */

        /**
         * TOP: (메소드) 먼저 선언 <br/>
         * getTypeAnnotatedWith <br/>
         * 어노테이션을 파라미터로 받아 annotation에 붙은 클래스타입 객체를 return해주는 메소드이다.<br/>
         * @return: UserController, UserService
         */
        Set<Class<?>>  preInstantiatedClazz = getTypesAnnotatedWith(Controller.class, Service.class);
        beanFactory = new BeanFactory(preInstantiatedClazz);
    }

    /**
     * DOWN: 미리 선언된 메소드를 구현 <br/>
     * org.example 패키지 하위의 @Controller, @Service Annotation을 받아서 reflection을 사용해 해당 어노테이션이 붙은 클래스 객체를 조회 <br/>
     * 조회해 온 객체를 Set<Class?>> beans 에 모두 추가한 뒤 해당 beans객체를 반환해준다.
     * @param annotations: 가변 객체
     * @return
     */
    private Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation>... annotations) {
        Set<Class<?>> beans = new HashSet<>();
        for (Class<? extends Annotation> annotation : annotations) {
            beans.addAll(reflections.getTypesAnnotatedWith(annotation));
        }
        return beans;
    }

    @Test
    void diTest() {
        UserController userController = beanFactory.getBean(UserController.class);// BeanFactory로부터 UserController에 대한 Bean을 반환받는다.
    }
}