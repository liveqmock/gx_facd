package com.wangzhixuan.service.impl;

import com.wangzhixuan.model.ItemFile;
import com.wangzhixuan.mapper.ItemFileMapper;
import com.wangzhixuan.service.IItemFileService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 条目附件表 服务实现类
 * </p>
 *
 * @author sunbq
 * @since 2017-08-22
 */
@Service
public class ItemFileServiceImpl extends ServiceImpl<ItemFileMapper, ItemFile> implements IItemFileService {

	@Autowired
	private ItemFileMapper itemFileMapper;
	
	@Override
	public ItemFile selectItemFile(int id) {
		
		return  itemFileMapper.selectItemFile(id);
	}

	@Override
	public ItemFile selectByOwnerid(int id) {
		return itemFileMapper.selectByOwnerid(id);
	}
	
}
