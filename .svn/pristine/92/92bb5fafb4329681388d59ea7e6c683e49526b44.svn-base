package com.wangzhixuan.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wangzhixuan.commons.result.PageInfo;
import com.wangzhixuan.commons.result.Tree;
import com.wangzhixuan.mapper.DailyPatrolMapper;
import com.wangzhixuan.mapper.UserMapper;
import com.wangzhixuan.model.DailyPatrol;
import com.wangzhixuan.model.vo.UserVo;
import com.wangzhixuan.service.IDailyPatrolService;

/**
 * <p>
 * 巡防（考勤表） 服务实现类
 * </p>
 *
 * @author sunbq
 * @since 2017-06-05
 */
@Service
public class DailyPatrolServiceImpl extends ServiceImpl<DailyPatrolMapper, DailyPatrol> implements IDailyPatrolService {
	
	@Autowired
	private DailyPatrolMapper dpMapper;
	@Autowired
	private UserMapper userService;

	@Override
	public void selectDataGrid(PageInfo pageInfo) {
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageInfo.getNowpage(), pageInfo.getSize());
		page.setOrderByField(pageInfo.getSort());
		page.setAsc(pageInfo.getOrder().equalsIgnoreCase("desc"));
		List<Map<String, Object>> list = dpMapper.selectbyPage(page, pageInfo.getCondition());
		pageInfo.setRows(list);
		pageInfo.setTotal(page.getTotal());
	}
	@Override
	public Object selectTree() {
		List<Tree> trees = new ArrayList<Tree>();
		EntityWrapper<DailyPatrol> ew = new EntityWrapper<DailyPatrol>();
		ew.where(" end_time is null ");
		ew.orderBy("start_time",true);
        List<DailyPatrol> rs = this.selectList(ew);
        for (DailyPatrol r : rs) {
            Tree tree = new Tree();
            UserVo u = userService.selectUserVoByUUID(r.getPatrolId());
            tree.setId(r.getId().longValue());
            tree.setText(u.getName()+"的巡防");
            trees.add(tree);
        }
        return trees;
	}
	@Override
	public List<Map<String, Object>> selectFormList(Integer year) {
		return dpMapper.selectrpList(year,"0");
	}
	@Override
	public Map<String, Object> selectForm(Integer year,String uid) {
		return dpMapper.selectrpList(year,uid).size()==0?null:dpMapper.selectrpList(year,uid).get(0);
	}
	@Override
	public DailyPatrol selectByUUid(String uuid) {
		EntityWrapper<DailyPatrol> en = new EntityWrapper<>();
		en.eq("uuid", uuid);
		return this.selectOne(en);
	}
}
