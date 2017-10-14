package com.wangzhixuan.service.impl;

import com.wangzhixuan.model.Notice;
import com.wangzhixuan.mapper.NoticeMapper;
import com.wangzhixuan.service.INoticeService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 公告消息 服务实现类
 * </p>
 *
 * @author sunbq
 * @since 2017-06-05
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

	@Override
	public Notice selectByUUid(String uuid) {
		EntityWrapper<Notice> en = new EntityWrapper<>();
		en.eq("uuid", uuid);
		return this.selectOne(en);
	}
	
}
