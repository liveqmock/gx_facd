package com.wangzhixuan.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wangzhixuan.model.Answer;

/**
 * <p>
  * 答案表 Mapper 接口
 * </p>
 *
 * @author sunbq
 * @since 2017-08-22
 */
public interface AnswerMapper extends BaseMapper<Answer> {

	List<Answer> selectAnswer(@Param("id")int id);
	
	void delectAnswer(@Param("id")int id);
}