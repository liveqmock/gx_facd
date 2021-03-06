package com.wangzhixuan.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wangzhixuan.model.PositionMark;

/**
 * <p>
  * 定位标注 Mapper 接口
 * </p>
 *
 * @author sunbq
 * @since 2017-06-05
 */
public interface PositionMarkMapper extends BaseMapper<PositionMark> {

	List<Map<String, Object>> selectbasePage(Page<Map<String, Object>> page);

	List<PositionMark> selectExcle(Map<String, Object> params);
}