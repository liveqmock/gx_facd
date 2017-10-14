package com.wangzhixuan.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wangzhixuan.mapper.PatrolTrailMapper;
import com.wangzhixuan.model.PatrolTrail;
import com.wangzhixuan.service.IPatrolTrailService;

/**
 * <p>
 * 巡逻轨迹信息 服务实现类
 * </p>
 *
 * @author sunbq
 * @since 2017-06-05
 */
@Service
public class PatrolTrailServiceImpl extends ServiceImpl<PatrolTrailMapper, PatrolTrail> implements IPatrolTrailService {

	@Override
	public PatrolTrail selectnew(String pid) {
		EntityWrapper<PatrolTrail> ew = new EntityWrapper<>();
		ew.eq("pid", pid);
		ew.orderBy("create_time",false);
		ew.limit(0, 1);
		return this.selectOne(ew);
	}
	
}
