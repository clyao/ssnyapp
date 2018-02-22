package com.ssnyapp.wxapp.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * 现场招聘会求职者
 * 
 * @author clyao
 * @email clyao@163.com
 * @date 2017-11-02 13:16:10
 */
public class JobSeekerDO implements Serializable {
	private static final long serialVersionUID = 1L;

	//求职者id
	private int id;

	//求职者姓名
	private String userName;

	//求职者户籍
	private String nation;

	//求职者性别
	private String sex;

	//求职者婚姻状况
	private String marriage;

	//求职者的毕业院校
	private String school;

	//求职者的专业
	private String speciality;

	//求职者的求职意向
	private String position;

	//求职者的地址
	private String address;

	//求职者的手机号
	private String telephone;

	//求职者的邮箱
	private String email;

	//求职者的学历
	private String degree;

	//求职的出去日期
	private Date brthday;

	//创建时间
	private Date createDate;

	private int state;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getMarriage() {
		return marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public Date getBrthday() {
		return brthday;
	}

	public void setBrthday(Date brthday) {
		this.brthday = brthday;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}
