package com.ssnyapp.wxapp.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * 现场招聘会
 * 
 * @author clyao
 * @email clyao@163.com
 * @date 2017-11-02 13:16:10
 */
public class JobFairDO implements Serializable {
	private static final long serialVersionUID = 1L;

	//招聘会的id
	private int id;

	//楼层
	private String floor;

	//星期
	private String jobWeek;

	//届数
	private int expireNum;

	//参会开会时间
	private Date joinDateStart;

	//参会结束时间
	private Date joinDateEnd;

	//地址
	private String address;

	//主题
	private String theme;

	//创建时间
	private Date createDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getJobWeek() {
		return jobWeek;
	}

	public void setJobWeek(String jobWeek) {
		this.jobWeek = jobWeek;
	}

	public int getExpireNum() {
		return expireNum;
	}

	public void setExpireNum(int expireNum) {
		this.expireNum = expireNum;
	}

	public Date getJoinDateStart() {
		return joinDateStart;
	}

	public void setJoinDateStart(Date joinDateStart) {
		this.joinDateStart = joinDateStart;
	}

	public Date getJoinDateEnd() {
		return joinDateEnd;
	}

	public void setJoinDateEnd(Date joinDateEnd) {
		this.joinDateEnd = joinDateEnd;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
