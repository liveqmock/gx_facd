package com.wangzhixuan.service;

import com.wangzhixuan.model.KnowledgeType;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 边防知识类别表 服务类
 * </p>
 *
 * @author sunbq
 * @since 2017-09-01
 */
public interface IKnowledgeTypeService extends IService<KnowledgeType> {

	Object selectTree();
	List<KnowledgeType> selectList();
	
}
