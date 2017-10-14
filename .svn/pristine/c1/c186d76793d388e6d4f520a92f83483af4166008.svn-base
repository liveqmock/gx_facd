package com.wangzhixuan.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 定位标注
 * </p>
 *
 * @author sunbq
 * @since 2017-06-18
 */
@TableName("position_mark")
public class PositionMark extends Model<PositionMark> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 巡防ID
     */
	private String pid;
    /**
     * 界碑id；
     */
	private String mmid;
    /**
     * 标注名称
     */
	private String name;
    /**
     * 标注类型：0=界碑，1=巡防路，2=邻国边境部署
     */
	private Integer type;
    /**
     * 经度
     */
	private Double longitude;
    /**
     * 纬度
     */
	private Double latitude;
    /**
     * 标记内容描述
     */
	private String info;
    /**
     * 审核人
     */
	@TableField("verify_id")
	private String verifyId;
    /**
     * 审核时间
     */
	@TableField("verify_time")
	private Date verifyTime;
    /**
     * 用户uuid
     */
	private String uid;
    /**
     * 信息状态：0-未查看；1-信息已查看；2-审核已通过；3-审核未通过
     */
	@TableField("info_status")
	private Integer infoStatus;
    /**
     * 附件地址表；多个附件使用';'分割
     */
	@TableField("attachment_url")
	private String attachmentUrl;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
    /**
     * 备注信息
     */
	private String remark;

    private String uuid;
    @TableField(exist=false)
    private String username;
	

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getMmid() {
		return mmid;
	}

	public void setMmid(String mmid) {
		this.mmid = mmid;
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

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getVerifyId() {
		return verifyId;
	}

	public void setVerifyId(String verifyId) {
		this.verifyId = verifyId;
	}

	public Date getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(Date verifyTime) {
		this.verifyTime = verifyTime;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Integer getInfoStatus() {
		return infoStatus;
	}

	public void setInfoStatus(Integer infoStatus) {
		this.infoStatus = infoStatus;
	}

	public String getAttachmentUrl() {
		return attachmentUrl;
	}

	public void setAttachmentUrl(String attachmentUrl) {
		this.attachmentUrl = attachmentUrl;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
