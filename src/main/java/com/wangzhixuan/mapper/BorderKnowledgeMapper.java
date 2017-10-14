package com.wangzhixuan.mapper;

import com.wangzhixuan.model.BorderKnowledge;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * <p>
  * 边防知识表 Mapper 接口
 * </p>
 *
 * @author sunbq
 * @since 2017-08-22
 */
public interface BorderKnowledgeMapper extends BaseMapper<BorderKnowledge> {
	
	List<Map<String, Object>> selectBorderKnowledgePage(Pagination page, Map<String, Object> params);

	public int insertAndGetId(BorderKnowledge BorderKnowledge);  
}