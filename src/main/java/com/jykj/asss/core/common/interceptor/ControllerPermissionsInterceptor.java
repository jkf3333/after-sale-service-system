package com.jykj.asss.core.common.interceptor;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jykj.asss.core.Constants;
import com.jykj.asss.core.bean.SystemUser;
import com.jykj.asss.core.common.annotatino.ControllerPermission;
import com.jykj.asss.core.common.annotatino.ControllerPermissionType;
import com.jykj.asss.core.common.util.MyStringUtils;

/**controller访问前，需要验证是否具有访问权限
 * 即区别用户是否需要登录、游客、公共、管理员
 * ***/
public class ControllerPermissionsInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//处理访问权限
		HttpSession session = request.getSession();
		SystemUser currentUser = (SystemUser) session.getAttribute(Constants.SESSION_KEY_USER_INFO);
		if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            ControllerPermission cpAnnotation = hm.getMethodAnnotation(ControllerPermission.class);
            if(cpAnnotation == null){
            	noPass("Controller 没有设置权限["+hm.toString()+"]",request,response);
            }else{
            	ControllerPermissionType type = cpAnnotation.value();
            	switch(type){
            		case PUBLIC:
            			return true;
            		case AFTER_LOGIN:
            			if(currentUser == null){
            				return noPass("Controller 当前用户没有登陆，无法访问:["+hm.toString()+"]",request,response);
            			}else{
            				return true;
            			}
            		case ONLY_LOCAL:
            			if("0.0.0.0.0.0".equals(request.getRemoteHost())){
            				return true;
            			}else{
                            return noPass("Controller 当前请求只允许本地访问:["+hm.toString()+"]", request, response);
            			}
            		case CUSTOMER:
            			if(currentUser == null){
            				return noPass("Controller 当前用户没有登陆，无法访问:["+hm.toString()+"]",request,response);
            			}else{
            				String[] prefix = cpAnnotation.prefix();
                            if (prefix == null || prefix.length == 0 || MyStringUtils.isBlank(prefix[0])) {
                                return noPass("Controller 方法 [" + hm.toString() + "]使用自定义权限却未设置前缀!",request, response);
                            }else{
								List<String> userMenu = (List<String>) session.getAttribute(Constants.SESSION_USER_MENU);
                            	if(CollectionUtils.isEmpty(userMenu)){
                            		return noPass("Controller 当前登陆者的访问菜单为空，无法访问菜单，user_id="+currentUser.getUser_id(),request,response);
                            	}else{
                            		for(String menu : userMenu){
                            			if (MyStringUtils.isNotBlank(menu)) {
                            				for(String pre : prefix){
                            					if(menu.equals(pre)){
                            						return true;
                            					}
                            				}
                            			}
                            		}
                            	}
                            }
            			}
            			return noPass("Controller 权限验证失败,用户[" + currentUser.getUser_id() + "]试图访问[" + request.getRequestURI() + "].", request, response);
            		default:
                        return noPass("Controller 权限限制方式不存在,URL:"+request.getRequestURI(), request, response);
            	}
            }
        }
		return true; 
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	/**没有通过验证，用户无法访问**/
	private boolean noPass(String msg, HttpServletRequest request, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_PAYMENT_REQUIRED);
		response.setCharacterEncoding("UTF-8");
        String loginPath = request.getContextPath() + "/" + Constants.LOGIN_PAGE;
        PrintWriter writer;
		try {
			writer = response.getWriter();
			writer.write("<script>location.href='" + loginPath + "';</script>");
	        writer.flush();
	        writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
