package com.wangzhixuan.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.wangzhixuan.model.Subject;

/**
 * <p>
  * 题库表 Mapper 接口
 * </p>
 *
 * @author sunbq
 * @since 2017-08-22
 */
public interface SubjectMapper extends BaseMapper<Subject> {
	
	List<Map<String, Object>> selectSubjectPage(Pagination page, Map<String, Object> params);

	public int insertAndGetId(Subject Subject); 

    List<Subject> selectByRandom(Map<String, Object> params);
   
    List<Subject> selectByRandom2(Map<String, Object> params);

    List<Subject> selectPage(Map<String, Object> params);
}