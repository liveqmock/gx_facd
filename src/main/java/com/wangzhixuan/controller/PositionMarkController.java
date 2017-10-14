package com.wangzhixuan.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wangzhixuan.commons.base.BaseController;
import com.wangzhixuan.commons.result.PageInfo;
import com.wangzhixuan.commons.utils.ExportExcel;
import com.wangzhixuan.model.DailyPatrol;
import com.wangzhixuan.model.MapMark;
import com.wangzhixuan.model.PositionMark;
import com.wangzhixuan.model.Road;
import com.wangzhixuan.model.SendFile;
import com.wangzhixuan.model.User;
import com.wangzhixuan.service.IDailyPatrolService;
import com.wangzhixuan.service.IMapMarkService;
import com.wangzhixuan.service.IPositionMarkService;
import com.wangzhixuan.service.IRoadService;
import com.wangzhixuan.service.ISendFileService;
import com.wangzhixuan.service.IUserService;

/**
 * <p>
 * 定位标注 前端控制器
 * </p>
 * 
 * @author sunbq
 * @since 2017-06-12
 */
@Controller
@RequestMapping("/positionMark")
public class PositionMarkController extends BaseController {

	@Autowired
	private IPositionMarkService positionMarkService;
	@Autowired
	private ISendFileService fileservice;
	@Autowired
	private IUserService userservice;
	@Autowired
	private IDailyPatrolService dpService;
	@Autowired
	private IRoadService roadService;
	@Autowired
	private IMapMarkService mapMarkService;

	@GetMapping("/manager")
	public String manager(Model model) {
		return "admin/positionMark/positionMarkList";
	}

