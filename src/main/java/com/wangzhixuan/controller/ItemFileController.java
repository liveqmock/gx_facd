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
import com.wangzhixuan.model.ItemFile;
import com.wangzhixuan.service.IItemFileService;

/**
 * <p>
 * 条目附件表  前端控制器
 * </p>
 * @author sunbq
 * @since 2017-08-22
 */
@Controller
@RequestMapping("/itemFile")
public class ItemFileController extends BaseController {
	
	@Autowired private IItemFileService itemFileService;
	
	@GetMapping("/manager")
	public String manager() {
		return "admin/itemFile/itemFileList";
	}


	@PostMapping("/dataGrid")
	@ResponseBody
	public PageInfo dataGrid(ItemFile itemFile, Integer page, Integer rows, String sort,String order) {
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		EntityWrapper<ItemFile> ew = new EntityWrapper<ItemFile>(itemFile);
		Page<ItemFile> pages = getPage(pageInfo);
		pages = itemFileService.selectPage(pages, ew);
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
		return "admin/itemFile/itemFileAdd";
	}

	/**
	 * 添加
	 * @param 
	 * @return
	 */
	@PostMapping("/add")
	@ResponseBody
	public Object add(@Valid ItemFile itemFile, BindingResult result) {
		if (result.hasErrors()) {
			return renderError(result);
		}
		boolean b = itemFileService.insert(itemFile);
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
		ItemFile itemFile = new ItemFile();
		boolean b = itemFileService.updateById(itemFile);
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
		ItemFile itemFile = itemFileService.selectById(id);
		model.addAttribute("itemFile", itemFile);
		return "admin/itemFile/itemFileEdit";
	}

	/**
	 * 编辑
	 * @param 
	 * @return
	 */
	@PostMapping("/edit")
	@ResponseBody
	public Object edit(@Valid ItemFile itemFile, BindingResult result) {
		if (result.hasErrors()) {
			return renderError(result);
		}
		boolean b = itemFileService.updateById(itemFile);
		if (b) {
			return renderSuccess("编辑成功！");
		} else {
			return renderError("编辑失败！");
		}
	}
	
}
