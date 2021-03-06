package com.wangzhixuan.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 答案表
 * </p>
 *
 * @author sunbq
 * @since 2017-08-22
 */
public class Answer extends Model<Answer> {

    private static final long serialVersionUID = 1L;

    /**
     * 答案主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 题目id
     */
	@TableField("subject_id")
	private Integer subjectId;
    /**
     * 答案
     */
	private String answerContent;

    /**
     * 是否正确答案：1-正确，0-错误
     */
	private int isAnswer;
    /**
     * 排序
     */
	private Integer seq;
    /**
     * 创建时间
     */
	private Date createTime;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public String getAnswerContent() {
		return answerContent;
	}

	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}

	

	public int getIsAnswer() {
		return isAnswer;
	}

	public void setIsAnswer(int isAnswer) {
		this.isAnswer = isAnswer;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
