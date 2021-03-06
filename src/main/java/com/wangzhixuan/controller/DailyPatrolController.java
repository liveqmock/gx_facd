package com.wangzhixuan.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wangzhixuan.commons.base.BaseController;
import com.wangzhixuan.commons.result.PageInfo;
import com.wangzhixuan.commons.utils.StringUtils;
import com.wangzhixuan.model.DailyPatrol;
import com.wangzhixuan.model.PatrolTrail;
import com.wangzhixuan.model.RoadTrail;
import com.wangzhixuan.service.IDailyPatrolService;
import com.wangzhixuan.service.IPatrolTrailService;
import com.wangzhixuan.service.IRoadTrailService;

/**
 * <p>
 * 巡防（考勤表） 前端控制器
 * </p>
 * 
 * @author sunbq
 * @since 2017-06-12
 */
@Controller
@RequestMapping("/dailyPatrol")
public class DailyPatrolController extends BaseController {

	@Autowired
	private IDailyPatrolService dailyPatrolService;

	@Autowired
	private IPatrolTrailService ptService;
	
	@Autowired
	private IRoadTrailService rtService;

	@GetMapping("/manager")
	public String manager() {
		return "admin/dailyPatrol/dailyPatrolList";
	}

	@PostMapping("/dataGrid")
	@ResponseBody
	public PageInfo dataGrid(HttpServletRequest req, DailyPatrol dp, Integer page, Integer rows, String sort,
			String order) {
		try {
			PageInfo pageInfo = new PageInfo(page, rows, sort, order);
			Map<String, Object> map = new HashMap<>();
			if (req.getParameter("uname") != null && !req.getParameter("uname").equals("")) {
				map.put("uname", "%"+req.getParameter("uname")+"%");
			}
			if (req.getParameter("rname") != null && !req.getParameter("rname").equals("")) {
				map.put("rname", "%"+req.getParameter("rname")+"%");
			}
			if (dp.getStartTime()!= null && !dp.getStartTime().toString().equals("")) {
				map.put("startTime", dp.getStartTime());
			}
			if (dp.getEndTime() != null && !dp.getEndTime().toString().equals("")) {
				map.put("endTime", dp.getEndTime());
			}
			pageInfo.setCondition(map);
			dailyPatrolService.selectDataGrid(pageInfo);
			this.OpLog("获取巡防列表", 0, getUserId());
			return pageInfo;
		} catch (Exception e) {
			e.printStackTrace();
			this.ErrorLog(e, 0, getUserId());
			return null;
		}
	}
	/**
	 * 地图获取单个巡防路线
	 * @param id
	 * @return
	 */
	@RequestMapping("/trails")
	@ResponseBody
	public Object trails(String uuid){
		EntityWrapper<PatrolTrail> ew = new EntityWrapper<>();
		ew.eq("pid", uuid);
		ew.orderBy("create_time", true);
		List<PatrolTrail> rlist = ptService.selectList(ew);
		return renderSuccess(rlist);
	}
	/**
	 *  设置为标准巡防路线
	 * @param id
	 * @return
	 */
	@RequestMapping("/setbase")
	@ResponseBody
	public Object setbase(String uuid){
		try {
			DailyPatrol dp = dailyPatrolService.selectByUUid(uuid);
			EntityWrapper<PatrolTrail> ew = new EntityWrapper<>();
			ew.eq("pid", uuid);
			ew.orderBy("create_time", true);
			List<PatrolTrail> rlist = ptService.selectList(ew);
			if (rlist.size()<3) {
				return renderError("无数据");
			}
			EntityWrapper<RoadTrail> wrapper = new EntityWrapper<>();
			wrapper.eq("rid", dp.getRouteId());
			rtService.delete(wrapper);
			for (PatrolTrail pt : rlist) {
				RoadTrail rt =new RoadTrail();
				rt.setCreateTime(new Date());
				rt.setLongitude(pt.getLongitude());
				rt.setLatitude(pt.getLatitude());
				rt.setRid(dp.getRouteId());
				rt.insert();
			}
			this.OpLog("设置标准库成功", 0, getUserId());
			return renderSuccess("设置成功！");
		} catch (Exception e) {
			e.printStackTrace();
			this.ErrorLog(e, 0, getUserId());
			return renderError("系统错误");
		}
		
	}
	
	
	@RequestMapping("/dailyPatrolEditEnd")
	@ResponseBody
	public Object dailyPatrolEditEnd(String uuid){
		
			DailyPatrol dp = dailyPatrolService.selectByUUid(uuid);
			Date d = new Date();
			dp.setEndStatus(2);
			dp.setEndTime(new Date());
			int time = StringUtils.getTime(dp.getStartTime(), d);
			dp.setTime(time);
			boolean b = dailyPatrolService.updateById(dp);
			if (b) {
				this.OpLog("强制结束巡防", 0, getUserId());
				return renderSuccess("结束当前巡防成功！");
			} else {
				return renderError("结束当前巡防失败！");
			}
		}
	
	/**
     * 权限树
     *
     * @return
     */
    @PostMapping("/tree")
    @ResponseBody
    public Object tree() {
        return dailyPatrolService.selectTree();
    }
}
