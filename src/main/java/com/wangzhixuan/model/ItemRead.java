package com.wangzhixuan.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 阅读记录表
 * </p>
 *
 * @author sunbq
 * @since 2017-08-22
 */
@TableName("item_read")
public class ItemRead extends Model<ItemRead> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 用户id
     */
	private Integer userId;
    /**
     * 条目id
     */
	@TableField("item_id")
	private Integer itemId;
    /**
     * 条目类型:1-边防知识，2-时事信息，3-试题
     */
	private int type;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}
