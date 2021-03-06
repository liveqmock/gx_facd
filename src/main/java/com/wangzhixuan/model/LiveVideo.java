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
 * 视频直播
 * </p>
 *
 * @author sunbq
 * @since 2017-07-03
 */
@TableName("live_video")
public class LiveVideo extends Model<LiveVideo> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 请求人
     */
	@TableField("live_id")
	private String liveId;
    /**
     * 接受人
     */
	@TableField("receive_id")
	private String receiveId;
    /**
     * 直播地址
     */
	private String url;
    /**
     * 0-直播请求；1-直播中；2-直播关闭；3-直播中断；
     */
	private Integer status;
    /**
     * 时间标记
     */
	@TableField("time_mark")
	private Long timeMark;
    /**
     * 1-手机端发起；2-电视端发起
     */
	private Integer type;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
    /**
     * 视频开始时间
     */
	@TableField("start_time")
	private Date startTime;
    /**
     * 视频结束时间
     */
	@TableField("end_time")
	private Date endTime;

	/**
	 * 巡防id
	 */
	private String patrolid;


	private String uuid;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLiveId() {
		return liveId;
	}

	public void setLiveId(String liveId) {
		this.liveId = liveId;
	}

	public String getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(String receiveId) {
		this.receiveId = receiveId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getTimeMark() {
		return timeMark;
	}

	public void setTimeMark(Long timeMark) {
		this.timeMark = timeMark;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getPatrolid() {
		return patrolid;
	}

	public void setPatrolid(String patrolid) {
		this.patrolid = patrolid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}


}
