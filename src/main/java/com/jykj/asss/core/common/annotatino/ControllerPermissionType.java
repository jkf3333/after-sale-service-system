package com.jykj.asss.core.common.annotatino;


/**自定义注解：用来控制controller的访问限制***/
public enum ControllerPermissionType {
	PUBLIC,//公共
	AFTER_LOGIN,//需要登录
	ONLY_ADMIN,//仅管理员
	CUSTOMER,//游客
	ONLY_LOCAL//本地
}
