package com.wangzhixuan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wangzhixuan.mapper.SendFileMapper;
import com.wangzhixuan.model.SendFile;
import com.wangzhixuan.service.ISendFileService;

/**
 * <p>
 * 报送信息附件 服务实现类
 * </p>
 *
 * @author sunbq
 * @since 2017-05-23
 */
@Service
public class SendFileServiceImpl extends ServiceImpl<SendFileMapper, SendFile> implements ISendFileService {

	@Autowired
	private SendFileMapper sendfilemap;

	@Override
	public List<SendFile> selectfileList(EntityWrapper<SendFile> ew) {
		return sendfilemap.selectList(ew);
	}

}
