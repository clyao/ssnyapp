package com.ssnyapp.wxapp.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * 现场招聘会企业职位
 * 
 * @author clyao
 * @email clyao@163.com
 * @date 2017-11-02 13:16:10
 */
public class CompanyDO implements Serializable {
	private static final long serialVersionUID = 1L;

	//职位id
	private int id;

	//公司名称
	private String companyName;

	//职位名称
	private String positionName;

	//工作地址
	private String workAddress;

	//工资
	private String salary;

	//联系人
	private String linkMan;

	//联系电话
	private String telephone;

	//职位描述
	private String description;

	//审核状态
	private int state;

	//创建时间
	private Date createDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getWorkAddress() {
		return workAddress;
	}

	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
