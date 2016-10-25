package com.jykj.asss.core.common.annotatino;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**限制用户的访问次数
 * runtime 注解的生命周期：在运行时有效(即运行时保留)
 * method 注解的作用域：方法
 * limit()访问次数
 * time()时间范围
 * ***/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestLimitAnnotation {
	int limit() default 10;
	int time() default 60000;//6s
}
