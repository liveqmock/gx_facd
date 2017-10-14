package com.wangzhixuan.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wangzhixuan.commons.base.BaseController;
import com.wangzhixuan.commons.result.PageInfo;
import com.wangzhixuan.commons.shiro.PasswordHash;
import com.wangzhixuan.commons.utils.StringUtils;
import com.wangzhixuan.model.Role;
import com.wangzhixuan.model.User;
import com.wangzhixuan.model.vo.UserVo;
import com.wangzhixuan.service.IRoadService;
import com.wangzhixuan.service.ISysConfigService;
import com.wangzhixuan.service.IUserService;

/**
 * @description：用户管理
 * @author：zhixuan.wang
 * @date：2015/10/1 14:51
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;
    @Autowired
    private PasswordHash passwordHash;
    @Autowired
    private IRoadService irdService;
	@Autowired
	private ISysConfigService configService;

    /**
     * 用户管理页
     *
     * @return
     */
    @GetMapping("/manager")
    public String manager() {
        return "admin/user/user";
    }

    /**
     * 用户管理列表
     *
     * @param userVo
     * @param page
     * @param rows
     * @param sort
     * @param order
     * @return
     */
    @PostMapping("/dataGrid")
    @ResponseBody
    public Object dataGrid(UserVo userVo, Integer page, Integer rows, String sort, String order) {
        PageInfo pageInfo = new PageInfo(page, rows, sort, order);
        Map<String, Object> condition = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(userVo.getName())) {
            condition.put("uname", userVo.getName());
        }
        if (userVo.getOrganizationId() != null) {
            condition.put("organizationId", userVo.getOrganizationId());
        }
        if (userVo.getCreatedateStart() != null) {
            condition.put("startTime", userVo.getCreatedateStart());
        }
        if (userVo.getCreatedateEnd() != null) {
            condition.put("endTime", userVo.getCreatedateEnd());
        }
        pageInfo.setCondition(condition);
        userService.selectDataGrid(pageInfo);
        return pageInfo;
    }
    @PostMapping("/dataGridEx")
    @ResponseBody
    public Object dataGridE(UserVo userVo, Integer page, Integer rows, String sort, String order) {
        PageInfo pageInfo = new PageInfo(page, rows, sort, order);
        Map<String, Object> condition = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(userVo.getName())) {
            condition.put("uname", userVo.getName());
        }
        if (userVo.getOrganizationId() != null) {
            condition.put("organizationId", userVo.getOrganizationId());
        }
        if (userVo.getCreatedateStart() != null) {
            condition.put("startTime", userVo.getCreatedateStart());
        }
        if (userVo.getCreatedateEnd() != null) {
            condition.put("endTime", userVo.getCreatedateEnd());
        }
        pageInfo.setCondition(condition);
        userService.selectDataGrid(pageInfo);
        return pageInfo;
    }
    /**
     * 添加用户页
     *
     * @return
     */
    @GetMapping("/addPage")
    public String addPage() {
        return "admin/user/userAdd";
    }

    /**
     * 添加用户
     *
     * @param userVo
     * @return
     */
    @PostMapping("/add")
    @ResponseBody
    public Object add(@Valid UserVo userVo, BindingResult result,@RequestParam("file") MultipartFile multipartFile,HttpServletRequest req) {
        if (result.hasErrors()) {
            return renderError(result);
        }
        String originalFilename = multipartFile.getOriginalFilename();
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
				userVo.setIcon(url +newFileName);
			} catch (IOException e) {
				e.printStackTrace();
				return renderError("系统异常");
			}
		}
        List<User> list = userService.selectByLoginName(userVo);
        if (list != null && !list.isEmpty()) {
            return renderError("登录名已存在!");
        }
        String salt = StringUtils.getUUId();
        String pwd = passwordHash.toHex(userVo.getPassword(), salt);
        userVo.setSalt(salt);
        userVo.setPassword(pwd);
        userVo.setUuid(UUID.randomUUID().toString());
        userService.insertByVo(userVo);
        return renderSuccess("添加成功");
    }

    /**
     * 编辑用户页
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/editPage")
    public String editPage(Model model, String uuid) {
        UserVo userVo = userService.selectVoByUUID(uuid);
        List<Role> rolesList = userVo.getRolesList();
        List<Long> ids = new ArrayList<Long>();
        for (Role role : rolesList) {
            ids.add(role.getId());
        }
        model.addAttribute("roleIds", ids);
        model.addAttribute("user", userVo);
        return "admin/user/userEdit";
    }

    /**
     * 编辑用户
     *
     * @param userVo
     * @return
     */
   
    @PostMapping("/edit")
    @ResponseBody
    public Object edit(@Valid UserVo userVo,@RequestParam("file") MultipartFile multipartFile,HttpServletRequest req) {
       
    	String originalFilename = multipartFile.getOriginalFilename();
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
				userVo.setIcon(url +newFileName);
			} catch (IOException e) {
				e.printStackTrace();
				return renderError("系统异常");
			}
		}
        List<User> list = userService.selectByLoginName(userVo);
        if (list != null && !list.isEmpty()) {
            return renderError("登录名已存在!");
        }
        // 更新密码
        if (StringUtils.isNotBlank(userVo.getPassword())) {
            User user = userService.selectById(userVo.getId());
            String salt = user.getSalt();
            String pwd = passwordHash.toHex(userVo.getPassword(), salt);
            userVo.setPassword(pwd);
        }
        userService.updateByVo(userVo);
        return renderSuccess("修改成功！");
    }

    /**
     * 修改密码页
     *
     * @return
     */
    @GetMapping("/editPwdPage")
    public String editPwdPage() {
        return "admin/user/userEditPwd";
    }

    /**
     * 修改密码
     *
     * @param oldPwd
     * @param pwd
     * @return
     */
    @PostMapping("/editUserPwd")
    @ResponseBody
    public Object editUserPwd(String oldPwd, String pwd) {
        User user = userService.selectById(getUserId());
        String salt = user.getSalt();
        if (!user.getPassword().equals(passwordHash.toHex(oldPwd, salt))) {
            return renderError("旧密码不正确!");
        }
        userService.updatePwdByUserId(getUserId(), passwordHash.toHex(pwd, salt));
        return renderSuccess("密码修改成功！");
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    public Object delete(String id) {
    	UserVo u =userService.selectVoByUUID(id);
    	
        Long currentUserId = getUserId();
        if (u.getId() == currentUserId) {
            return renderError("不可以删除自己！");
        }
        userService.deleteUserById(u.getId());
        return renderSuccess("删除成功！");
    }
    @RequestMapping("tree")
    @ResponseBody
    public Object roadtree(){
    	return irdService.selectTree();
    }
}
