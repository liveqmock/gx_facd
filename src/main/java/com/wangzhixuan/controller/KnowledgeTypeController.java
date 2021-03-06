package com.wangzhixuan.controller;

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
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wangzhixuan.commons.base.BaseController;
import com.wangzhixuan.commons.result.PageInfo;
import com.wangzhixuan.model.KnowledgeType;
import com.wangzhixuan.service.IKnowledgeTypeService;

/**
 * <p>
 * 边防知识类别表  前端控制器
 * </p>
 * @author sunbq
 * @since 2017-09-01
 */
@Controller
@RequestMapping("/knowledgeType")
public class KnowledgeTypeController extends BaseController {
	
	@Autowired private IKnowledgeTypeService knowledgeTypeService;
	
	@GetMapping("/manager")
	public String manager() {
		return "admin/knowledgeType/knowledgeTypeList";
	}


	@PostMapping("/dataGrid")
	@ResponseBody
	public PageInfo dataGrid(KnowledgeType knowledgeType, Integer page, Integer rows, String sort,String order) {
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		try {
		EntityWrapper<KnowledgeType> ew = new EntityWrapper<KnowledgeType>();
		if (knowledgeType.getName()!=null) {
			ew.like("name", knowledgeType.getName());
		}
		ew.orderBy(pageInfo.getSort(), pageInfo.getOrder().equalsIgnoreCase("ASC"));
		Page<KnowledgeType> pages = getPage(pageInfo);
		pages = knowledgeTypeService.selectPage(pages, ew);
		pageInfo.setRows(pages.getRecords());
		pageInfo.setTotal(pages.getTotal());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return pageInfo;
	}
	
	/**
	 * 添加页面
	 * @return
	 */
	@GetMapping("/addPage")
	public String addPage() {
		return "admin/knowledgeType/knowledgeTypeAdd";
	}

	/**
	 * 添加
	 * @param 
	 * @return
	 */
	@PostMapping("/add")
	@ResponseBody
	public Object add(@Valid KnowledgeType knowledgeType, BindingResult result,HttpServletRequest req) {
		if (result.hasErrors()) {
			return renderError(result);
		}
		knowledgeType.setName(req.getParameter("type"));
		knowledgeType.setStatus(1);
		boolean b = knowledgeTypeService.insert(knowledgeType);
		if (b) {
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
	public Object delete(int id) {
		KnowledgeType knowledgeType = new KnowledgeType();
		knowledgeType.setId(id);
		boolean b = knowledgeTypeService.deleteById(knowledgeType);
		if (b) {
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
		KnowledgeType knowledgeType = knowledgeTypeService.selectById(id);
		model.addAttribute("knowledgeType", knowledgeType);
		return "admin/knowledgeType/knowledgeTypeEdit";
	}

	/**
	 * 编辑
	 * @param 
	 * @return
	 */
	@PostMapping("/edit")
	@ResponseBody
	public Object edit(@Valid KnowledgeType knowledgeType, BindingResult result) {
		if (result.hasErrors()) {
			return renderError(result);
		}
		knowledgeType.setId(knowledgeType.getId());
		knowledgeType.setName(knowledgeType.getName());
		boolean b = knowledgeTypeService.updateById(knowledgeType);
		if (b) {
			return renderSuccess("编辑成功！");
		} else {
			return renderError("编辑失败！");
		}
	}
	
}

