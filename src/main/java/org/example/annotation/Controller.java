package org.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE}) // 클래스 인터페이스 enum에 사용할 수 있는 어노테이션 - TYPE 엘리먼트타입
@Retention(RetentionPolicy.RUNTIME) // 유지기간 : 런타임
public @interface Controller {
}
