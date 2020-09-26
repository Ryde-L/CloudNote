package com.cloudnote.note.satoken;

import cn.dev33.satoken.stp.StpInterface;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 *    自定义权限验证接口扩展 
 */
@Component
public class StpInterfaceImpl implements StpInterface {


	/**
	 * 返回一个账号所拥有的权限码集合
	 * 目前暂时只有管理员和普通用户及游客，权限简单划分成用户操作和管理员操作
	 * @param login_id
	 * @param login_key
	 * @return 权限List
	 */
	@Override
	public List<Object> getPermissionCodeList(Object login_id, String login_key) {
		List<Object> list = new ArrayList<Object>();
		if (login_id instanceof String){
			if (((String) login_id).contains("user"))
				list.add("user-operation");
			if (((String) login_id).contains("admin")){
				list.add("admin-operation");
				list.add("user-operation");
			}
		}
		return list;
	}

}
