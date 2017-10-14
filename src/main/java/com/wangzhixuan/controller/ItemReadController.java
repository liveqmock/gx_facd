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
import com.wangzhixuan.model.ItemRead;
import com.wangzhixuan.service.IItemReadService;

/**
 * <p>
 * 阅读记录表  前端控制器
 * </p>
 * @author sunbq
 * @since 2017-08-22
 */
@Controller
@RequestMapping("/itemRead")
public class ItemReadController extends BaseController {
	
	@Autowired private IItemReadService itemReadService;
	
	@GetMapping("/manager")
	public String manager() {
		return "admin/itemRead/itemReadList";
	}


	@PostMapping("/dataGrid")
	@ResponseBody
	public PageInfo dataGrid(ItemRead itemRead, Integer page, Integer rows, String sort,String order) {
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		EntityWrapper<ItemRead> ew = new EntityWrapper<ItemRead>(itemRead);
		Page<ItemRead> pages = getPage(pageInfo);
		pages = itemReadService.selectPage(pages, ew);
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
		return "admin/itemRead/itemReadAdd";
	}

	/**
	 * 添加
	 * @param 
	 * @return
	 */
	@PostMapping("/add")
	@ResponseBody
	public Object add(@Valid ItemRead itemRead, BindingResult result) {
		if (result.hasErrors()) {
			return renderError(result);
		}
		boolean b = itemReadService.insert(itemRead);
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
	public Object delete(Long id) {
		ItemRead itemRead = new ItemRead();
		boolean b = itemReadService.updateById(itemRead);
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
		ItemRead itemRead = itemReadService.selectById(id);
		model.addAttribute("itemRead", itemRead);
		return "admin/itemRead/itemReadEdit";
	}

	/**
	 * 编辑
	 * @param 
	 * @return
	 */
	@PostMapping("/edit")
	@ResponseBody
	public Object edit(@Valid ItemRead itemRead, BindingResult result) {
		if (result.hasErrors()) {
			return renderError(result);
		}
		boolean b = itemReadService.updateById(itemRead);
		if (b) {
			return renderSuccess("编辑成功！");
		} else {
			return renderError("编辑失败！");
		}
	}
	
}
