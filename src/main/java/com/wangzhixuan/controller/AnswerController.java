package com.wangzhixuan.controller;

import java.util.Date;

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
import com.wangzhixuan.model.Answer;
import com.wangzhixuan.service.IAnswerService;

/**
 * <p>
 * 答案表  前端控制器
 * </p>
 * @author sunbq
 * @since 2017-08-22
 */
@Controller
@RequestMapping("/answer")
public class AnswerController extends BaseController {
	
	@Autowired private IAnswerService answerService;
	
	@GetMapping("/manager")
	public String manager() {
		return "admin/answer/answerList";
	}


	@PostMapping("/dataGrid")
	@ResponseBody
	public PageInfo dataGrid(Answer answer, Integer page, Integer rows, String sort,String order) {
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		EntityWrapper<Answer> ew = new EntityWrapper<Answer>(answer);
		Page<Answer> pages = getPage(pageInfo);
		pages = answerService.selectPage(pages, ew);
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
		return "admin/answer/answerAdd";
	}

	/**
	 * 添加
	 * @param 
	 * @return
	 */
	@PostMapping("/add")
	@ResponseBody
	public Object add(@Valid Answer answer, BindingResult result) {
		if (result.hasErrors()) {
			return renderError(result);
		}
		answer.setCreateTime(new Date());
		boolean b = answerService.insert(answer);
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
		Answer answer = new Answer();
		boolean b = answerService.updateById(answer);
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
		Answer answer = answerService.selectById(id);
		model.addAttribute("answer", answer);
		return "admin/answer/answerEdit";
	}

	/**
	 * 编辑
	 * @param 
	 * @return
	 */
	@PostMapping("/edit")
	@ResponseBody
	public Object edit(@Valid Answer answer, BindingResult result) {
		if (result.hasErrors()) {
			return renderError(result);
		}
		boolean b = answerService.updateById(answer);
		if (b) {
			return renderSuccess("编辑成功！");
		} else {
			return renderError("编辑失败！");
		}
	}
	
}
