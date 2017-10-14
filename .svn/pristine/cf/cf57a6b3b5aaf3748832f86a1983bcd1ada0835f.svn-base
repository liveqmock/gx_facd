package com.wangzhixuan.controller;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
import com.wangzhixuan.model.User;
import com.wangzhixuan.model.VideoFile;
import com.wangzhixuan.service.ISysConfigService;
import com.wangzhixuan.service.IUserService;
import com.wangzhixuan.service.IVideoFileService;

/**
 * <p>
 * 前端控制器
 * </p>
 * 
 * @author sunbq
 * @since 2017-08-07
 */
@Controller
@RequestMapping("/videoFile")
public class VideoFileController extends BaseController {

	@Autowired
	private IVideoFileService videoFileService;

	@Autowired
	private ISysConfigService sysConfigService;

	@Autowired
	private IUserService userService;

	@GetMapping("/manager")
	public String manager() {
		return "admin/videofile/videofileList";
	}

	@PostMapping("/dataGrid")
	@ResponseBody
	public PageInfo dataGrid(HttpServletRequest req, VideoFile videoFile, Integer page, Integer rows, String sort,
			String order) {

		String recvDateStart = req.getParameter("recvDateStart");
		String recvDateEnd = req.getParameter("recvDateEnd");
		String videoName = req.getParameter("videoName");
		String videostatus = req.getParameter("videostatus");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);

		Map<String, Object> condition = new HashMap<String, Object>();

		if (videoName != null && videoName != "") {
			condition.put("userName", videoName);
		}
		if (videostatus != null && videostatus != "") {
			condition.put("videostatus", Integer.parseInt(videostatus));
		}
		// 开始时间
		if (null != recvDateStart && !recvDateStart.equals("")) {
			try {
				condition.put("recvDateStart", sdf.parseObject(recvDateStart));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 结束时间
		if (null != recvDateEnd && !recvDateEnd.equals("")) {
			try {
				condition.put("recvDateEnd", sdf.parseObject(recvDateEnd));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		pageInfo.setCondition(condition);
		videoFileService.selectDataGrid(pageInfo);

		return pageInfo;
	}

	/**
	 * 添加页面
	 * 
	 * @return
	 */
	@GetMapping("/addPage")
	public String addPage() {
		return "admin/videofile/videofileAdd";
	}

	/**
	 * 添加
	 * 
	 * @param
	 * @return
	 */
	@PostMapping("/add")
	@ResponseBody
	public Object add(@Valid VideoFile videoFile, BindingResult result) {
		if (result.hasErrors()) {
			return renderError(result);
		}
		User u = userService.selectById(getUserId());
		videoFile.setUserId(u.getUuid());
		videoFile.setCreateTime(new Date());
		videoFile.setUuid(UUID.randomUUID().toString());
		boolean b = videoFileService.insert(videoFile);
		if (b) {
			return renderSuccess("添加成功！");
		} else {
			return renderError("添加失败！");
		}
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("/delete")
	@ResponseBody
	public Object delete(String uuid) {
		boolean fileResult = true;
		VideoFile v = videoFileService.selectByUUid(uuid);
		File videofile = new File(v.getDiskPath());
		if (videofile.exists()) {
			try {
				if (videofile.isFile()) {
					fileResult = videofile.delete();
				}
			} catch (Exception e) {
				e.printStackTrace();
				return renderError("视频文件删除异常！");
			}
		}
		File imgfile = new File(v.getImgDiskPath());
		if (imgfile.exists()) {
			try {
				if (imgfile.isFile()) {
					fileResult = imgfile.delete();
				}
			} catch (Exception e) {
				e.printStackTrace();
				return renderError("缩略图文件删除异常！");
			}
		}
		if (fileResult) {
			boolean dataResult = videoFileService.deleteById(v.getId());
			if (dataResult) {
				return renderSuccess("数据删除成功！");
			} else {
				return renderError("数据删除失败！");
			}

		} else {
			return renderError("文件删除失败！");
		}
	}

	/**
	 * 编辑
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@GetMapping("/editPage")
	public String editPage(Model model, String uuid) {
		VideoFile videoFile = videoFileService.selectByUUid(uuid);
		model.addAttribute("videoFile", videoFile);
		User u = userService.selectByUUid(videoFile.getUserId());
		model.addAttribute("user", u);
		String video_http_port = sysConfigService.selectConfig("video_http_port");
		String video_ip = sysConfigService.selectConfig("video_ip");
		model.addAttribute("video_http_port", video_http_port);
		model.addAttribute("video_ip", video_ip);
		return "admin/videofile/videofileEdit";
	}

	/**
	 * 编辑
	 * 
	 * @param
	 * @return
	 */
	@PostMapping("/edit")
	@ResponseBody
	public Object edit(@Valid VideoFile videoFile, BindingResult result) {
		if (result.hasErrors()) {
			return renderError(result);
		}

		boolean b = videoFileService.updateById(videoFile);
		if (b) {
			return renderSuccess("编辑成功！");
		} else {
			return renderError("编辑失败！");
		}
	}

	/**
	 * 查看
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@GetMapping("/see")
	public String see(Model model, String uuid) {
		VideoFile videoFile = videoFileService.selectByUUid(uuid);
		model.addAttribute("videoFile", videoFile);
		User u = userService.selectById(videoFile.getUserId());
		model.addAttribute("user", u);
		return "admin/videofile/videofilesee";
	}
}
