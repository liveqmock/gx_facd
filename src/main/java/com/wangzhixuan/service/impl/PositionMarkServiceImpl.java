package com.wangzhixuan.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wangzhixuan.commons.result.PageInfo;
import com.wangzhixuan.mapper.PositionMarkMapper;
import com.wangzhixuan.model.PositionMark;
import com.wangzhixuan.service.IPositionMarkService;

/**
 * <p>
 * 定位标注 服务实现类
 * </p>
 *
 * @author sunbq
 * @since 2017-06-05
 */
@Service
public class PositionMarkServiceImpl extends ServiceImpl<PositionMarkMapper, PositionMark> implements IPositionMarkService {

	@Autowired private PositionMarkMapper posMapper;
	@Override
	public void selectbaseinfo(PageInfo pageInfo) {
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageInfo.getNowpage(), pageInfo.getSize());
        page.setOrderByField(pageInfo.getSort());
        page.setAsc(pageInfo.getOrder().equalsIgnoreCase("asc"));
        List<Map<String, Object>> list = posMapper.selectbasePage(page);
        pageInfo.setRows(list);
        pageInfo.setTotal(page.getTotal());		
	}
	@Override
	public List<PositionMark> selectExcle(Map<String, Object> condition) {
		List<PositionMark> list =  posMapper.selectExcle(condition);
		return list;
	}
	@Override
	public PositionMark selectByUUid(String uuid) {
		EntityWrapper<PositionMark> en = new EntityWrapper<>();
		en.eq("uuid", uuid);
		return this.selectOne(en);
	}
	
}
