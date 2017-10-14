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
import com.wangzhixuan.model.DataType;
import com.wangzhixuan.service.IDataTypeService;

/**
 * <p>
 *   前端控制器
 * </p>
 * @author sunbq
 * @since 2017-09-12
 */
@Controller
@RequestMapping("/dataType")
public class DataTypeController extends BaseController {
	
	@Autowired private IDataTypeService dataTypeService;
	
	@GetMapping("/manager")
	public String manager() {
		return "admin/dataType/dataTypeList";
	}


	@PostMapping("/dataGrid")
	@ResponseBody
	public PageInfo dataGrid(DataType dataType, Integer page, Integer rows, String sort,String order) {
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		EntityWrapper<DataType> ew = new EntityWrapper<DataType>(dataType);
		Page<DataType> pages = getPage(pageInfo);
		pages = dataTypeService.selectPage(pages, ew);
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
		return "admin/dataType/dataTypeAdd";
	}

	/**
	 * 添加
	 * @param 
	 * @return
	 */
	@PostMapping("/add")
	@ResponseBody
	public Object add(@Valid DataType dataType, BindingResult result) {
		if (result.hasErrors()) {
			return renderError(result);
		}
//		dataType.setCreateTime(new Date());
//		dataType.setUpdateTime(new Date());
//		dataType.setDeleteFlag(0);
		boolean b = dataTypeService.insert(dataType);
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
		DataType dataType = new DataType();
//		dataType.setId(id);
//		dataType.setUpdateTime(new Date());
//		dataType.setDeleteFlag(1);
		boolean b = dataTypeService.updateById(dataType);
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
		DataType dataType = dataTypeService.selectById(id);
		model.addAttribute("dataType", dataType);
		return "admin/dataType/dataTypeEdit";
	}

	/**
	 * 编辑
	 * @param 
	 * @return
	 */
	@PostMapping("/edit")
	@ResponseBody
	public Object edit(@Valid DataType dataType, BindingResult result) {
		if (result.hasErrors()) {
			return renderError(result);
		}
//		dataType.setUpdateTime(new Date());
		boolean b = dataTypeService.updateById(dataType);
		if (b) {
			return renderSuccess("编辑成功！");
		} else {
			return renderError("编辑失败！");
		}
	}
	
}
