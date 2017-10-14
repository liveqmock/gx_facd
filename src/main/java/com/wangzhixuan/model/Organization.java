package com.wangzhixuan.model;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wangzhixuan.commons.utils.JsonUtils;

/**
 *
 * 组织机构
 *
 */
public class Organization implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 主键id */
	@TableId(type = IdType.AUTO)
	@JsonProperty("zid")
	private Long id;

	/** 组织名 */
	@NotBlank
	private String name;

	/** 地址 */
	private String address;

	/** 编号 */
	@NotBlank
	@JsonProperty("id")
	private String code;

	/** 图标 */
	@JsonProperty("iconCls")
	private String icon;

	/** 父级主键 */
	private String pid;

	/** 排序 */
	private Integer seq;

	/** 创建时间 */
	@TableField(value = "create_time")
	private Date createTime;

	/** 系统访问url */
	@TableField(value = "system_address")
	private String systemAddress;

	/** 是否是子系统：1-是，0-否 */
	@TableField(value = "is_system")
	private Integer isSystem;
	
	/** ssh连接ip */
	private String remoteip;
	/** ssh连接端口 */
	private String remoteport;
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPid() {
		return this.pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getSystemAddress() {
		return systemAddress;
	}

	public void setSystemAddress(String systemAddress) {
		this.systemAddress = systemAddress;
	}

	public Integer getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(Integer isSystem) {
		this.isSystem = isSystem;
	}

	public String getRemoteip() {
		return remoteip;
	}

	public void setRemoteip(String remoteip) {
		this.remoteip = remoteip;
	}

	public String getRemoteport() {
		return remoteport;
	}

	public void setRemoteport(String remoteport) {
		this.remoteport = remoteport;
	}

	@Override
	public String toString() {
		return JsonUtils.toJson(this);
	}
}
