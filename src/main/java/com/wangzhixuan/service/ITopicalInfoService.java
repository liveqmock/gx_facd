package com.wangzhixuan.service;

import com.wangzhixuan.commons.result.PageInfo;
import com.wangzhixuan.model.TopicalInfo;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 实事信息表 服务类
 * </p>
 *
 * @author sunbq
 * @since 2017-08-22
 */
public interface ITopicalInfoService extends IService<TopicalInfo> {
	void selectDataGrid(PageInfo pageInfo);
	List<Map<String, Object>> selectByStatus();
	int insertAndGetId(TopicalInfo topicalInfo);
}
