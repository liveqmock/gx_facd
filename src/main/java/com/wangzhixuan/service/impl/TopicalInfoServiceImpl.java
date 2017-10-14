package com.wangzhixuan.service.impl;

import com.wangzhixuan.model.TopicalInfo;
import com.wangzhixuan.commons.result.PageInfo;
import com.wangzhixuan.mapper.TopicalInfoMapper;
import com.wangzhixuan.service.ITopicalInfoService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicalInfoServiceImpl extends ServiceImpl<TopicalInfoMapper, TopicalInfo> implements ITopicalInfoService {
	
	@Autowired
	private TopicalInfoMapper topicalInfoMapper;

	@Override
	public void selectDataGrid(PageInfo pageInfo) {
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageInfo.getNowpage(), pageInfo.getSize());
		page.setOrderByField(pageInfo.getSort());
		page.setAsc(pageInfo.getOrder().equalsIgnoreCase("asc"));
        List<Map<String, Object>> list = topicalInfoMapper.selectvoidFilePage(page, pageInfo.getCondition());
        pageInfo.setRows(list);
        pageInfo.setTotal(page.getTotal());
	}

	@Override
	public List<Map<String, Object>> selectByStatus() {
		return topicalInfoMapper.selectByStatus();
	}

	@Override
	public int insertAndGetId(TopicalInfo topicalInfo) {
		 topicalInfoMapper.insertAndGetId(topicalInfo);
		 return topicalInfo.getId();
	}
	
}
