package com.wangzhixuan.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangzhixuan.commons.base.BaseController;
import com.wangzhixuan.commons.result.PageInfo;
import com.wangzhixuan.model.Examination;
import com.wangzhixuan.model.Score;
import com.wangzhixuan.model.User;
import com.wangzhixuan.service.IExaminationService;
import com.wangzhixuan.service.IScoreService;

/**
 * <p>
 * 成绩报表  前端控制器
 * </p>
 * @author sunbq
 * @since 2017-08-22
 */
@Controller
@RequestMapping("/score")
public class ScoreController extends BaseController {
	
	@Autowired private IScoreService scoreService;
	@Autowired private IExaminationService examinationService;
	
	@GetMapping("/manager")
	public String manager() {
		return "admin/score/scoreList";
	}


	@PostMapping("/dataGrid")
	@ResponseBody
	public PageInfo dataGrid(Score score, Integer page, Integer rows, String sort,String order,HttpServletRequest req) {
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		
		 Map<String, Object> condition = new HashMap<String, Object>();
		String title = req.getParameter("title");
		 if (null != title && !title.equals("")) {	//标题
	            condition.put("title", title);
	        }
		
         		 
		 pageInfo.setCondition(condition);
	    scoreService.selectDataGrid(pageInfo);
		return pageInfo;
	}
	
	/**
	 * 查看
	 * @param 
	 * @return
	 */
	@RequestMapping("/seeuser")
	public Object seeuser( int id ,Model model) {
		Examination ex = examinationService.selectById(id);
		model.addAttribute("ex", ex);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("exid", ex.getId());
		List<User> us= examinationService.selectExaminationUser(map);
		model.addAttribute("us", us);
		
		return "admin/score/scoreuser";
	}
	/**
	 * 查看
	 * @param 
	 * @return
	 */
	@RequestMapping("/seeinfo")
	public Object seeinfo( int id ,Model model) {
		Examination ex = examinationService.selectById(id);
		model.addAttribute("ex", ex);
		return "admin/score/scoreinfo";
	}
}
