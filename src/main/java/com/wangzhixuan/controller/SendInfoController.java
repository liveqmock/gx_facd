package com.wangzhixuan.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.wangzhixuan.commons.base.BaseController;
import com.wangzhixuan.commons.result.PageInfo;
import com.wangzhixuan.commons.utils.ExportExcel;
import com.wangzhixuan.model.SendFile;
import com.wangzhixuan.model.SendInfo;
import com.wangzhixuan.model.SendMessage;
import com.wangzhixuan.model.User;
import com.wangzhixuan.service.ISendFileService;
import com.wangzhixuan.service.ISendInfoService;
import com.wangzhixuan.service.ISendMessageService;
import com.wangzhixuan.service.IUserService;

/**
 * <p>
 * 报送信息 前端控制器
 * </p>
 * 
 * @author sunbq
 * @since 2017-05-23
 */
@Controller
@RequestMapping("/sendInfo")
public class SendInfoController extends BaseController {

	@Autowired
	private ISendInfoService sendInfoService;
	@Autowired
	private IUserService userservice;
	@Autowired
	private ISendFileService fileservice;
	@Autowired
	private ISendMessageService msgservice;

	@GetMapping("/manager")
	public String manager(Model model) {
		return "admin/sendInfo/sendInfoList";
	}

	@RequestMapping("/dataGrid")
	@ResponseBody
	public PageInfo dataGrid(/*
								 * SendInfoVo sendInfo, Integer page, Integer
								 * rows, String sort, String order
								 */
			HttpServletRequest request, HttpServletResponse response, int stype, String export) {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String sorts = request.getParameter("sort");
		String order = request.getParameter("order");

		// 获取传入的参数
		String sendUserName = request.getParameter("sendUserName");
		String sendInfoName = request.getParameter("sendInfoName");
		String recvDateStart = request.getParameter("recvDateStart");
		String recvDateEnd = request.getParameter("recvDateEnd");
		String sendInfo = request.getParameter("sendInfo");
		String sendInfoType = request.getParameter("sendInfoType");
		String urgencyLevel = request.getParameter("urgencyLevel");
		String infoStatus = request.getParameter("infoStatus");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		PageInfo pageInfo = null;
		if (page != null && rows != null && sorts != null && order != null) {
			pageInfo = new PageInfo(Integer.parseInt(page), Integer.parseInt(rows), sorts, order);
		}

		Map<String, Object> condition = new HashMap<String, Object>();

		condition.put("stype", stype);
		// 发送者的姓名
		if (null != sendUserName && !sendUserName.equals("")) {
			condition.put("sendUserName", sendUserName);
		}
		if (null != sendInfoName && !sendInfoName.equals("")) { // 标题
			condition.put("sendInfoName", sendInfoName);
		}
		// 开始时间
		if (null != recvDateStart && !recvDateStart.equals("")) {
			try {
				condition.put("recvDateStart", sdf.parseObject(recvDateStart));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		// 结束时间
		if (null != recvDateEnd && !recvDateEnd.equals("")) {
			try {
				condition.put("recvDateEnd", sdf.parseObject(recvDateEnd));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		// 报送内容
		if (null != sendInfo && !sendInfo.equals("")) {
			condition.put("content", sendInfo);
		}
		// 报送的类型
		if (null != sendInfoType && !sendInfoType.equals("")) {
			condition.put("sendInfoType", Integer.parseInt(sendInfoType));
		}
		// 紧急程度
		if (null != urgencyLevel && !urgencyLevel.equals("")) {
			condition.put("urgencyLevel", Integer.parseInt(urgencyLevel));
		}
		// 公文状态
		if (null != infoStatus && !infoStatus.equals("")) {
			condition.put("infoStatus", Integer.parseInt(infoStatus));
		}

		if (condition != null) {
			pageInfo.setCondition(condition);
		}

		sendInfoService.selectDataGrid(pageInfo);

		return pageInfo;
	}

	/**
	 * 编辑
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@GetMapping("/verify")
	public String verifyPage(Model model, String uuid) {
		try {
			SendInfo SendInfo = sendInfoService.selectByUUid(uuid);
			if (SendInfo.getInfoStatus() == 0) {
				SendInfo.setInfoStatus(1);
				sendInfoService.updateById(SendInfo);
			}
			EntityWrapper<SendFile> ew = new EntityWrapper<SendFile>();
			ew.eq("owenid", SendInfo.getUuid());
			ew.eq("msgid", 0);
			ew.eq("filesource", 2);
			List<SendFile> file = fileservice.selectfileList(ew);
			model.addAttribute("SendFile", file);
			User u = userservice.selectByUUid(SendInfo.getSendId());
			SendInfo.setSendusername(u.getName());
			model.addAttribute("SendInfo", SendInfo);
			EntityWrapper<SendMessage> wrapper = new EntityWrapper<>();
			wrapper.eq("pid", uuid);
			wrapper.eq("type", 1);
			List<SendMessage> mlist = msgservice.selectList(wrapper);
			model.addAttribute("SendMsg", mlist);
			EntityWrapper<SendFile> ew1 = new EntityWrapper<SendFile>();
			ew1.eq("owenid", SendInfo.getUuid());
			ew1.ne("msgid", 0);
			ew1.eq("filesource", 2);
			List<SendFile> file1 = fileservice.selectList(ew1);

			model.addAttribute("SendFile1", file1);

			this.OpLog("进入审核信息页面", 0, getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			this.ErrorLog(e, 0, getUserId());
		}
		return "admin/sendInfo/verify";
	}

	@GetMapping("/see")
	public String see(Model model, String uuid) {
		SendInfo SendInfo = sendInfoService.selectByUUid(uuid);
		EntityWrapper<SendFile> ew = new EntityWrapper<SendFile>();
		ew.eq("owenid", SendInfo.getUuid());
		ew.eq("msgid", 0);
		ew.eq("filesource", 2);
		List<SendFile> file = fileservice.selectfileList(ew);
		model.addAttribute("SendFile", file);
		User u = userservice.selectByUUid(SendInfo.getSendId());
		SendInfo.setSendusername(u.getName());
		model.addAttribute("SendInfo", SendInfo);
		EntityWrapper<SendMessage> wrapper = new EntityWrapper<>();
		wrapper.eq("pid", uuid);
		wrapper.eq("type", 1);
		List<SendMessage> mlist = msgservice.selectList(wrapper);
		model.addAttribute("SendMsg", mlist);

		EntityWrapper<SendFile> ew1 = new EntityWrapper<SendFile>();
		ew1.eq("owenid", SendInfo.getId());
		ew1.ne("msgid", 0);
		ew1.eq("filesource", 2);
		List<SendFile> file1 = fileservice.selectList(ew1);

		model.addAttribute("SendFile1", file1);

		return "admin/sendInfo/see";
	}

	/**
	 * 变更状态
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("/change")
	@ResponseBody
	public Object change(Long id, Integer infoStatus, Integer sendInfoType) {
		User u = userservice.selectById(getUserId());
		SendInfo sendInfo = new SendInfo();
		sendInfo.setId(id);
		sendInfo.setVerifyId(u.getUuid());
		sendInfo.setVerifyTime(new Date());
		sendInfo.setInfoStatus(infoStatus);
		sendInfo.setSendInfoType(sendInfoType);
		boolean b = sendInfoService.updateById(sendInfo);
		if (b) {
			this.OpLog("审核报送信息", 0, getUserId());
			return renderSuccess("审核操作成功！");
		} else {
			return renderError("审核操作失败！");
		}
	}

	/**
	 * 添加页面
	 * 
	 * @return
	 */
	@GetMapping("/addPage")
	public String addPage() {
		return "admin/sendInfo/sendInfoAdd";
	}

	/**
	 * 添加
	 * 
	 * @param
	 * @return
	 */
	@PostMapping("/add")
	@ResponseBody
	public Object add(@Valid SendInfo sendInfo, BindingResult result) {
		if (result.hasErrors()) {
			return renderError(result);
		}
		User u = userservice.selectById(getUserId());
		sendInfo.setReceiveTime(new Date());
		sendInfo.setSendId(u.getUuid());
		sendInfo.setInfoStatus(0);
		sendInfo.setDataStatus(1);
		boolean b = sendInfoService.insert(sendInfo);
		if (b) {
			return renderSuccess("添加成功！");
		} else {
			return renderError("添加失败！");
		}
	}

	/**
	 * 报送信息导出
	 * 
	 * @param response
	 * @param request
	 * @param stype
	 */
	@RequestMapping("/exportSendInFo")
	@ResponseBody
	public void exportSendInFo(HttpServletResponse response, HttpServletRequest request, int stype) {
		response.setCharacterEncoding("utf-8");

		// 获取传入的参数
		String sendUserName = request.getParameter("sendUserName");
		String sendInfoName = request.getParameter("sendInfoName");
		String recvDateStart = request.getParameter("recvDateStart");
		String recvDateEnd = request.getParameter("recvDateEnd");
		String sendInfo = request.getParameter("sendInfo");
		String sendInfoType = request.getParameter("sendInfoType");
		String urgencyLevel = request.getParameter("urgencyLevel");
		String infoStatus = request.getParameter("infoStatus");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Map<String, Object> condition = new HashMap<String, Object>();

		condition.put("stype", stype);
		// 发送者的姓名
		if (null != sendUserName && !sendUserName.equals("")) {
			condition.put("sendUserName", sendUserName);
		}
		if (null != sendInfoName && !sendInfoName.equals("")) { // 标题
			condition.put("sendInfoName", sendInfoName);
		}
		// 开始时间
		if (null != recvDateStart && !recvDateStart.equals("")) {
			try {
				condition.put("recvDateStart", sdf.parseObject(recvDateStart));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		// 结束时间
		if (null != recvDateEnd && !recvDateEnd.equals("")) {
			try {
				condition.put("recvDateEnd", sdf.parseObject(recvDateEnd));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		// 报送内容
		if (null != sendInfo && !sendInfo.equals("")) {
			condition.put("content", sendInfo);
		}
		// 报送的类型
		if (null != sendInfoType && !sendInfoType.equals("")) {
			condition.put("sendInfoType", Integer.parseInt(sendInfoType));
		}
		// 紧急程度
		if (null != urgencyLevel && !urgencyLevel.equals("")) {
			condition.put("urgencyLevel", Integer.parseInt(urgencyLevel));
		}
		// 公文状态
		if (null != infoStatus && !infoStatus.equals("")) {
			condition.put("infoStatus", Integer.parseInt(infoStatus));
		}

		List<SendInfo> list = sendInfoService.selectExcle(condition);

		this.OpLog("报送信息导出", 0, getUserId());

		String[] a = new String[] { "报送人", "报送标题", "报送时间", "报送内容", "报送信息类型", "紧急程度", "报送信息状态" };
		ExportExcel e = new ExportExcel("报送信息", a, list, response);
		try {
			e.exportSendInFo();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
