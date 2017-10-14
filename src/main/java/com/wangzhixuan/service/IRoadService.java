package com.wangzhixuan.service;

import com.wangzhixuan.model.Road;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 路段信息 服务类
 * </p>
 *
 * @author sunbq
 * @since 2017-06-05
 */
public interface IRoadService extends IService<Road> {
	
	Object selectTree();

	Road selectByUUid(String uuid);
}
