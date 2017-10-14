package com.wangzhixuan.service.impl;

import com.wangzhixuan.model.SendMessage;
import com.wangzhixuan.mapper.SendMessageMapper;
import com.wangzhixuan.service.ISendMessageService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 报送信息消息 服务实现类
 * </p>
 *
 * @author sunbq
 * @since 2017-06-05
 */
@Service
public class SendMessageServiceImpl extends ServiceImpl<SendMessageMapper, SendMessage> implements ISendMessageService {
	
}
