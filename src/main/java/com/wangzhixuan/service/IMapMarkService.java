package com.wangzhixuan.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.wangzhixuan.model.MapMark;

/**
 * <p>
 * 地图标记 服务类
 * </p>
 *
 * @author sunbq
 * @since 2017-06-12
 */
public interface IMapMarkService extends IService<MapMark> {
	
	List<MapMark> selectExcle(Map<String, Object> condition );

	MapMark selectByUUid(String uuid);
	
}
