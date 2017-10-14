package com.wangzhixuan.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.wangzhixuan.commons.result.PageInfo;
import com.wangzhixuan.model.PositionMark;

/**
 * <p>
 * 定位标注 服务类
 * </p>
 *
 * @author sunbq
 * @since 2017-06-05
 */
public interface IPositionMarkService extends IService<PositionMark> {

	void selectbaseinfo(PageInfo pageInfo);

	List<PositionMark> selectExcle(Map<String, Object> condition );

	PositionMark selectByUUid(String uuid);
}
