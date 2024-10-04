package com.ennaru.monitor.docker.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ContainerId를 정의하는 어노테이션
 */
@Target(ElementType.METHOD)
public @interface ContainerId {
}
