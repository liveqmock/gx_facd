package com.wangzhixuan.service;

import com.wangzhixuan.model.Notice;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 公告消息 服务类
 * </p>
 *
 * @author sunbq
 * @since 2017-06-05
 */
public interface INoticeService extends IService<Notice> {

	Notice selectByUUid(String uuid);
	
}
