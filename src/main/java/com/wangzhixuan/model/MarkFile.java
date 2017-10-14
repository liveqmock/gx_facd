package com.wangzhixuan.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 地图标记文件表
 * </p>
 *
 * @author sunbq
 * @since 2017-06-13
 */
@TableName("mark_file")
public class MarkFile extends Model<MarkFile> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 地图标记id
     */
	private Long mid;
    /**
     * 文件名
     */
	private String filename;
    /**
     * 文件地址
     */
	private String fieurl;
    /**
     * 文件类型：1-图片；2-语音；3-视频；
     */
	private Integer filetype;
    /**
     * 文件后缀
     */
	private String filesuffix;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFieurl() {
		return fieurl;
	}

	public void setFieurl(String fieurl) {
		this.fieurl = fieurl;
	}

	public Integer getFiletype() {
		return filetype;
	}

	public void setFiletype(Integer filetype) {
		this.filetype = filetype;
	}

	public String getFilesuffix() {
		return filesuffix;
	}

	public void setFilesuffix(String filesuffix) {
		this.filesuffix = filesuffix;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
