package com.wangzhixuan.service.impl;

import com.wangzhixuan.model.KnowledgeType;
import com.wangzhixuan.commons.result.Tree;
import com.wangzhixuan.mapper.KnowledgeTypeMapper;
import com.wangzhixuan.service.IKnowledgeTypeService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 边防知识类别表 服务实现类
 * </p>
 *
 * @author sunbq
 * @since 2017-09-01
 */
@Service
public class KnowledgeTypeServiceImpl extends ServiceImpl<KnowledgeTypeMapper, KnowledgeType> implements IKnowledgeTypeService {

	
	@Autowired
	KnowledgeTypeMapper knowledgeTypeMapper;
	@Override
	public Object selectTree() {
		List<Tree> trees = new ArrayList<Tree>();
		EntityWrapper<KnowledgeType> ew = new EntityWrapper<KnowledgeType>();
		//ew.ne("data_status", 2);
		//ew.orderBy("create_time",true);
        List<KnowledgeType> kt = this.selectList(ew);
        for (KnowledgeType k : kt) {
            Tree tree = new Tree();
            tree.setId((long)k.getId());
            tree.setText(k.getName());
            trees.add(tree);
        }
        return trees;
	}

	@Override
	public List<KnowledgeType> selectList() {
		EntityWrapper<KnowledgeType> ew = new EntityWrapper<KnowledgeType>();
		return knowledgeTypeMapper.selectList(ew);
	}
}
