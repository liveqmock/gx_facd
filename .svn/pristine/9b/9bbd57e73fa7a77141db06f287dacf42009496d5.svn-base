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
import com.wangzhixuan.model.FeedBack;
import com.wangzhixuan.service.IFeedBackService;

/**
 * <p>
 * 用户建议表  前端控制器
 * </p>
 * @author sunbq
 * @since 2017-07-11
 */
@Controller
@RequestMapping("/feedBack")
public class FeedBackController extends BaseController {
	
	@Autowired private IFeedBackService feedBackService;
	
	@GetMapping("/manager")
	public String manager() {
		return "admin/feedBack/feedBackList";
	}


	@PostMapping("/dataGrid")
	@ResponseBody
	public PageInfo dataGrid(FeedBack feedBack, Integer page, Integer rows, String sort,String order) {
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		EntityWrapper<FeedBack> ew = new EntityWrapper<FeedBack>(feedBack);
		Page<FeedBack> pages = getPage(pageInfo);
		pages = feedBackService.selectPage(pages, ew);
		pageInfo.setRows(pages.getRecords());
		pageInfo.setTotal(pages.getTotal());
		this.OpLog("查询用户反馈", 0, this.getUserId());
		return pageInfo;
	}
	
	/**
	 * 添加页面
	 * @return
	 */
	@GetMapping("/addPage")
	public String addPage(Model model,int id) {
		FeedBack feedBack= feedBackService.selectById(id);
		model.addAttribute("feedBack", feedBack);
		return "admin/feedBack/feedBackAdd";
	}

	/**
	 * 添加
	 * @param 
	 * @return
	 */
	@PostMapping("/add")
	@ResponseBody
	public Object add(@Valid FeedBack feedBack, BindingResult result) {
		if (result.hasErrors()) {
			return renderError(result);
		}
		if(feedBack.getAnswer()!=null){
			EntityWrapper<FeedBack> wrapper = new EntityWrapper<>();
			boolean b = feedBackService.update(feedBack, wrapper);
			if(b){
				this.OpLog("修改用户反馈", 0, this.getUserId());
				return renderSuccess("修改成功！");
			}else{
				return renderError("修改失败！");
			}
			
		}
		feedBack.setUid(this.getUserId());
		boolean b = feedBackService.insert(feedBack);
		if (b) {
			this.OpLog("添加用户反馈", 0, this.getUserId());
			return renderSuccess("添加成功！");
		} else {
			return renderError("添加失败！");
		}
	}

	
	
}
