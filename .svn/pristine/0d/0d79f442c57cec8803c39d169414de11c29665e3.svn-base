package com.wangzhixuan.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.wangzhixuan.commons.base.BaseController;
import com.wangzhixuan.commons.result.PageInfo;
import com.wangzhixuan.model.Answer;
import com.wangzhixuan.model.Subject;
import com.wangzhixuan.service.IAnswerService;
import com.wangzhixuan.service.ISubjectService;

/**
 * <p>
 * 题库表  前端控制器
 * </p>
 * @author sunbq
 * @since 2017-08-22
 */
@Controller
@RequestMapping("/subject")
public class SubjectController extends BaseController {
	
	@Autowired private ISubjectService subjectService;
	
	@Autowired private IAnswerService answerService;
	
	@GetMapping("/manager")
	public String manager() {
		return "admin/subject/subjectList";
	}


	@PostMapping("/dataGrid")
	@ResponseBody
	public PageInfo dataGrid( Integer page, Integer rows, String sort,String order,HttpServletRequest req) {
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);

		String level = req.getParameter("level");
		String title = req.getParameter("title");
		String createUser = req.getParameter("createUser");
		String recvDateStart = req.getParameter("recvDateStart");
		String recvDateEnd = req.getParameter("recvDateEnd");
		
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		
		
        Map<String, Object> condition = new HashMap<String, Object>();

        //发送者的姓名
        if (null != level && !level.equals("")) {
            condition.put("level", Integer.parseInt(level));
        }
        if (null != title && !title.equals("")) {	//标题
            condition.put("title", title);
        }
        //开始时间
        if (null != recvDateStart && !recvDateStart.equals("")) {
            try {
				condition.put("recvDateStart", sdf.parseObject(recvDateStart));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        //结束时间
        if (null != recvDateEnd && !recvDateEnd.equals("")) {
            try {
				condition.put("recvDateEnd", sdf.parseObject(recvDateEnd));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
      
        if (null != createUser && !createUser.equals("") ){
        	condition.put("createUser", createUser);
        }
      
        pageInfo.setCondition(condition);
        subjectService.selectDataGrid(pageInfo);
		return pageInfo;
	}
	
	/**
	 * 添加页面
	 * @return
	 */
	@GetMapping("/addPage")
	public String addPage() {
		return "admin/subject/subjectAdd";
	}

	/**
	 * 添加
	 * @param 
	 * @return
	 */
	@PostMapping("/add")
	@ResponseBody
	public Object add(@Valid Subject subject, Answer answer1 ,BindingResult result,HttpServletRequest req) {
		if (result.hasErrors()) {
			return renderError(result);
		}
		long i =this.getUserId();
		int j  =(int)i;
		subject.setCreateTime(new Date());
		subject.setCreateUser(j);
		subject.setStatus(1);
		boolean b = subjectService.insertAnswer(subject, answer1,req);
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
		answerService.deleteAnswer(id);
		boolean b = subjectService.deleteById(id);
		
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
	public String editPage(Model model, int id) {
		Subject subject = subjectService.selectById(id);
		List<Answer> answer = answerService.selectAnswer(id);
		model.addAttribute("subject", subject);
		model.addAttribute("Answer", answer);
		return "admin/subject/subjectEdit";
	}

	/**
	 * 编辑
	 * @param 
	 * @return
	 */
	@PostMapping("/edit")
	@ResponseBody
	public Object edit(@Valid Subject subject, BindingResult result,HttpServletRequest req) {
		if (result.hasErrors()) {
			return renderError(result);
		}
		boolean b = subjectService.updataAnswer(subject,req);
		if (b) {
			return renderSuccess("编辑成功！");
		} else {
			return renderError("编辑失败！");
		}
	}
	
}
