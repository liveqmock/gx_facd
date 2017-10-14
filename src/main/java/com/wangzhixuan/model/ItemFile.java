package com.wangzhixuan.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 条目附件表
 * </p>
 *
 * @author sunbq
 * @since 2017-08-22
 */
@TableName("item_file")
public class ItemFile extends Model<ItemFile> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 文件名称
     */
	private String filename;
    /**
     * 文件后缀
     */
	private String filesuffix;
    /**
     * 文件路径
     */
	private String fileurl;
    /**
     * 类型:1-边防知识，2-时事信息，3-试题
     */
	private int type;
    /**
     * 所属条目
     */
	private Integer ownerid;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilesuffix() {
		return filesuffix;
	}

	public void setFilesuffix(String filesuffix) {
		this.filesuffix = filesuffix;
	}

	public String getFileurl() {
		return fileurl;
	}

	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}

	

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Integer getOwnerid() {
		return ownerid;
	}

	public void setOwnerid(Integer ownerid) {
		this.ownerid = ownerid;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
