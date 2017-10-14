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
import com.wangzhixuan.model.TestPaper;
import com.wangzhixuan.service.ITestPaperService;

/**
 * <p>
 * 试卷信息  前端控制器
 * </p>
 * @author sunbq
 * @since 2017-08-22
 */
@Controller
@RequestMapping("/testPaper")
public class TestPaperController extends BaseController {
	
	@Autowired private ITestPaperService testPaperService;
	
	@GetMapping("/manager")
	public String manager() {
		return "admin/testPaper/testPaperList";
	}


	@PostMapping("/dataGrid")
	@ResponseBody
	public PageInfo dataGrid(TestPaper testPaper, Integer page, Integer rows, String sort,String order) {
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		EntityWrapper<TestPaper> ew = new EntityWrapper<TestPaper>(testPaper);
		Page<TestPaper> pages = getPage(pageInfo);
		pages = testPaperService.selectPage(pages, ew);
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
		return "admin/testPaper/testPaperAdd";
	}

	/**
	 * 添加
	 * @param 
	 * @return
	 */
	@PostMapping("/add")
	@ResponseBody
	public Object add(@Valid TestPaper testPaper, BindingResult result) {
		if (result.hasErrors()) {
			return renderError(result);
		}
		boolean b = testPaperService.insert(testPaper);
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
		TestPaper testPaper = new TestPaper();
		boolean b = testPaperService.updateById(testPaper);
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
		TestPaper testPaper = testPaperService.selectById(id);
		model.addAttribute("testPaper", testPaper);
		return "admin/testPaper/testPaperEdit";
	}

	/**
	 * 编辑
	 * @param 
	 * @return
	 */
	@PostMapping("/edit")
	@ResponseBody
	public Object edit(@Valid TestPaper testPaper, BindingResult result) {
		if (result.hasErrors()) {
			return renderError(result);
		}
		boolean b = testPaperService.updateById(testPaper);
		if (b) {
			return renderSuccess("编辑成功！");
		} else {
			return renderError("编辑失败！");
		}
	}
	
}
