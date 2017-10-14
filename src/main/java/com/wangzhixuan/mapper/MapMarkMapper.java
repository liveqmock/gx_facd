package com.wangzhixuan.mapper;

import com.wangzhixuan.model.MapMark;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  * 地图标记 Mapper 接口
 * </p>
 *
 * @author sunbq
 * @since 2017-06-12
 */
public interface MapMarkMapper extends BaseMapper<MapMark> {
 
	List<MapMark> selectExcle(Map<String, Object> condition );
}