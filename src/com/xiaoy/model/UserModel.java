package com.xiaoy.model;

/**
 * @author XiaoY
 * @date: 2015年9月5日 下午6:38:51
 */
public class UserModel {

	private String loginName;// 登录名
	private String username;// 用户姓名
	private String sex;// 性别
	private String number;// 联系电话

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
}
