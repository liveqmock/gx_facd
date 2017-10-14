package com.wangzhixuan.model;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 考试信息
 * </p>
 *
 * @author sunbq
 * @since 2017-08-22
 */
public class Examination extends Model<Examination> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String info;
    /**
     * 类型：1-随机选题，2-手动选题
     */
    private int type;
    
    /**
     * 状态：0-删除；1-创建；2-发布；
     */
    private int status;
    
	/**
     * 创建人id
     */
    private Integer createUser;
    /**
     * 创建时间
     */

    private Date createTime;
    /**
     * 考试开始日期
     */
    @TableField("begin_time")
    private Date beginTime;
    /**
     * 考试截止日期
     */
    @TableField("end_time")
    private Date endTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    public int getStatus() {
  		return status;
  	}

  	public void setStatus(int status) {
  		this.status = status;
  	}
  	

	@Override
    protected Serializable pkVal() {
        return this.id;
    }

	

	
   
}
