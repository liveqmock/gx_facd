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
import com.wangzhixuan.model.TestDetail;
import com.wangzhixuan.service.ITestDetailService;

/**
 * <p>
 * 详细考试情况  前端控制器
 * </p>
 * @author sunbq
 * @since 2017-08-22
 */
@Controller
@RequestMapping("/testDetail")
public class TestDetailController extends BaseController {
	
	@Autowired private ITestDetailService testDetailService;
	
	@GetMapping("/manager")
	public String manager() {
		return "admin/testDetail/testDetailList";
	}


	@PostMapping("/dataGrid")
	@ResponseBody
	public PageInfo dataGrid(TestDetail testDetail, Integer page, Integer rows, String sort,String order) {
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		EntityWrapper<TestDetail> ew = new EntityWrapper<TestDetail>(testDetail);
		Page<TestDetail> pages = getPage(pageInfo);
		pages = testDetailService.selectPage(pages, ew);
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
		return "admin/testDetail/testDetailAdd";
	}

	/**
	 * 添加
	 * @param 
	 * @return
	 */
	@PostMapping("/add")
	@ResponseBody
	public Object add(@Valid TestDetail testDetail, BindingResult result) {
		if (result.hasErrors()) {
			return renderError(result);
		}
		boolean b = testDetailService.insert(testDetail);
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
		TestDetail testDetail = new TestDetail();
		boolean b = testDetailService.updateById(testDetail);
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
		TestDetail testDetail = testDetailService.selectById(id);
		model.addAttribute("testDetail", testDetail);
		return "admin/testDetail/testDetailEdit";
	}

	/**
	 * 编辑
	 * @param 
	 * @return
	 */
	@PostMapping("/edit")
	@ResponseBody
	public Object edit(@Valid TestDetail testDetail, BindingResult result) {
		if (result.hasErrors()) {
			return renderError(result);
		}
		boolean b = testDetailService.updateById(testDetail);
		if (b) {
			return renderSuccess("编辑成功！");
		} else {
			return renderError("编辑失败！");
		}
	}
	
}
