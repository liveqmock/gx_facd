package com.wangzhixuan.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wangzhixuan.commons.base.BaseController;
import com.wangzhixuan.commons.utils.ContentTypeMaps;
import com.wangzhixuan.commons.utils.StringUtils;
import com.wangzhixuan.model.SendFile;
import com.wangzhixuan.service.ISendFileService;
import com.wangzhixuan.service.ISysConfigService;

/**
 * <p>
 * 报送信息附件 前端控制器
 * </p>
 * 
 * @author sunbq
 * @since 2017-05-23
 */
@Controller
@RequestMapping("/sendFile")
public class SendFileController extends BaseController {

	@Autowired
	private ISendFileService sendFileService;

	@Autowired
	private ISysConfigService configService;
	/**
	 * 添加页面
	 * 
	 * @return
	 */
	@GetMapping("/addPage")
	public String editPage(Model model, String uuid) {
		model.addAttribute("owenid", uuid);
		return "admin/sendInfo/sendFileAdd";
	}

	/**
	 * 添加
	 * 
	 * @param
	 * @return
	 */
	@PostMapping("/add")
	@ResponseBody
	public Object add(@RequestParam(value = "graphTheories") MultipartFile[] graphTheories, @Valid SendFile sendFile,
			BindingResult result, HttpServletRequest request) {
		if (result.hasErrors()) {
			return renderError(result);
		}
		try {
			String date = new SimpleDateFormat("yyyy_MM").format(new Date());
			// 存储图片的物理路径
			String pic_path = configService.selectConfig("upload_url")+"/"+date;
//			String url =request.getRequestURL().toString().replace(request.getServletPath(), "/upload/"+date+"/");
			String url =request.getContextPath()+ "/upload/"+date+"/";
			for (MultipartFile multipartFile : graphTheories) {
				// 文件的原始名称
				String originalFilename = multipartFile.getOriginalFilename();
				String newFileName = null;
				if (multipartFile != null && originalFilename != null && originalFilename.length() > 0) {
					newFileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
					// 如果文件夹不存在则创建
					File file = new File(pic_path);
					if (!file.exists()) {
						boolean a =file.mkdirs();
						System.out.println(a);
					}
					/**
					 * 获取新文件的File实例,通过spring的org.springframework.web.multipartInterface
					 * MultipartFile
					 * 下的transferTo方法,这个可以移动文件的文件系统,复制文件系统中的文件或内存内容保存到目标文件.
					 * 如果目标文件已经存在,它将被删除。
					 */
					// 新文件路径实例
					File targetFile = new File(pic_path, newFileName);
					// 内存数据读入磁盘
					multipartFile.transferTo(targetFile);
					String type = ContentTypeMaps.ContentType(targetFile);
					sendFile.setFileurl(url + newFileName);
					sendFile.setFilename(originalFilename);
					sendFile.setFilesuffix(originalFilename.substring(originalFilename.lastIndexOf(".") + 1));
					sendFile.setFiletype(StringUtils.getType(type));
					sendFile.setFilesource(2);// 类型 
					sendFile.setUuid(UUID.randomUUID().toString());
		            sendFileService.insert(sendFile);
				}
			}
			return renderSuccess("添加成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return renderError("添加失败！");
		}
	}

}
