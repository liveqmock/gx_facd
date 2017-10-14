package com.wangzhixuan.controller;

import java.io.File;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wangzhixuan.commons.base.BaseController;
import com.wangzhixuan.commons.result.PageInfo;
import com.wangzhixuan.model.BorderKnowledge;
import com.wangzhixuan.model.ItemFile;
import com.wangzhixuan.model.KnowledgeType;
import com.wangzhixuan.service.IBorderKnowledgeService;
import com.wangzhixuan.service.IItemFileService;
import com.wangzhixuan.service.IKnowledgeTypeService;
import com.wangzhixuan.service.ISysConfigService;

/**
 * <p>
 * 边防知识表  前端控制器
 * </p>
 * @author sunbq
 * @since 2017-08-22
 */
@Controller
@RequestMapping("/borderKnowledge")
public class BorderKnowledgeController extends BaseController {
	
	@Autowired private IBorderKnowledgeService borderKnowledgeService;
	
	@Autowired
	private ISysConfigService configService;
	
	@Autowired private IKnowledgeTypeService knowledgeTypeService;
	
	@Autowired private IItemFileService itemFileService;
	
	
	@GetMapping("/manager")
	public String manager() {
		return "admin/borderKnowledge/borderKnowledgeList";
	}


	@PostMapping("/dataGrid")
	@ResponseBody
	public PageInfo dataGrid( Integer page, Integer rows, String sort,String order,HttpServletRequest req) {
		
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		
		String name = req.getParameter("name");
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		String createUser = req.getParameter("createUser");
		String recvDateStart = req.getParameter("recvDateStart");
		String recvDateEnd = req.getParameter("recvDateEnd");
		
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		
		
        Map<String, Object> condition = new HashMap<String, Object>();

        //类型名称
        if (null != name && !name.equals("")) {
            condition.put("bname", name);
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
        if ( null != content && !content.equals("") ){
        	condition.put("content", content);
        }
        if (null != createUser && !createUser.equals("") ){
        	condition.put("createUser", createUser);
        }
      
        if(condition!=null){
        	pageInfo.setCondition(condition);
        }
        borderKnowledgeService.selectDataGrid(pageInfo);
        
		return pageInfo;
	}
	
	/**
	 * 添加页面
	 * @return
	 */
	@GetMapping("/addPage")
	public String addPage() {
		return "admin/borderKnowledge/borderKnowledgeAdd";
	}

	/**
	 * 添加
	 * @param 
	 * @return
	 */
	@PostMapping("/add")
	@ResponseBody
	public Object add(@Valid BorderKnowledge borderKnowledge,KnowledgeType knowledgeType,@RequestParam("file") MultipartFile multipartFile, BindingResult result,HttpServletRequest req) {
		if (result.hasErrors()) {
			return renderError(result);
		}
		String originalFilename = multipartFile.getOriginalFilename();
		ItemFile itemFile = new ItemFile();
		if (multipartFile != null && multipartFile.getOriginalFilename() != null && multipartFile.getOriginalFilename().length() > 0) {
			String newFileName = new Date().getTime()+originalFilename.substring(originalFilename.lastIndexOf("."));
			// 存储图片的物理路径
			String date = new SimpleDateFormat("yyyy_MM").format(new Date());
			String pic_path = configService.selectConfig("upload_url")+"/"+date;
//			String url =req.getRequestURL().toString().replace(req.getServletPath(), "/upload/"+date+"/");
			String url =req.getContextPath()+ "/upload/"+date+"/";
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
				itemFile.setFileurl(url +newFileName);
				itemFile.setFilename(newFileName);
				String f = newFileName.substring(newFileName.lastIndexOf(".")+1);
				itemFile.setFilesuffix(f);
			} catch (IOException e) {
				e.printStackTrace();
				return renderError("系统异常");
			}
		}
		long i = this.getUserId();
		int j = (int) i;
		borderKnowledge.setCreateUser(j);
		boolean b = borderKnowledgeService.insertThree(borderKnowledge, knowledgeType, itemFile, req);
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
		BorderKnowledge borderKnowledge =borderKnowledgeService.selectById(id);
		borderKnowledge.setStatus(0);
		KnowledgeType k =  knowledgeTypeService.selectById(borderKnowledge.getTypeId());
		k.setStatus(0);
		boolean a = knowledgeTypeService.updateById(k);
		boolean b = borderKnowledgeService.updateById(borderKnowledge);
		if (b&&a) {
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
		BorderKnowledge borderKnowledge = borderKnowledgeService.selectById(id);
		EntityWrapper<KnowledgeType> ew = new EntityWrapper<KnowledgeType>();
		ew.eq("status",0 );
		List<KnowledgeType> KnowledgeType1 =	knowledgeTypeService.selectList(ew);
		KnowledgeType knowledgeType2= knowledgeTypeService.selectById(borderKnowledge.getTypeId());
		model.addAttribute("b", borderKnowledge);
		model.addAttribute("KnowledgeType1", KnowledgeType1);
		model.addAttribute("k2", knowledgeType2);
		return "admin/borderKnowledge/borderKnowledgeEdit";
	}

	/**
	 * 编辑
	 * @param 
	 * @return
	 */
	@PostMapping("/edit")
	@ResponseBody
	public Object edit(@Valid BorderKnowledge borderKnowledge,@RequestParam("file") MultipartFile multipartFile,KnowledgeType knowledgeType, BindingResult result,int kid,HttpServletRequest req) {
		if (result.hasErrors()) {
			return renderError(result);
		}
		String originalFilename = multipartFile.getOriginalFilename();
		ItemFile itemFile = itemFileService.selectItemFile(borderKnowledge.getId());
		if (multipartFile != null && multipartFile.getOriginalFilename() != null && multipartFile.getOriginalFilename().length() > 0) {
			String newFileName = new Date().getTime()+originalFilename.substring(originalFilename.lastIndexOf("."));
			// 存储图片的物理路径
			String date = new SimpleDateFormat("yyyy_MM").format(new Date());
			String pic_path = configService.selectConfig("upload_url")+"/"+date;
//			String url =req.getRequestURL().toString().replace(req.getServletPath(), "/upload/"+date+"/");
			String url =req.getContextPath()+ "/upload/"+date+"/";
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
				itemFile.setFileurl(url +newFileName);
				itemFile.setFilename(newFileName);
				String f = newFileName.substring(newFileName.lastIndexOf(".")+1);
				itemFile.setFilesuffix(f);
			} catch (IOException e) {
				e.printStackTrace();
				return renderError("系统异常");
			}
		}
		
		boolean b = borderKnowledgeService.updateThree(borderKnowledge, knowledgeType, kid, itemFile);
		
		
		if (b ) {
			return renderSuccess("编辑成功！");
		} else {
			return renderError("编辑失败！");
		}
	}
	
