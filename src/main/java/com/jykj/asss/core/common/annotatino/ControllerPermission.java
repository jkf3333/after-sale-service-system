package com.jykj.asss.core.common.annotatino;


/**自定义注解：用来控制controller的访问限制***/
public @interface ControllerPermission {
	ControllerPermissionType value();//用户的登陆类型
	String[] prefix();//用户访问controller的前缀
}
