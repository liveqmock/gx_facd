package com.wangzhixuan.controller.phone;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wangzhixuan.commons.base.BaseController;
import com.wangzhixuan.commons.result.PageInfo;
import com.wangzhixuan.model.BorderKnowledge;
import com.wangzhixuan.model.ItemFile;
import com.wangzhixuan.model.ItemRead;
import com.wangzhixuan.model.KnowledgeType;
import com.wangzhixuan.model.TopicalInfo;
import com.wangzhixuan.model.User;
import com.wangzhixuan.service.IBorderKnowledgeService;
import com.wangzhixuan.service.IItemFileService;
import com.wangzhixuan.service.IItemReadService;
import com.wangzhixuan.service.IKnowledgeTypeService;
import com.wangzhixuan.service.ITopicalInfoService;
import com.wangzhixuan.service.IUserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/phoneTopicalInfo")
public class PhoneTopicalInfoController extends BaseController{

	@Autowired
	private IUserService userService;
	@Autowired
	private IItemReadService itemReadService;
	@Autowired
	private ITopicalInfoService topicalInfoService;
	@Autowired
	private IKnowledgeTypeService knowledgeService;
	@Autowired
	private IBorderKnowledgeService borderKnowledgeService;
	@Autowired
	private IItemFileService itemFileService;
	@Autowired
	private IKnowledgeTypeService ktService;
	/** 
	 * 手机时事信息列表分页查询
	 * @return
	 */
	@RequestMapping("getPageInfo")
	@ResponseBody
	public Object selectByLimit(HttpServletRequest req,Integer pageLimit,Integer pageIndex) {
		EntityWrapper<TopicalInfo> ew = new EntityWrapper<TopicalInfo>();
		PageInfo pageInfo = new PageInfo(pageIndex,pageLimit,"createTime", "desc");
		Page<TopicalInfo> pages=new Page<TopicalInfo>(pageInfo.getNowpage(), pageInfo.getSize());
		Page<TopicalInfo> selectPage = topicalInfoService.selectPage(pages,ew );
		JSONArray arr = new JSONArray();
		for(TopicalInfo list:selectPage.getRecords()){
			JSONObject json=new JSONObject();
			EntityWrapper<ItemFile> es = new EntityWrapper<ItemFile>();
			es.setSqlSelect("fileurl");
			es.eq("id",list.getId());
			Map<String, Object> map = itemFileService.selectMap(es);
			//String newString="gxbhf.sirbox.cn"+map.get("fileurl").toString();
			json.put("id",list.getId());
			json.put("title",list.getTitle());
			json.put("content",list.getContent());
			json.put("createUser",list.getCreateUser());
			json.put("createTime",list.getCreateTime());
			json.put("status",list.getStatus());
			json.put("fileurl",map.get("fileurl").toString());
			arr.add(json);
		}
		return renderSuccess(arr);
		
	
	}
	/**
	 * 手机时事信息列表查询接口
	 * @return
	 */
	@RequestMapping("getReleased")
	@ResponseBody
	public Object selectByStatus() {
		JSONArray array=new JSONArray();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JSONObject jo=null;
		try {
		List<Map<String, Object>> list=topicalInfoService.selectByStatus();
		for(int i=0;i<list.size();i++) {
			jo=new JSONObject();
			jo.put("id", list.get(i).get("id"));
			jo.put("title", list.get(i).get("title"));
			jo.put("content", list.get(i).get("content"));
			jo.put("createUser", list.get(i).get("createUser"));
			jo.put("fileurl", list.get(i).get("fileurl"));
			jo.put("createTime", sdf.format(list.get(i).get("createTime")));
			array.add(jo);
		}
		return renderSuccess(array);
		}catch(Exception e) {
			e.printStackTrace();
			return renderError("系统异常！");
		}
	}
	/**
	 * 手机获取时事信息详情页接口
	 * @param id
	 * @return
	 */
	@RequestMapping("getDetail")
	@ResponseBody
	public Object selectById(Integer id) {
		JSONArray array=new JSONArray();
		TopicalInfo info = topicalInfoService.selectById(id);
		array.add(info);
		return renderSuccess(array);
	}
	
	/**
	 * 统计阅读数接口
	 */
	@RequestMapping("count")
	@ResponseBody
	public Object count(int userid, int item_id, int type, String token) {
		User user = userService.selectBytoken(token);
		try {
			if(user==null) {
				return renderError("用户登录已过期！");
			}
			EntityWrapper<ItemRead> ew = new EntityWrapper<ItemRead>();
			ew.eq("userid", userid);
			ew.eq("item_id", item_id);
			ew.eq("type", type);
			ItemRead itemRead = itemReadService.selectOne(ew);
			if(itemRead==null) {
				ItemRead ir=new ItemRead();
				ir.setUserId(userid);
				ir.setItemId(item_id);
				ir.setType(type);
				Boolean b = itemReadService.insert(ir);
			}
			return renderSuccess();
		}catch(Exception e) {
			e.printStackTrace();
			return renderError("系统异常！");
		}
	}
	

	/* 知识库展示
	 */
	@RequestMapping("/showInfo")
	@ResponseBody
	public Object listInfo(String token,HttpServletRequest req){
		//User user = usrService.selectBytoken(token);
		//if (user == null) {
		//	return renderError("用户登录已过期!");
		//}
		EntityWrapper<KnowledgeType> ew = new EntityWrapper<KnowledgeType>(); 
				ew.setSqlSelect("id,name");
				List<Map<String, Object>> n = knowledgeService.selectMaps(ew);
				JSONArray fromObject = JSONArray.fromObject(n);
				return renderSuccess(fromObject);
	}
	/*
	 * 知识库目录展示
	 */
	@RequestMapping("/showListInfo")
	@ResponseBody
	public Object showListInfo(String token,HttpServletRequest req,Integer pageLimit,Integer pageIndex,Integer id){
		EntityWrapper<BorderKnowledge> ew = new EntityWrapper<BorderKnowledge>();
		ew.setSqlSelect("id,title,content");
		ew.eq("typeId",id);
		PageInfo pageInfo = new PageInfo(pageIndex,pageLimit, "createTime", "desc");
		Page<BorderKnowledge> pages = new Page<BorderKnowledge>(pageInfo.getNowpage(), pageInfo.getSize());
		JSONArray arr = new JSONArray();
		pages= borderKnowledgeService.selectPage(pages, ew);
		for (BorderKnowledge n : pages.getRecords()) {
			EntityWrapper<BorderKnowledge> es = new EntityWrapper<BorderKnowledge>();
			es.setSqlSelect("id,title,content");
			es.eq("id", n.getId());
			Map<String, Object> selectMap = borderKnowledgeService.selectMap(es);
			selectMap.put("content",HtmlUtils.htmlUnescape(selectMap.get("content").toString()));
			arr.add(selectMap);
		}
		return renderSuccess(arr);
	}
	/*
	 * 知识库类别
	 * 
	 */
	@RequestMapping("/getType")
	@ResponseBody
	public Object showListInfo(String token,HttpServletRequest req){
		EntityWrapper<KnowledgeType> ew=new EntityWrapper<KnowledgeType>();
		List<Map<String, Object>> selectMaps = ktService.selectMaps(ew);
		JSONArray arr = new JSONArray();
		arr.add(selectMaps);
		return renderSuccess(arr);
		
	}
}
