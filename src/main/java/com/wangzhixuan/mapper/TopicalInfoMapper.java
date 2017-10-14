package com.wangzhixuan.mapper;

import com.wangzhixuan.model.TopicalInfo;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * <p>
  * 实事信息表 Mapper 接口
 * </p>
 *
 * @author sunbq
 * @since 2017-08-22
 */
public interface TopicalInfoMapper extends BaseMapper<TopicalInfo> {
	List<Map<String, Object>> selectvoidFilePage(Pagination page, Map<String, Object> params);
	List<Map<String, Object>> selectByStatus();
	int insertAndGetId(TopicalInfo topicalInfo);
}