	/**
	 * 发布边防知识
	 * @param borderKnowledge
	 * @param knowledgeType
	 * @param result
	 * @return
	 */
	@PostMapping("/check")
	@ResponseBody
	public Object check(@Valid BorderKnowledge borderKnowledge,KnowledgeType knowledgeType, BindingResult result,int id) {
		if (result.hasErrors()) {
			return renderError(result);
		}
		BorderKnowledge bk = borderKnowledgeService.selectById(borderKnowledge.getId());
		bk.setStatus(2);
		boolean b = borderKnowledgeService.updateById(bk);
//		
//		KnowledgeType kType = knowledgeTypeService.selectById(bk.getTypeId());
//		kType.setStatus(2);
//		boolean a = knowledgeTypeService.updateById(kType);
		if (b  ) {
			return renderSuccess("发布成功！");
		} else {
			return renderError("发布失败！");
		}
	}
	
	@GetMapping("/see")
	public Object see( Long id ,Model model) {
		BorderKnowledge borderKnowledge = borderKnowledgeService.selectById(id);
		borderKnowledge.setContent(HtmlUtils.htmlUnescape(borderKnowledge.getContent()));
		KnowledgeType knowledgeType = knowledgeTypeService.selectById(borderKnowledge.getTypeId());
		model.addAttribute("b", borderKnowledge);
		model.addAttribute("k", knowledgeType);
	    return "admin/borderKnowledge/borderKnowledgeSee";
	}
	
    @RequestMapping("type")
    @ResponseBody
    public Object getType(){
    	return knowledgeTypeService.selectList();
    }
    
    @RequestMapping("tree")
    @ResponseBody
    public Object getTree(){
    	EntityWrapper<KnowledgeType> ew = new EntityWrapper<KnowledgeType>();
    	ew.eq("status",0 );
    	List<KnowledgeType> KnowledgeType =	knowledgeTypeService.selectList(ew);
    		for (KnowledgeType knowledgeType2 : KnowledgeType) {
    			knowledgeType2.setText(knowledgeType2.getName());
			}
    		
    		return KnowledgeType;
    	// knowledgeTypeService.selectTree();
    }
}