	@PostMapping("/dataGrid")
	@ResponseBody
	public PageInfo dataGrid(PositionMark positionMark, Integer page, Integer rows, String sort, String order,
			int stype) {
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		try {
			EntityWrapper<PositionMark> ew = new EntityWrapper<PositionMark>();
			if (positionMark.getName() != null) {
				ew.like("name", positionMark.getName());
			}
			if (positionMark.getType() != null) {
				ew.eq("type", positionMark.getType());
			}
			if (stype == 2) {
				ew.eq("info_status", 2);
			} else {
				ew.ne("info_status", 2);
			}
			ew.orderBy(pageInfo.getSort(), pageInfo.getOrder().equalsIgnoreCase("ASC"));
			Page<PositionMark> pages = getPage(pageInfo);
			pages = positionMarkService.selectPage(pages, ew);

			for (PositionMark pge : pages.getRecords()) {
				User us = userservice.selectById(pge.getUid());
				pge.setUsername(us.getName());
			}
			pageInfo.setRows(pages.getRecords());
			pageInfo.setTotal(pages.getTotal());
			this.OpLog("标记信息列表", 0, getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			this.ErrorLog(e, 0, getUserId());
		}
		return pageInfo;
	}

	@PostMapping("/getBaseInfo")
	@ResponseBody
	public PageInfo getBaseInfo(HttpServletRequest req, Integer page, Integer rows, String sort, String order) {
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		positionMarkService.selectbaseinfo(pageInfo);
		return pageInfo;
	}

	/**
	 * 审核页面
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@GetMapping("/editPage")
	public String editPage(Model model, String uuid) {
		PositionMark positionMark = positionMarkService.selectByUUid(uuid);
		try {
			if (positionMark.getInfoStatus() == 0) {
				positionMark.setInfoStatus(1);
				positionMark.updateById();
			}
			EntityWrapper<SendFile> ew = new EntityWrapper<SendFile>();
			ew.eq("owenid", positionMark.getUuid());
			ew.eq("filesource", 1);
			List<SendFile> file = fileservice.selectfileList(ew);
			model.addAttribute("SendFile", file);
			User u = userservice.selectByUUid(positionMark.getUid());
			model.addAttribute("username", u.getName());
			model.addAttribute("positionMark", positionMark);
			if (positionMark.getType() == 0 && !positionMark.getPid().equals("0")) {
				DailyPatrol dp = dpService.selectByUUid(positionMark.getPid());
				Road road = roadService.selectByUUid(dp.getRouteId());
				model.addAttribute("road", road);
				MapMark mm = mapMarkService.selectByUUid(positionMark.getMmid());
				model.addAttribute("mapMark", mm);
			}
			this.OpLog("进入审核页面", 0, getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			this.ErrorLog(e, 0, getUserId());
		}
		return "admin/positionMark/positionMarkEdit";
	}

	@GetMapping("/see")
	public String see(Model model, String uuid) {
		PositionMark positionMark = positionMarkService.selectByUUid(uuid);
		EntityWrapper<SendFile> ew = new EntityWrapper<SendFile>();
		ew.eq("owenid", uuid);
		ew.eq("filesource", 1);
		List<SendFile> file = fileservice.selectfileList(ew);
		model.addAttribute("SendFile", file);
		User u = userservice.selectByUUid(positionMark.getUid());
		model.addAttribute("username", u.getName());
		model.addAttribute("positionMark", positionMark);
		if (positionMark.getType() == 0 && !positionMark.getPid().equals("0")) {
			DailyPatrol dp = dpService.selectByUUid(positionMark.getPid());
			Road road = roadService.selectByUUid(dp.getRouteId());
			model.addAttribute("road", road);
			MapMark mm = mapMarkService.selectByUUid(positionMark.getMmid());
			model.addAttribute("mapMark", mm);
		}
		return "admin/positionMark/positionMarksee";
	}

	/**
	 * 变更状态
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("/change")
	@ResponseBody
	public Object change(String uuid, Integer infoStatus) {
		User u = userservice.selectById(getUserId());
		EntityWrapper<PositionMark> ew = new EntityWrapper<>();
		ew.eq("uuid", uuid);
		PositionMark sendInfo = new PositionMark();
		sendInfo.setVerifyId(u.getUuid());
		sendInfo.setVerifyTime(new Date());
		sendInfo.setInfoStatus(infoStatus);
		boolean b = positionMarkService.update(sendInfo, ew);
		if (b) {
			this.OpLog("审核标记信息", 0, getUserId());
			return renderSuccess("审核操作成功！");
		} else {
			return renderError("审核操作失败！");
		}
	}

	/**
	 * 替换标记库重的坐标
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("/replacemm")
	@ResponseBody
	public Object replacemm(String uuid) {
		PositionMark pm = positionMarkService.selectByUUid(uuid);
		if (pm.getMmid() == null) {
			return renderError("标记库中无此标记！");
		}
		EntityWrapper<MapMark> ew = new EntityWrapper<>();
		ew.eq("uuid", pm.getMmid());
		MapMark mm = new MapMark();
		mm.setLatitude(pm.getLatitude());
		mm.setLongitude(pm.getLongitude());
		boolean a = mapMarkService.update(mm, ew);
		if (a) {
			this.OpLog("替换界碑信息ID=" + mm.getUuid(), 0, getUserId());
			return renderSuccess("替换操作成功！");
		}
		return renderError("操作失败！");
	}

	@PostMapping("/setcriterion")
	@ResponseBody
	public Object setcriterion(String uuid) {
		try {
			PositionMark pm = positionMarkService.selectByUUid(uuid);
			MapMark mm = new MapMark();
			mm.setInfo(pm.getInfo());
			mm.setLongitude(pm.getLongitude());
			mm.setLatitude(pm.getLatitude());
			mm.setName(pm.getName());
			mm.setType(pm.getType());
			mm.setCreateTime(new Date());
			mm.setUuid(UUID.randomUUID().toString());
			mm.insert();
			pm.setMmid(mm.getUuid());
			pm.updateById();
			this.OpLog("设置标准库ID=" + uuid + ">>>" + mm.getId(), 0, getUserId());
			return renderSuccess("设置成功");
		} catch (Exception e) {
			e.printStackTrace();
			this.ErrorLog(e, 0, getUserId());
			return renderError("系统错误");
		}
	}

	/**
	 * 上报标注导出
	 * 
	 * @param response
	 * @param request
	 * @param stype
	 */
	@RequestMapping("/exportpositionMark")
	@ResponseBody
	public void exportpositionMark(PositionMark positionMark, HttpServletResponse response, HttpServletRequest request,
			int stype) {

		response.setCharacterEncoding("utf-8");

		Map<String, Object> condition = new HashMap<String, Object>();

		if (positionMark.getName() != null) {
			condition.put("name", positionMark.getName());
		}
		if (positionMark.getType() != null) {
			condition.put("type", positionMark.getType());
		}

		condition.put("stype", stype);
		List<PositionMark> list = positionMarkService.selectExcle(condition);

		this.OpLog("标记信息导出", 0, getUserId());

		String[] a = new String[] { "标注名称", "标记人", "类型", "经度", "纬度", "状态", "时间" };
		ExportExcel e = new ExportExcel("标注信息", a, list, response);
		try {
			e.exportPositionMark();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
