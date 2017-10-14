package com.wangzhixuan.mapper;

import com.wangzhixuan.model.Score;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * <p>
  * 成绩报表 Mapper 接口
 * </p>
 *
 * @author sunbq
 * @since 2017-08-22
 */
public interface ScoreMapper extends BaseMapper<Score> {

	List<Map<String, Object>> selectScorePage(Pagination page, Map<String, Object> params);
}