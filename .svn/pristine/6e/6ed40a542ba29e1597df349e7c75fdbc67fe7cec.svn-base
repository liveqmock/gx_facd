package com.wangzhixuan.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 产品版本管理
 * </p>
 *
 * @author sunbq
 * @since 2017-06-14
 */
@TableName("app_version")
public class AppVersion extends Model<AppVersion> {

    private static final long serialVersionUID = 1L;

    @TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 产品名称
     */
	private String name;
	/**
	 * 类型 1-移动端；2-手机端
	 */
	private Integer type;
    /**
     * 产品地址
     */
	private String url;
    /**
     * 版本号
     */
	private String version;
    /**
     * 更新版本原因
     */
	private String raeson;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
	/**
	 * 状态 2-停用; 1-启用；0-未启用
	 * @return
	 */
	private Integer status;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getRaeson() {
		return raeson;
	}

	public void setRaeson(String raeson) {
		this.raeson = raeson;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
