package com.wangzhixuan.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author sunbq
 * @since 2017-08-07
 */
@TableName("video_file")
public class VideoFile extends Model<VideoFile> {

    private static final long serialVersionUID = 1L;

    /**
     * 文件名称
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	private String fileName;
    /**
     * 文件路径
     */
	private String fileUrl;
    /**
     * 创建时间
     */
	private Date createTime;
    /**
     * 直播人
     */
	private String userId;
    /**
     * 备注
     */
	private String remarks;
	
	/**
	 * 视频分类
	 * @return
	 */
	@TableField("video_type")
     private Integer videotype;
	/**
	 * 视频截图
	 */
	@TableField("video_img")
	private String videoimg;
	
	/**
	 * 视频文件存放路径
	 */
	private String diskPath;
	
	/**
	 * 缩略图物理路径
	 */
	private String imgDiskPath;

	private String uuid;

	public String getImgDiskPath() {
		return imgDiskPath;
	}





	public void setImgDiskPath(String imgDiskPath) {
		this.imgDiskPath = imgDiskPath;
	}





	public Integer getId() {
		return id;
	}

	
	


	public Integer getVideotype() {
		return videotype;
	}





	public void setVideotype(Integer videotype) {
		this.videotype = videotype;
	}





	public String getDiskPath() {
		return diskPath;
	}





	public void setDiskPath(String diskPath) {
		this.diskPath = diskPath;
	}





	public String getVideoimg() {
		return videoimg;
	}


	public void setVideoimg(String videoimg) {
		this.videoimg = videoimg;
	}


	public void setId(Integer id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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


	@Override
	public String toString() {
		return "VideoFile [id=" + id + ", fileName=" + fileName + ", fileUrl=" + fileUrl + ", createTime=" + createTime
				+ ", userId=" + userId + ", remarks=" + remarks + ", videotype=" + videotype + ", videoimg=" + videoimg
				+ ", diskPath=" + diskPath + ", imgDiskPath=" + imgDiskPath + "]";
	}
    
	
}
