package com.wangzhixuan.service.impl;

import com.wangzhixuan.model.Road;
import com.wangzhixuan.commons.result.Tree;
import com.wangzhixuan.mapper.RoadMapper;
import com.wangzhixuan.service.IRoadService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 路段信息 服务实现类
 * </p>
 *
 * @author sunbq
 * @since 2017-06-05
 */
@Service
public class RoadServiceImpl extends ServiceImpl<RoadMapper, Road> implements IRoadService {

	@Override
	public Object selectTree() {
		List<Tree> trees = new ArrayList<Tree>();
		EntityWrapper<Road> ew = new EntityWrapper<Road>();
		ew.ne("data_status", 2);
		ew.orderBy("create_time",true);
        List<Road> rs = this.selectList(ew);
        for (Road r : rs) {
            Tree tree = new Tree();
            tree.setId(r.getId());
            tree.setText(r.getName());
            trees.add(tree);
        }
        return trees;
	}

	@Override
	public Road selectByUUid(String uuid) {
		EntityWrapper<Road> en = new EntityWrapper<>();
		en.eq("uuid", uuid);
		return this.selectOne(en);
	}
	
}
