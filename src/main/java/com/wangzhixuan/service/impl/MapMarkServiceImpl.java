package com.wangzhixuan.service.impl;

import com.wangzhixuan.model.MapMark;
import com.wangzhixuan.mapper.MapMarkMapper;
import com.wangzhixuan.service.IMapMarkService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 地图标记 服务实现类
 * </p>
 *
 * @author sunbq
 * @since 2017-06-12
 */
@Service
public class MapMarkServiceImpl extends ServiceImpl<MapMarkMapper, MapMark> implements IMapMarkService {

	@Autowired private MapMarkMapper mapMarkMapper;
	
	@Override
	public List<MapMark> selectExcle(Map<String, Object> condition) {
		List<MapMark> list = mapMarkMapper.selectExcle(condition);
		return list;
	}

	@Override
	public MapMark selectByUUid(String uuid) {
		EntityWrapper<MapMark> en = new EntityWrapper<>();
		en.eq("uuid", uuid);
		return this.selectOne(en);
	}
	
}
