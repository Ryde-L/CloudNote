package com.cloudnote.sso.pojo;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ryde
 * @since 2019-09-10T10:22:00Z
 */
@SuppressWarnings("serial")
public class Administrator implements Serializable {
	/**
     * 
	 */
	private Integer id;
	/**
     * 
	 */
	private String name;
	/**
     * 
	 */
	private String pwd;

	/**
     * 获取 
	 */
	public Integer getId() {
		return id;
	}

	/**
     * 设置 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
     * 获取 
	 */
	public String getName() {
		return name;
	}

	/**
     * 设置 
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
     * 获取 
	 */
	public String getPwd() {
		return pwd;
	}

	/**
     * 设置 
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}