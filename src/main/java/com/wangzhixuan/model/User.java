package com.wangzhixuan.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.wangzhixuan.commons.utils.JsonUtils;

/**
 *
 * 用户
 *
 */
public class User implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 主键id */
	@TableId(type = IdType.AUTO)
	private Long id;

	/** 登陆名 */
	@TableField(value = "login_name")
	private String loginName;

	/** 用户名 */
	private String name;

	/** 密码 */
	private String password;
	
	/** 密码加密盐 */
	private String salt;

	/** 性别 */
	private Integer sex;

	/** 年龄 */
	private Integer age;

	/** 手机号 */
	private String phone;

	/** 用户类别 */
	@TableField(value = "user_type")
	private Integer userType;

	/** 用户状态 */
	private Integer status;

	/** 所属机构 */
	@TableField(value = "organization_id")
	private String organizationId;

	/** 创建时间 */
	@TableField(value = "create_time")
	private Date createTime;
	/**
	 * 头像
	 */
	private String icon;
    /**
     * 生日
     */
	private String birthday;
    /**
     * 01 中共党员；02 中共预备党员；03 共青团员；04 民革党员；05 民盟盟员；06 民建会员；07 民进会员；08 农工党党员；09 致公党党员；10 九三学社社员；11 台盟盟员；12 无党派人士；13 群众
     */
	private String politics;
	/**
	 * 巡防路段
	 */
	private String road;
	/**
	 * 家庭地址
	 */
	@TableField("home_adress")
	private String homeAdress;
	/**
	 * 用户标识符
	 */
	private String token;
	/**
	 * 设备码
	 */
	private String devicenum;
    /**
     * 是否授权：0-未授权；1-已授权
     */
	@TableField("is_grant")
	private Integer isGrant;
	private String uuid;


	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Integer getSex() {
		return this.sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getUserType() {
		return this.userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getOrganizationId() {
		return this.organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getPolitics() {
		return this.politics;
	}

	public void setPolitics(String politics) {
		this.politics = politics;
	}

	public String getRoad() {
		return road;
	}

	public void setRoad(String road) {
		this.road = road;
	}

	public String getHomeAdress() {
		return this.homeAdress;
	}

	public void setHomeAdress(String homeAdress) {
		this.homeAdress = homeAdress;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getDevicenum() {
		return devicenum;
	}

	public void setDevicenum(String devicenum) {
		this.devicenum = devicenum;
	}

	public Integer getIsGrant() {
		return isGrant;
	}

	public void setIsGrant(Integer isGrant) {
		this.isGrant = isGrant;
	}
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public String toString() {
		return JsonUtils.toJson(this);
	}


}
