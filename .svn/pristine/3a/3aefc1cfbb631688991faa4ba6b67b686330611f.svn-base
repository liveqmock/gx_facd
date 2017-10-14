package com.wangzhixuan.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.wangzhixuan.model.Examination;
import com.wangzhixuan.model.User;

/**
 * <p>
  * 考试信息 Mapper 接口
 * </p>
 *
 * @author sunbq
 * @since 2017-08-22
 */
public interface ExaminationMapper extends BaseMapper<Examination> {

	List<Map<String, Object>> selectExaminationPage(Pagination page, Map<String, Object> params);
	public int insertAndGetId(Examination examination);
	
	List<User> selectExaminationUser(Map<String,Object> map);
}