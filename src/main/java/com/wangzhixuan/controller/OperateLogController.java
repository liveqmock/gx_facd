package com.wangzhixuan.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangzhixuan.commons.base.BaseController;
import com.wangzhixuan.commons.result.PageInfo;
import com.wangzhixuan.service.IOperateLogService;

/**
 * <p>
 * 操作日志表  前端控制器
 * </p>
 * @author sunbq
 * @since 2017-07-06
 */
@Controller
@RequestMapping("/OperateLog")
public class OperateLogController extends BaseController {
	
	@Autowired private IOperateLogService logService;
	
	@GetMapping("/manager")
	public String manager() {
		return "admin/OperateLog";
	}

	@PostMapping("/dataGrid")
    @ResponseBody
    public PageInfo dataGrid(Integer page, Integer rows,HttpServletRequest request, 
            @RequestParam(value = "sort", defaultValue = "createtime") String sort, 
            @RequestParam(value = "order", defaultValue = "DESC") String order) {
		
		
		//获取传入的参数
		String userName = request.getParameter("userName");
		String terrace = request.getParameter("terrace");
		String msg = request.getParameter("msg");
		String recvDateStart = request.getParameter("recvDateStart");
		String recvDateEnd = request.getParameter("recvDateEnd");
		
		
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
        Map<String, Object> condition = new HashMap<String, Object>();

        //操作人的姓名，平台和内容
        if (null != userName && !userName.equals("")) {
            condition.put("userName", userName);
        }
        if (null != terrace && !terrace.equals("")) {	//标题
            condition.put("terrace", terrace);
        }
        if (null != msg && !msg.equals("")) {	//标题
        	condition.put("msg", msg);
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
       
        pageInfo.setCondition(condition);
        logService.selectDataGrid(pageInfo);
        
        
        return pageInfo;
    }
	
	
	
}
