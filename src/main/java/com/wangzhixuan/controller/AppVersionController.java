package com.wangzhixuan.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wangzhixuan.commons.base.BaseController;
import com.wangzhixuan.commons.result.PageInfo;
import com.wangzhixuan.model.AppVersion;
import com.wangzhixuan.service.IAppVersionService;
import com.wangzhixuan.service.ISysConfigService;

/**
 * <p>
 * 产品版本管理  前端控制器
 * </p>
 * @author sunbq
 * @since 2017-06-27
 */
@Controller
@RequestMapping("/appVersion")
public class AppVersionController extends BaseController {
	
	@Autowired private IAppVersionService appVersionService;

	@Autowired
	private ISysConfigService configService;
	@GetMapping("/manager")
	public String manager() {
		return "admin/appVersion/appVersionList";
	}


	@PostMapping("/dataGrid")
	@ResponseBody
	public PageInfo dataGrid(AppVersion appVersion, Integer page, Integer rows, String sort,String order) {
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		EntityWrapper<AppVersion> ew = new EntityWrapper<AppVersion>();
		if (appVersion.getName()!=null&&!appVersion.getName().equals("")) {
			ew.like("name", appVersion.getName());
		}
		ew.ne("status", 4);
		ew.orderBy(pageInfo.getSort(), pageInfo.getOrder().equalsIgnoreCase("DESC"));
		Page<AppVersion> pages = getPage(pageInfo);
		pages = appVersionService.selectPage(pages, ew);
		pageInfo.setRows(pages.getRecords());
		pageInfo.setTotal(pages.getTotal());
		this.OpLog("软件版本列表", 0, getUserId());
		return pageInfo;
	}
	
	/**
	 * 添加页面
	 * @return
	 */
	@GetMapping("/addPage")
	public String addPage() {
		return "admin/appVersion/appVersionAdd";
	}

	/**
	 * 添加
	 * @param 
	 * @return
	 */
	@PostMapping("/add")
	@ResponseBody
	public Object add(@Valid AppVersion appVersion,@RequestParam("file") MultipartFile multipartFile, BindingResult result,HttpServletRequest req) {
		if (result.hasErrors()) {
			return renderError(result);
		}
		String date = new SimpleDateFormat("yyyy").format(new Date());
		// 存储图片的物理路径
		String pic_path = configService.selectConfig("upload_url")+"/app"+date;
//		String url =req.getRequestURL().toString().replace(req.getServletPath(), "/upload/app"+date+"/");
		String url =req.getContextPath()+ "/upload/app"+date+"/";
		if (multipartFile != null && multipartFile.getOriginalFilename() != null && multipartFile.getOriginalFilename().length() > 0) {
			String newFileName = "facd-platform_"+new Date().getTime()+".apk";
			// 如果文件夹不存在则创建
			File file = new File(pic_path);
			if (!file.exists()) {
				file.mkdirs();
			}
			// 新文件路径实例
			File targetFile = new File(pic_path, newFileName);
			// 内存数据读入磁盘
			try {
				multipartFile.transferTo(targetFile);
				appVersion.setUrl(url +newFileName);
			} catch (IOException e) {
				e.printStackTrace();
				return renderError("系统异常");
			}
		}
		appVersion.setCreateTime(new Date());
		appVersion.setStatus(0);
		boolean b = appVersionService.insert(appVersion);
		if (b) {
			this.OpLog("添加软件成功", 0, getUserId());
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
	public Object delete(Integer id) {
		AppVersion appVersion = new AppVersion();
		appVersion.setId(id);
		appVersion.setStatus(4);
		boolean b = appVersionService.updateById(appVersion);
		if (b) {

			this.OpLog("删除软件版本成功id="+id, 0, getUserId());
			return renderSuccess("删除成功！");
		} else {
			return renderError("删除失败！");
		}
	}
	@PostMapping("/stop")
	@ResponseBody
	public Object stop(Integer id) {
		AppVersion appVersion = new AppVersion();
		appVersion.setId(id);
		appVersion.setStatus(2);
		boolean b = appVersionService.updateById(appVersion);
		if (b) {
			this.OpLog("停用软件版本成功id="+id, 0, getUserId());
			return renderSuccess("操作成功！");
		} else {
			return renderError("操作失败！");
		}
	}

	@PostMapping("/start")
	@ResponseBody
	public Object start(Integer id) {
		AppVersion appVersion = appVersionService.selectById(id);
		EntityWrapper<AppVersion> ew = new EntityWrapper<>();
		ew.eq("status", 1);
		ew.eq("type", appVersion.getType());
		AppVersion App = new AppVersion();
		App.setStatus(2);
		appVersionService.update(App, ew);
		appVersion.setStatus(1);
		boolean b = appVersionService.updateById(appVersion);
		if (b) {
			this.OpLog("启用软件版本成功id="+id, 0, getUserId());
			return renderSuccess("操作成功！");
		} else {
			return renderError("操作失败！");
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
		AppVersion appVersion = appVersionService.selectById(id);
		model.addAttribute("appVersion", appVersion);
		return "admin/appVersion/appVersionEdit";
	}

	/**
	 * 编辑
	 * @param 
	 * @return
	 */
	@PostMapping("/edit")
	@ResponseBody
	public Object edit(@Valid AppVersion appVersion,@RequestParam("file") MultipartFile multipartFile, BindingResult result,HttpServletRequest req) {
		if (result.hasErrors()) {
			return renderError(result);
		}
		String date = new SimpleDateFormat("yyyy").format(new Date());
		// 存储图片的物理路径
		String pic_path = configService.selectConfig("upload_url")+"/app"+date;
//		String url =req.getRequestURL().toString().replace(req.getServletPath(), "/upload/app"+date+"/");
		String url =req.getContextPath()+ "/upload/app"+date+"/";
		if (multipartFile != null && multipartFile.getOriginalFilename() != null && multipartFile.getOriginalFilename().length() > 0) {
			String newFileName = "facd-platform_"+new Date().getTime()+".apk";
			// 如果文件夹不存在则创建
			File file = new File(pic_path);
			if (!file.exists()) {
				file.mkdirs();
			}
			// 新文件路径实例
			File targetFile = new File(pic_path, newFileName);
			// 内存数据读入磁盘
			try {
				multipartFile.transferTo(targetFile);
				appVersion.setUrl(url+newFileName);
			} catch (IOException e) {
				e.printStackTrace();
				return renderError("系统异常");
			}
		}
		boolean b = appVersionService.updateById(appVersion);
		if (b) {
			this.OpLog("修改软件版本成功id="+appVersion.getId(), 0, getUserId());
			return renderSuccess("编辑成功！");
		} else {
			return renderError("编辑失败！");
		}
	}
	
}
