package com.wangzhixuan.controller;

import java.util.Date;

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
import com.wangzhixuan.model.UseHelp;
import com.wangzhixuan.service.IUseHelpService;

/**
 * <p>
 * 使用帮助  前端控制器
 * </p>
 * @author sunbq
 * @since 2017-07-11
 */
@Controller
@RequestMapping("/useHelp")
public class UseHelpController extends BaseController {
	
	@Autowired private IUseHelpService useHelpService;
	
	
	@GetMapping("/manager")
	public String manager() {
		return "admin/useHelp/useHelpList";
	}


	@PostMapping("/dataGrid")
	@ResponseBody
	public PageInfo dataGrid(UseHelp useHelp, Integer page, Integer rows, String sort,String order) {
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		try{
		EntityWrapper<UseHelp> ew = new EntityWrapper<>();
		if (useHelp.getTitle()!=null&&!useHelp.getTitle().equals("")) {
			ew.like("title", useHelp.getTitle());
		}
		ew.ne("status", 2);
		ew.orderBy(pageInfo.getSort(), pageInfo.getOrder().equalsIgnoreCase("ASC"));
		Page<UseHelp> pages = getPage(pageInfo);
		pages = useHelpService.selectPage(pages, ew);
		pageInfo.setRows(pages.getRecords());
		pageInfo.setTotal(pages.getTotal());
		this.OpLog("获取用户帮助列表", 0, getUserId());
		}catch(Exception e){
			e.printStackTrace();
			this.ErrorLog(e, 0, getUserId());
		}
		return pageInfo;
	}
	
	/**
	 * 添加页面
	 * @return
	 */
	@GetMapping("/addPage")
	public String addPage() {
		return "admin/useHelp/useHelpAdd";
	}

	/**
	 * 添加
	 * @param 
	 * @return
	 */
	@PostMapping("/add")
	@ResponseBody
	public Object add(@Valid UseHelp useHelp, BindingResult result,HttpServletRequest req) {
		if (result.hasErrors()) {
			return renderError(result);
		}
		useHelp.setInfo(req.getParameter("info"));
		useHelp.setCreateTime(new Date());
		useHelp.setStatus(0);
		boolean b = useHelpService.insert(useHelp);
		if (b) {
			this.OpLog("添加用户帮助", 0, getUserId());
			return renderSuccess("添加成功！");
		} else {
			return renderError("添加失败！");
		}
	}

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@PostMapping("/delete")
	@ResponseBody
	public Object delete(Integer id) {
		UseHelp useHelp = new UseHelp();
		useHelp.setId(id);
		useHelp.setStatus(2);
		boolean b = useHelpService.updateById(useHelp);
		if (b) {
			this.OpLog("删除用户帮助", 0, getUserId());
			return renderSuccess("删除成功！");
		} else {
			return renderError("删除失败！");
		}
	}

	/**
	 * 编辑
	 * @param model
	 * @param id
	 * @return
	 */
	@GetMapping("/editPage")
	public String editPage(Model model, Long id) {
		UseHelp useHelp = useHelpService.selectById(id);
		useHelp.setInfo(HtmlUtils.htmlUnescape(useHelp.getInfo()));
		model.addAttribute("useHelp", useHelp);
		return "admin/useHelp/useHelpEdit";
	}

	/**
	 * 编辑
	 * @param 
	 * @return
	 */
	@PostMapping("/edit")
	@ResponseBody
	public Object edit(@Valid UseHelp useHelp, BindingResult result) {
		if (result.hasErrors()) {
			return renderError(result);
		}
		boolean b = useHelpService.updateById(useHelp);
		if (b) {
			this.OpLog("编辑用户帮助", 0, getUserId());
			return renderSuccess("编辑成功！");
		} else {
			return renderError("编辑失败！");
		}
	}
	/**
	 * 查看
	 * @param 
	 * @return
	 */
	@GetMapping("/see")
	public Object see( Long id ,Model model) {
		UseHelp notice = useHelpService.selectById(id);
		notice.setInfo(HtmlUtils.htmlUnescape(notice.getInfo()));
		model.addAttribute("notice", notice);
		this.OpLog("查看用户帮助", 0, getUserId());
		return "admin/notice/noticesee";
	}
	
	/**
	 * 发布
	 * @param id
	 * @return
	 */
	@PostMapping("/check")
	@ResponseBody
	public Object check(Integer id) {
		UseHelp useHelp = useHelpService.selectById(id);
		useHelp.setStatus(1);
		boolean b = useHelpService.updateById(useHelp);
		if (b) {
			this.OpLog("发布用户用户帮助", 0, getUserId());
			return renderSuccess("发布成功！");
		} else {
			return renderError("发布失败！");
		}
	}

}
