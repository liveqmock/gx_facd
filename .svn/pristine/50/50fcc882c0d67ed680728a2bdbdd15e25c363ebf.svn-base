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
 * 巡防（考勤表）
 * </p>
 *
 * @author sunbq
 * @since 2017-06-06
 */
@TableName("daily_patrol")
public class DailyPatrol extends Model<DailyPatrol> {

    private static final long serialVersionUID = 1L;

    /**
     * 表主键ID
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 巡防人员 id
     */
	@TableField("patrol_id")
	private String patrolId;
    /**
     * 巡防路线Id
     */
	@TableField("route_id")
	private String routeId;
    /**
     * 巡防开始时间
     */
	@TableField("start_time")
	private Date startTime;
    /**
     * 巡防结束时间
     */
	@TableField("end_time")
	private Date endTime;
    /**
     * 报送信息数量
     */
	private Integer reportcount;
    /**
     * 标记信息数量
     */
	private Integer tagcount;
    /**
     * 全程用时（分钟）
     */
	private Integer time;
    /**
     * 已经巡防的距离
     */
	private Double patrolDistance;
	/**
	 * 结束状态 0-开始；1-结束；2-异常
	 */
	@TableField("end_status")
	private int endStatus;

	private String uuid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPatrolId() {
		return patrolId;
	}

	public void setPatrolId(String patrolId) {
		this.patrolId = patrolId;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
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

	public Integer getReportcount() {
		return reportcount;
	}

	public void setReportcount(Integer reportcount) {
		this.reportcount = reportcount;
	}

	public Integer getTagcount() {
		return tagcount;
	}

	public void setTagcount(Integer tagcount) {
		this.tagcount = tagcount;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public Double getPatrolDistance() {
		return patrolDistance;
	}

	public void setPatrolDistance(Double patrolDistance) {
		this.patrolDistance = patrolDistance;
	}

	public int getEndStatus() {
		return endStatus;
	}

	public void setEndStatus(int endStatus) {
		this.endStatus = endStatus;
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
