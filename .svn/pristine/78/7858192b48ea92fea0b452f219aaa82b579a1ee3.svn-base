package com.wangzhixuan.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wangzhixuan.commons.base.BaseController;
import com.wangzhixuan.commons.result.PageInfo;
import com.wangzhixuan.model.Notice;
import com.wangzhixuan.model.NoticeRead;
import com.wangzhixuan.model.User;
import com.wangzhixuan.service.INoticeReadService;
import com.wangzhixuan.service.INoticeService;
import com.wangzhixuan.service.IOrganizationService;
import com.wangzhixuan.service.IUserService;

/**
 * <p>
 * 公告消息 前端控制器
 * </p>
 * 
 * @author sunbq
 * @since 2017-06-14
 */
@Controller
@RequestMapping("/notice")
public class NoticeController extends BaseController {

	@Autowired
	private INoticeService noticeService;
	@Autowired
	private INoticeReadService nrService;
	@Autowired
	private IUserService UserService;
	@Autowired
	private IOrganizationService orgService;

	@GetMapping("/manager")
	public String manager() {
		return "admin/notice/noticeList";
	}

	@PostMapping("/dataGrid")
	@ResponseBody
	public PageInfo dataGrid(Notice notice, Integer page, Integer rows, String sort, String order) {
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		try {
			EntityWrapper<Notice> ew = new EntityWrapper<Notice>();
			if (notice.getTitle() != null && !notice.getTitle().equals("")) {
				ew.like("title", notice.getTitle());
			}
			ew.ne("status", 0);
			ew.orderBy(pageInfo.getSort(), pageInfo.getOrder().equalsIgnoreCase("ASC"));
			Page<Notice> pages = getPage(pageInfo);
			pages = noticeService.selectPage(pages, ew);
			pageInfo.setRows(pages.getRecords());
			pageInfo.setTotal(pages.getTotal());
			this.OpLog("获取公告信息列表", 0, getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			this.ErrorLog(e, 0, getUserId());
		}
		return pageInfo;
	}

	/**
	 * 添加页面
	 * 
	 * @return
	 */
	@GetMapping("/addPage")
	public String addPage() {
		return "admin/notice/noticeAdd";
	}

	/**
	 * 添加
	 * 
	 * @param
	 * @return
	 */
	@PostMapping("/add")
	@ResponseBody
	public Object add(@Valid Notice notice, BindingResult result, HttpServletRequest req) {
		if (result.hasErrors()) {
			return renderError(result);
		}
		User u = UserService.selectById(getUserId());
		notice.setInfo(req.getParameter("info"));
		notice.setCreateTime(new Date());
		notice.setUid(u.getUuid());
		notice.setUuid(UUID.randomUUID().toString());
		boolean b = noticeService.insert(notice);
		if (b) {
			this.OpLog("添加公告", 0, getUserId());
			return renderSuccess("添加成功！");
		} else {
			return renderError("添加失败！");
		}
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("/delete")
	@ResponseBody
	public Object delete(String uuid) {
		EntityWrapper<Notice> ew = new EntityWrapper<>();
		ew.eq("uuid", uuid);
		Notice notice = new Notice();
		notice.setStatus(0);
		boolean b = noticeService.update(notice, ew);
		NoticeRead nr = new NoticeRead();
		nr.setNstatus(0);
		EntityWrapper<NoticeRead> ewnr = new EntityWrapper<>();
		ewnr.eq("nid", notice.getUuid());
		nrService.update(nr, ewnr);
		if (b) {
			this.OpLog("删除公告", 0, getUserId());
			return renderSuccess("删除成功！");
		} else {
			return renderError("删除失败！");
		}
	}

	/**
	 * 发布
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("/check")
	@ResponseBody
	public Object check(String uuid) {
		Notice notice = noticeService.selectByUUid(uuid);
		notice.setStatus(2);
		String val = notice.getReceiver();
		List<String> pid = orgService.selectOrganizationDePid(val);
		for (String o : pid) {
			val += ("," + o);
		}
		EntityWrapper<User> ew = new EntityWrapper<User>();
		ew.setSqlSelect("uuid");
		ew.in("organization_id", val);
		List<Map<String, Object>> us = UserService.selectMaps(ew);
		Date d = new Date();
		for (Map<String, Object> u : us) {
			NoticeRead nr = new NoticeRead();
			nr.setCreateTime(d);
			nr.setNid(notice.getUuid());
			nr.setStatus(0);
			nr.setUid(u.get("uuid").toString());
			nr.insert();
		}
		boolean b = noticeService.updateById(notice);
		if (b) {
			this.OpLog("发布公告", 0, getUserId());
			return renderSuccess("发布成功！");
		} else {
			return renderError("发布失败！");
		}
	}

	/**
	 * 编辑
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@GetMapping("/editPage")
	public String editPage(Model model, String uuid) {
		Notice notice = noticeService.selectByUUid(uuid);
		notice.setInfo(HtmlUtils.htmlUnescape(notice.getInfo()));
		model.addAttribute("notice", notice);
		return "admin/notice/noticeEdit";
	}

	/**
	 * 编辑
	 * 
	 * @param
	 * @return
	 */
	@PostMapping("/edit")
	@ResponseBody
	public Object edit(@Valid Notice notice, BindingResult result) {
		if (result.hasErrors()) {
			return renderError(result);
		}
		boolean b = noticeService.updateById(notice);
		if (b) {
			this.OpLog("修改公告", 0, getUserId());
			return renderSuccess("编辑成功！");
		} else {
			return renderError("编辑失败！");
		}
	}

	/**
	 * 查看
	 * 
	 * @param
	 * @return
	 */
	@GetMapping("/see")
	public Object see(String uuid, Model model) {
		Notice notice = noticeService.selectByUUid(uuid);
		notice.setInfo(HtmlUtils.htmlUnescape(notice.getInfo()));
		model.addAttribute("notice", notice);
		String val = notice.getReceiver();
		List<String> pid = orgService.selectOrganizationDePid(val);
		for (String o : pid) {
			val += ("," + o);
		}
		EntityWrapper<User> ew = new EntityWrapper<User>();
		ew.setSqlSelect("name");
		ew.in("organization_id", val);
		List<Map<String, Object>> us = UserService.selectMaps(ew);
		model.addAttribute("us", us);
		return "admin/notice/noticesee";
	}
}
