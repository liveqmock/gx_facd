package com.wangzhixuan.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wangzhixuan.commons.base.BaseController;
import com.wangzhixuan.commons.result.PageInfo;
import com.wangzhixuan.model.SysConfig;
import com.wangzhixuan.service.ISysConfigService;

/**
 * <p>
 * 配置信息表  前端控制器
 * </p>
 * @author sunbq
 * @since 2017-07-07
 */
@Controller
@RequestMapping("/sysConfig")
public class SysConfigController extends BaseController {
	
	@Autowired private ISysConfigService sysConfigService;
	
	@GetMapping("/manager")
	public String manager() {
		return "admin/sysConfig/sysConfigList";
	}


	@PostMapping("/dataGrid")
	@ResponseBody
	public PageInfo dataGrid(SysConfig sysConfig, Integer page, Integer rows, String sort,String order) {
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		EntityWrapper<SysConfig> ew = new EntityWrapper<SysConfig>();
		if(sysConfig.getInfo()!=null && !sysConfig.getInfo().equals("")) {
		      ew.like("info", sysConfig.getInfo());
		}
		ew.orderBy(pageInfo.getSort(), pageInfo.getOrder().equalsIgnoreCase("ASC"));
		Page<SysConfig> pages = getPage(pageInfo);
		pages = sysConfigService.selectPage(pages, ew);
		pageInfo.setRows(pages.getRecords());
		pageInfo.setTotal(pages.getTotal());
		return pageInfo;
	}
	
	/**
	 * 添加页面
	 * @return
	 */
	@GetMapping("/addPage")
	public String addPage() {
		return "admin/sysConfig/sysConfigAdd";
	}

	/**
	 * 添加
	 * @param 
	 * @return
	 */
	@PostMapping("/add")
	@ResponseBody
	public Object add(@Valid SysConfig sysConfig, BindingResult result) {
		if (result.hasErrors()) {
			return renderError(result);
		}
		boolean b = sysConfigService.insert(sysConfig);
		if (b) {
			return renderSuccess("添加成功！");
		} else {
			return renderError("添加失败！");
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
		SysConfig sysConfig = sysConfigService.selectById(id);
		model.addAttribute("sysConfig", sysConfig);
		return "admin/sysConfig/sysConfigEdit";
	}

	/**
	 * 编辑
	 * @param 
	 * @return
	 */
	@PostMapping("/edit")
	@ResponseBody
	public Object edit(@Valid SysConfig sysConfig, BindingResult result) {
		if (result.hasErrors()) {
			return renderError(result);
		}
		boolean b = sysConfigService.updateById(sysConfig);
		if (b) {
			return renderSuccess("编辑成功！");
		} else {
			return renderError("编辑失败！");
		}
	}
	
}
