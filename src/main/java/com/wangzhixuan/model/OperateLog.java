package com.wangzhixuan.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 操作日志表
 * </p>
 *
 * @author sunbq
 * @since 2017-07-06
 */
@TableName("operate_log")
public class OperateLog extends Model<OperateLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 操作日志主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 操作人id
     */
	private Long userid;

	/**
	 * 操作人uuid
	 */
	private String uuid;

    /**
     * 操作内容
     */
	private String msg;
    /**
     * 日志类型
     */
	private String type;
    /**
     * 操作平台:0-pc,1-移动，2-Tv
     */
	private Integer terrace;
	private Date createtime;
	
	@TableField(exist=false)
    private String userName;
	
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getTerrace() {
		return terrace;
	}

	public void setTerrace(Integer terrace) {
		this.terrace = terrace;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
