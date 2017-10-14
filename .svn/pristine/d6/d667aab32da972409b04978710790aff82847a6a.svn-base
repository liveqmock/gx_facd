package com.wangzhixuan.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 详细考试情况
 * </p>
 *
 * @author sunbq
 * @since 2017-08-22
 */
@TableName("test_detail")
public class TestDetail extends Model<TestDetail> {

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
     * 考试id
     */
	@TableField("examination_id")
	private Integer examinationId;
    /**
     * 试题id
     */
	@TableField("subject_id")
	private Integer subjectId;
    /**
     * 所选答案id
     */
	private Integer choose;


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

	public Integer getExaminationId() {
		return examinationId;
	}

	public void setExaminationId(Integer examinationId) {
		this.examinationId = examinationId;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getChoose() {
		return choose;
	}

	public void setChoose(Integer choose) {
		this.choose = choose;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
