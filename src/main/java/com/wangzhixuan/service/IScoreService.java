package com.wangzhixuan.service;

import com.wangzhixuan.commons.result.PageInfo;
import com.wangzhixuan.model.Score;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 成绩报表 服务类
 * </p>
 *
 * @author sunbq
 * @since 2017-08-22
 */
public interface IScoreService extends IService<Score> {
	void selectDataGrid(PageInfo pageInfo);
}
