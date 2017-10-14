package com.wangzhixuan.controller.phone;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wangzhixuan.commons.utils.*;
import com.wangzhixuan.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wangzhixuan.commons.base.BaseController;
import com.wangzhixuan.commons.result.PageInfo;
import com.wangzhixuan.commons.shiro.PasswordHash;
import com.wangzhixuan.model.VideoFile;
import com.wangzhixuan.service.IAppVersionService;
import com.wangzhixuan.service.IDailyPatrolService;
import com.wangzhixuan.service.ILiveVideoService;
import com.wangzhixuan.service.IMapMarkService;
import com.wangzhixuan.service.INoticeReadService;
import com.wangzhixuan.service.INoticeService;
import com.wangzhixuan.service.IPatrolTrailService;
import com.wangzhixuan.service.IPositionMarkService;
import com.wangzhixuan.service.IRoadService;
import com.wangzhixuan.service.IRoadTrailService;
import com.wangzhixuan.service.ISendFileService;
import com.wangzhixuan.service.ISendInfoService;
import com.wangzhixuan.service.ISendMessageService;
import com.wangzhixuan.service.ISysConfigService;
import com.wangzhixuan.service.ISysLogService;
import com.wangzhixuan.service.IUserService;
import com.wangzhixuan.service.IVideoFileService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/phone")
public class PhoneCommonController extends BaseController {


    @Autowired
    private IUserService usrService;

    @Autowired
    private ILiveVideoService iLiveVideoService;

    @Autowired
    private IRoadService roadService;

    @Autowired
    private ISendFileService sendFileService;

    @Autowired
    private ISendInfoService sendInfoService;

    @Autowired
    private PasswordHash passwordHash;

    @Autowired
    private IRoadTrailService roadtrailService;

    @Autowired
    private IDailyPatrolService dailypatrolService;

    @Autowired
    private IPositionMarkService markService;

    @Autowired
    private ISendMessageService smsgService;

    @Autowired
    private INoticeReadService inotService;

    @Autowired
    private IMapMarkService mmService;

    @Autowired
    private INoticeService inService;

    @Autowired
    private IPatrolTrailService ptrailService;

    @Autowired
    private IAppVersionService IAppService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private IVideoFileService videoFileService;

    @Autowired
    private ISysLogService sysLogService;

    @RequestMapping("/loginByPhone")
    @ResponseBody
    public Object login(HttpServletRequest req) {
        String username = req.getParameter("userName");
        String password = req.getParameter("userPassword");
        String devicenum = req.getParameter("deviceCode");

        if (StringUtils.isBlank(devicenum)) {
            return renderError("设备不可用！");
        }
        if (StringUtils.isBlank(username)) {
            return renderError("用户名不能为空！");
        }
        if (StringUtils.isBlank(password)) {
            return renderError("密码不能为空！");
        }
        try {
            EntityWrapper<User> wrapper = new EntityWrapper<User>();
            wrapper.addFilter(" login_name = {0}  ", username);
            wrapper.eq("user_type", 1);
            User user = usrService.selectOne(wrapper);
            if (user == null) {
                return renderError("用户名不正确！");
            }
            if (!user.getPassword().equals(passwordHash.toHex(password, user.getSalt()))) {
                return renderError("用户密码不正确！");
            }
            if (user.getDevicenum() == null || !user.getDevicenum().equals(devicenum) || user.getIsGrant() == 0) {
                user.setDevicenum(devicenum);
                user.setIsGrant(0);
                usrService.updateByUUID(user);
                this.OpLog("移动端登录，等待授权", 1, user.getUuid());
                return renderError("请等待授权！");
            }
            SysLog syslog = new SysLog();
            syslog.setLoginName(user.getLoginName());
            syslog.setCreateTime(new Date());
            syslog.setRoleName(user.getName());
            syslog.setClientIp(this.getRemoteHost(req));
            syslog.setOptContent("手机登陆");
            sysLogService.insert(syslog);

            String token = DigestUtils.getRandomString(16);// 存储到token中
            // 将用户头像放入json中
            String url = req.getRequestURL().toString().split(req.getContextPath())[0];
            JSONObject j = new JSONObject();
            j.put("avatarUrl", url + user.getIcon());
            j.put("id", user.getId());
            j.put("name", user.getName());
            j.put("politicalStatus", StringUtils.politicalStatus(user.getPolitics()));
            j.put("token", token);
            j.put("birthday", user.getBirthday());
            j.put("phone", user.getPhone());
            j.put("address", user.getHomeAdress());
            j.put("sex", user.getSex() == 0 ? "男" : "女");
            j.put("uuid", user.getUuid());
            user.setToken(token);
            // 获取路段名称信息
            Road road = roadService.selectByUUid(user.getRoad());
            if (road != null) {
                j.put("road", road.getName());
                j.put("totalDistance", road.getDistance());
                // 获取路段的轨迹信息
                EntityWrapper<RoadTrail> wrapper1 = new EntityWrapper<RoadTrail>();
                wrapper1.addFilter(" rid = {0} ", user.getRoad());
                wrapper1.orderBy("id", true);
                List<RoadTrail> route = roadtrailService.selectList(wrapper1);
                List<Double> arr = new ArrayList<>();
                for (RoadTrail rt : route) {
                    arr.add(rt.getLongitude());
                    arr.add(rt.getLatitude());
                }
                String ben = null;
                String end = null;
                if (route.size() > 0) {
                    ben = "{\"longitude\":" + route.get(0).getLongitude() + ",\"latitude\":"
                            + route.get(0).getLatitude() + "}";

                    end = "{\"longitude\":" + route.get(route.size() - 1).getLongitude() + ",\"latitude\":"
                            + route.get(route.size() - 1).getLatitude() + "}";
                } else {
                    ben = "{\"longitude\":0,\"latitude\":0}";

                    end = "{\"longitude\":0,\"latitude\":0}";
                }
                j.put("beginPos", ben);
                j.put("endPos", end);
                j.put("roadLine", arr);
                j.put("totaltime", road.getTime());
            }
            EntityWrapper<DailyPatrol> wrapper1 = new EntityWrapper<>();
            //patrol_id -- 巡防人员id
            wrapper1.eq("patrol_id", user.getUuid());
            wrapper1.where("end_time is null");
            wrapper1.eq("route_id", user.getRoad());
            DailyPatrol dp = dailypatrolService.selectOne(wrapper1);
            j.put("patrolid", dp == null ? 0 : dp.getId());
            j.put("starttime", dp == null ? 0 : dp.getStartTime().getTime());
            j.put("odometer", dp == null ? 0 : dp.getPatrolDistance());
            j.put("effectiverange", configService.selectConfig("effective_range"));
            usrService.updateById(user);
            this.OpLog("登陆成功", 1, user.getId());
            return renderSuccess(j);
        } catch (Exception e) {
            e.printStackTrace();
            this.ErrorLog(e, 1, 0l);
            return renderError("系统异常");
        }

    }

    /**
     * 获取巡防员个人信息
     *
     * @param req
     * @return
     */
    @RequestMapping("/personinfo")
    @ResponseBody
    public Object personinfo(HttpServletRequest req, String token) {
        User user = usrService.selectBytoken(token);
        try {
            if (user == null) {
                return renderError("用户登录已过期!");
            }
            JSONObject j = JSONObject.fromObject(user);
            Road road = roadService.selectById(user.getRoad());// getRoadId()
            j.put("roadname", road == null ? "未分配路段" : road.getName());
            this.OpLog("获取巡防员个人信息", 1, user.getUuid());
            return renderSuccess(j);
        } catch (Exception e) {
            this.ErrorLog(e, 1, user.getUuid());
            e.printStackTrace();
            return renderError("系统异常");
        }
    }

    /**
     * 开始巡防--签到
     *
     * @param req
     * @return
     */
    @RequestMapping("/patrolOn")
    @ResponseBody
    public Object patrolon(HttpServletRequest req, String token) {
        User user = usrService.selectBytoken(token);
        if (user == null) {
            return renderError("用户登录已过期!");
        }
        String nowLongitude = req.getParameter("nowLongitude");
        String nowLatitude = req.getParameter("nowLatitude");
        // 保存信息 签到表
        // 签到
        try {
            EntityWrapper<DailyPatrol> wrapper1 = new EntityWrapper<>();
            wrapper1.eq("patrol_id", user.getUuid());
            wrapper1.where("end_time is null");
            wrapper1.eq("route_id", user.getRoad());
            wrapper1.eq("end_status", 0);
            DailyPatrol dp = dailypatrolService.selectOne(wrapper1);
            if (dp != null) {
                return renderError("您有未结束的巡防操作！请重新登陆获取信息");
            }
            DailyPatrol sign = new DailyPatrol();
            sign.setStartTime(new Date());
            sign.setRouteId(user.getRoad());
            sign.setPatrolId(user.getUuid());
            sign.setPatrolDistance(0.0);
            sign.setReportcount(0);
            sign.setTagcount(0);
            sign.setEndStatus(0);
            boolean a = dailypatrolService.insert(sign);
            if (!a) {
                this.OpLog("签到失败", 1, user.getUuid());
                return renderError("数据保存失败");
            }
            PatrolTrail pt = new PatrolTrail();
            pt.setCreateTime(new Date());
            pt.setLongitude(Double.valueOf(nowLongitude));
            pt.setLatitude(Double.valueOf(nowLatitude));
            pt.setPid(sign.getUuid());
            pt.setInterval(0.0);
            pt.setSpeed(0.0);
            pt.setMovetype(100);
            ptrailService.insert(pt);
            JSONObject j = new JSONObject();
            j.put("patrolid", sign.getId());
            this.OpLog("签到成功", 1, user.getUuid());
            return renderSuccess(j);
        } catch (Exception e) {
            e.printStackTrace();
            this.ErrorLog(e, 1, user.getUuid());
            return renderError("系统异常");
        }
    }

    /**
     * 退出巡防--签退
     *
     * @param req
     * @return
     */
    @RequestMapping("/patrolOff")
    @ResponseBody
    public Object patroloff(HttpServletRequest req, String token) {
        String nowLongitude = req.getParameter("nowLongitude");
        String nowLatitude = req.getParameter("nowLatitude");
        String pid = req.getParameter("patrolid");
        User user = usrService.selectBytoken(token);
        if (user == null) {
            return renderError("用户登录已过期!");
        }
        try {
            // 签退
            double i = req.getParameter("odometer") == null ? 0.0 : Double.valueOf(req.getParameter("odometer").toString());
            DailyPatrol sign = dailypatrolService.selectById(pid);
            Date d = new Date();
            sign.setEndTime(d);
            sign.setEndStatus(1);
            int time = StringUtils.getTime(sign.getStartTime(), d);
            sign.setTime(time);
            sign.setPatrolDistance(i);
            sign.setEndStatus(1);
            boolean a = dailypatrolService.updateById(sign);
            if (!a) {
                this.OpLog("退签失败", 1, user.getUuid());
                return renderError("数据保存失败");
            }
            PatrolTrail pt = new PatrolTrail();
            pt.setCreateTime(d);
            pt.setLongitude(Double.valueOf(nowLongitude));
            pt.setLatitude(Double.valueOf(nowLatitude));
            pt.setPid(pid);
            pt.setInterval(i);
            pt.setSpeed(0.0);
            pt.setMovetype(101);
            ptrailService.insert(pt);
            this.OpLog("退签成功", 1, user.getUuid());
            return renderSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            this.ErrorLog(e, 1, user.getUuid());
            return renderError("系统异常");
        }
    }

    /**
     * 实时坐标
     *
     * @param patrolid
     * @param token
     * @return
     */
    @RequestMapping("rtposition")
    @ResponseBody
    public Object rtposition(String patrolid, String token, HttpServletRequest req) {
        Double longitude = Double.valueOf(req.getParameter("nowLongitude"));
        Double latitude = Double.valueOf(req.getParameter("nowLatitude"));

        User user = usrService.selectBytoken(token);
        if (user == null) {
            return renderError("用户登录已过期!");
        }
        Double speed = req.getParameter("speed") == null ? 0.0 : Double.valueOf(req.getParameter("speed").toString());
        int movetype = req.getParameter("movetype") == null ? 1 : Integer.valueOf(req.getParameter("movetype").toString());
        try {
            DailyPatrol dp = dailypatrolService.selectById(patrolid);
            if (dp.getEndTime() != null) {

                return renderError("本次巡防已结束！");
            }
            double i = req.getParameter("odometer") == null ? 0.0 : Double.valueOf(req.getParameter("odometer"));
            dp.setPatrolDistance(i);
            dailypatrolService.updateById(dp);
            PatrolTrail pt = new PatrolTrail();
            pt.setCreateTime(new Date());
            pt.setLongitude(longitude);
            pt.setLatitude(latitude);
            pt.setPid(patrolid);
            pt.setMovetype(movetype);
            pt.setSpeed(speed);
            pt.setInterval(i);
            ptrailService.insert(pt);
            return renderSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            this.ErrorLog(e, 1, user.getUuid());
            return renderError("系统异常");
        }
    }

    /**
     * 标记信息保存
     *
     * @param multipartFiles 文件附件*s
     * @param req
     * @return
     * @throws SQLException
     */
    @RequestMapping("/savepatrolmark")
    @ResponseBody
    @Transactional
    public Object savepatrolmark(@RequestParam("file") MultipartFile[] multipartFiles, HttpServletRequest req,
                                 String token) throws SQLException {
        User user = usrService.selectBytoken(token);
        if (user == null) {
            return renderError("用户登录已过期!");
        }
        try {
            String pid = req.getParameter("patrolid");
            if (pid != null) {
                DailyPatrol dp = dailypatrolService.selectById(pid);
                dp.setTagcount(dp.getTagcount() + 1);
                dailypatrolService.updateById(dp);
            }
            String pinfo = req.getParameter("info");
            String mmid = req.getParameter("boundaryMarkerID");
            PositionMark info = new PositionMark();
            info.setUid(user.getUuid());// 固定上传用户
            info.setCreateTime(new Date());
            info.setMmid((mmid == null || mmid.equals("") || mmid.toLowerCase().equals("null") || mmid.equals("undefined")) ? null : mmid);
            info.setName(req.getParameter("title").toString());
            info.setType(Integer.valueOf(req.getParameter("type")));
            info.setInfo(pinfo == null ? "" : pinfo);
            info.setLongitude(Double.valueOf(req.getParameter("longitude").toString()));
            info.setLatitude(Double.valueOf(req.getParameter("latitude").toString()));
            info.setInfoStatus(0);
            info.setPid(pid);
            info.setUuid(UUID.randomUUID().toString());
            boolean a = markService.insert(info);
            System.out.println(info.toString());
            if (a == false) {
                this.OpLog("坐标保存失败:" + pinfo, 1, user.getUuid());
                return renderError("标记信息存储失败！");
            }
            String date = new SimpleDateFormat("yyyy_MM").format(new Date());
            // 存储图片的物理路径
            String pic_path = configService.selectConfig("upload_url") + "/" + date;
//			String url =req.getRequestURL().toString().replace(req.getServletPath(), "/upload/"+date+"/");
            String url = req.getContextPath() + "/upload/" + date + "/";
            for (MultipartFile multipartFile : multipartFiles) {
                SendFile ifile = new SendFile();
                // 文件的原始名称
                String originalFilename = multipartFile.getOriginalFilename();
                String newFileName = null;
                if (multipartFile != null && originalFilename != null && originalFilename.length() > 0) {
                    newFileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));

                    // 如果文件夹不存在则创建
                    File file = new File(pic_path);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    // 新文件路径实例
                    File targetFile = new File(pic_path, newFileName);
                    // 内存数据读入磁盘
                    multipartFile.transferTo(targetFile);
                    String type = ContentTypeMaps.ContentType(targetFile);
                    ifile.setFileurl(url + newFileName);
                    ifile.setFilename(originalFilename);
                    ifile.setFilesuffix(originalFilename.substring(originalFilename.lastIndexOf(".") + 1));
                    ifile.setFiletype(StringUtils.getType(type));
                    ifile.setOwenid(info.getUuid());// 所属标记信息id
                    ifile.setFilesource(1);// 类型 1:标记信息文件
                    ifile.setUuid(UUID.randomUUID().toString());//UUID唯一标识

                    sendFileService.insert(ifile);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        this.OpLog("信息报送", 1, user.getId());
        return renderSuccess("报送成功");
    }

    /**
     * 获取标及信息历史记录
     *
     * @param req
     * @return
     */
    @RequestMapping("/marklist")
    @ResponseBody
    public Object markList(HttpServletRequest req, String token, Integer pageIndex) {
        User user = usrService.selectBytoken(token);
        if (user == null) {
            return renderError("用户登录已过期!");
        }
        try {
            EntityWrapper<PositionMark> ewp = new EntityWrapper<>();
            ewp.setSqlSelect("id,name as title,type,info,unix_timestamp(create_time)*1000 as datetime,longitude,latitude,mmid boundaryMarkerID,info_status infostatus");
            //查询已审核过的
//			ewp.eq("info_status", 2);//注释后查询所有的标记信息
            ewp.eq("uid", user.getUuid());
            PageInfo pageInfo = new PageInfo(pageIndex, 10, "create_time", "desc");
            Page<Map<String, Object>> pages = new Page<Map<String, Object>>(pageInfo.getNowpage(), pageInfo.getSize());
            pages = markService.selectMapsPage(pages, ewp);
            JSONArray arr = new JSONArray();
            String url = req.getRequestURL().toString().split(req.getContextPath())[0];
            for (Map<String, Object> mar : pages.getRecords()) {
                JSONObject j = JSONObject.fromObject(mar);
                EntityWrapper<SendFile> ew = new EntityWrapper<>();
                ew.setSqlSelect(" CASE filetype when 1 then 'image'  when 2 then 'audio'  when 3 then 'video'  else 'image' end infoName,CONCAT('" + url + "',fileurl) infoPath,filesuffix");
                ew.eq("filesource", 1);
                ew.eq("owenid", mar.get("uuid"));
                List<Map<String, Object>> files = sendFileService.selectMaps(ew);
                j.put("file", JsonUtils.toJson(files));
                arr.add(j);
            }
            if (pageIndex > pages.getPages()) {
                return renderError("我是有底线的！");
            }
            this.OpLog("获取历史记录", 1, user.getUuid());
            return renderSuccess(arr);
        } catch (Exception e) {
            e.printStackTrace();
            this.ErrorLog(e, 1, user.getUuid());
            return renderError("系统异常");
        }
    }

    /**
     * 获取单条的标记信息
     *
     * @param req
     * @return
     */
    @RequestMapping("/markinfo")
    @ResponseBody
    public Object markinfo(HttpServletRequest req, String token, String uuid) {

        User user = usrService.selectBytoken(token);
        if (user == null) {
            return renderError("用户登录已过期!");
        }
        try {
            String url = req.getRequestURL().toString().split(req.getContextPath())[0];
            EntityWrapper<PositionMark> ew_positionMark = new EntityWrapper<>();
            ew_positionMark.eq("uuid", uuid);
            PositionMark info = markService.selectOne(ew_positionMark);

            EntityWrapper<SendFile> ew = new EntityWrapper<>();
            ew.setSqlSelect(" CASE filetype when 1 then 'image'  when 2 then 'audio'  when 3 then 'video'  else 'image' end infoName,CONCAT('" + url + "',fileurl) infoPath,filesuffix");
            ew.eq("datasource", 1);
            ew.eq("owenid", uuid);
            List<Map<String, Object>> files = sendFileService.selectMaps(ew);
            JSONObject j = JSONObject.fromObject(info);
            j.put("files", JsonUtils.toJson(files));
            this.OpLog("获取标记信息", 1, user.getUuid());
            return renderSuccess(j);
        } catch (Exception e) {
            e.printStackTrace();
            this.ErrorLog(e, 1, user.getUuid());
            return renderError("系统异常");
        }
    }

    /**
     * 主数据：名称，类型，内容，是否有附件： 附件数据：附件文件 和 类型
     *
     * @param multipartFiles
     * @param req
     * @param token
     * @return
     * @throws SQLException
     */
    @RequestMapping("/savesendinfo")
    @ResponseBody
    @Transactional
    public Object savesendinfo(@RequestParam("file") MultipartFile[] multipartFiles, HttpServletRequest req,
                               String token) throws SQLException {
        User user = usrService.selectBytoken(token);
        if (user == null) {
            return renderError("用户登录已过期!");
        }
        try {
            String pid = req.getParameter("patrolid");
            DailyPatrol dp = dailypatrolService.selectById(pid);
            dp.setReportcount(dp.getReportcount() + 1);
            dailypatrolService.updateById(dp);
            // 保存报送信息主表
            SendInfo info = new SendInfo();
            info.setSendId(user.getUuid());// 固定上传用户
            info.setReceiveTime(new Date(Long.valueOf(req.getParameter("datetime"))));
            info.setSendInfoName(req.getParameter("title").toString());
            info.setSendInfoType(Integer.valueOf(req.getParameter("type")));
            info.setLongitude(Double.valueOf(req.getParameter("longitude").toString()));
            info.setLatitude(Double.valueOf(req.getParameter("latitude").toString()));
            info.setUrgencyLevel(Integer.valueOf(req.getParameter("level").toString()));
            info.setSendInfo(req.getParameter("info"));
            info.setDataStatus(1);
            info.setPid(pid);
            boolean a = sendInfoService.insert(info);
            if (!a) {
                return renderError("主信息存储失败！");
            }
            String date = new SimpleDateFormat("yyyy_MM").format(new Date());
            // 存储图片的物理路径
            String pic_path = configService.selectConfig("upload_url") + "/" + date;
//			String url =req.getRequestURL().toString().replace(req.getServletPath(), "/upload/"+date+"/");
            String url = req.getContextPath() + "/upload/" + date + "/";
            for (MultipartFile multipartFile : multipartFiles) {// 保存附件信息*s
                SendFile ifile = new SendFile();
                // 文件的原始名称
                String originalFilename = multipartFile.getOriginalFilename();
                String newFileName = null;
                if (multipartFile != null && originalFilename != null && originalFilename.length() > 0) {
                    newFileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
                    File file = new File(pic_path);// 实例化文件夹，如果文件夹不存在则创建
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    File targetFile = new File(pic_path, newFileName);// 新文件路径实例
                    multipartFile.transferTo(targetFile);// 内存数据读入磁盘
                    String type = ContentTypeMaps.ContentType(targetFile);// 获取文件的contentType
                    ifile.setFileurl(url + newFileName);
                    ifile.setFilename(originalFilename);
                    ifile.setFilesuffix(originalFilename.substring(originalFilename.lastIndexOf(".") + 1));
                    ifile.setOwenid(info.getUuid());
                    ifile.setFiletype(StringUtils.getType(type));
                    //ifile.setMsgid(0l); // 所属上报信息的消息id
                    ifile.setFilesource(2);// 类型 2:报送信息文件
                    ifile.setUuid(UUID.randomUUID().toString());
                    sendFileService.insert(ifile);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        this.OpLog("报送信息", 1, user.getId());
        return renderSuccess("报送成功");
    }

    /**
     * 报送信息展示列表
     *
     * @param req
     * @param token
     * @return
     */
    @RequestMapping("/sendlist")
    @ResponseBody
    public Object sendlist(HttpServletRequest req, Integer pageIndex, String token) {
        User user = usrService.selectBytoken(token);
        if (user == null) {
            return renderError("用户登录已过期!");
        }
        try {
            EntityWrapper<SendInfo> ewq = new EntityWrapper<>();
            ewq.setSqlSelect(
                    "id,send_info_name title,send_info_type type,urgency_level level,send_info info,longitude,latitude,unix_timestamp(receive_time)*1000 as datetime,info_status infostatus ");
            //查询已审核过的
//			ewq.eq("info_status", 2);//注释后查询所有的标记信息
            ewq.eq("send_id", user.getUuid());
            PageInfo pageInfo = new PageInfo(pageIndex, 10, "receive_time", "desc");
            Page<Map<String, Object>> pages = new Page<Map<String, Object>>(pageInfo.getNowpage(), pageInfo.getSize());
            pages = sendInfoService.selectMapsPage(pages, ewq);
            JSONArray arr = new JSONArray();
            String url = req.getRequestURL().toString().split(req.getContextPath())[0];
            for (Map<String, Object> mar : pages.getRecords()) {
                JSONObject j = JSONObject.fromObject(mar);
                EntityWrapper<SendFile> ew = new EntityWrapper<>();
                ew.setSqlSelect(" CASE filetype when 1 then 'image'  when 2 then 'audio'  when 3 then 'video' else 'image' end infoName,CONCAT('" + url + "',fileurl) infoPath,filesuffix");
                ew.eq("msgid", 0);
                ew.eq("owenid", mar.get("uuid"));
                ew.eq("filesource", 2);
                List<Map<String, Object>> a = sendFileService.selectMaps(ew);
                j.put("file", JsonUtils.toJson(a));
                arr.add(j);
            }
            if (pageIndex > pages.getPages()) {
                return renderError("我是有底线的！");
            }
            this.OpLog("获取报送信息", 1, user.getUuid());
            return renderSuccess(arr);
        } catch (Exception e) {
            e.printStackTrace();
            this.ErrorLog(e, 1, user.getUuid());
            return renderError("系统异常");
        }
    }

    /**
     * 获取一条报送信息的内容
     *
     * @param reportid
     * @param token
     * @return
     */
    @RequestMapping("reportmsg")
    @ResponseBody
    public Object sendinfo(String reportid, String token, HttpServletRequest req) {
        User user = usrService.selectBytoken(token);
        if (user == null) {
            return renderError("用户登录已过期!");
        }
        try {
            JSONObject j = new JSONObject();

            EntityWrapper<SendMessage> wrapper = new EntityWrapper<SendMessage>();
            wrapper.setSqlSelect("id,msg_info info,unix_timestamp(creat_time)*1000 as datetime  ");
            wrapper.eq("pid", reportid);
            JSONArray arr = new JSONArray();
            List<Map<String, Object>> msglist = smsgService.selectMaps(wrapper);
            String url = req.getRequestURL().toString().split(req.getContextPath())[0];
            for (Map<String, Object> msg : msglist) {
                JSONObject sj = JSONObject.fromObject(msg);
                EntityWrapper<SendFile> wrapper1 = new EntityWrapper<SendFile>();
                wrapper1.setSqlSelect(" CASE filetype when 1 then 'image'  when 2 then 'audio'  when 3 then 'video'  else 'image' end infoName,CONCAT('" + url + "',fileurl) infoPath");
                wrapper1.eq("owenid", reportid);
                wrapper1.eq("msgid", msg.get("uuid"));
                wrapper1.eq("filesource", 2);
                List<Map<String, Object>> files = sendFileService.selectMaps(wrapper1);
                sj.put("file", JsonUtils.toJson(files));
                arr.add(sj);
            }
            j.put("msg", arr);
            j.put("reportid", reportid);
            this.OpLog("获取报送信息", 1, user.getUuid());
            return renderSuccess(j);
        } catch (Exception e) {
            e.printStackTrace();
            this.ErrorLog(e, 1, user.getUuid());
            return renderError("系统异常");
        }
    }

    /**
     * 修改添加报送信息
     *
     * @param req
     * @param token
     * @return
     * @throws SQLException
     */
    @RequestMapping("/addsendinfo")
    @ResponseBody
    @Transactional
    public Object addsendinfo(@RequestParam("file") MultipartFile[] multipartFiles, HttpServletRequest req,
                              String token) throws SQLException {
        User user = usrService.selectBytoken(token);
        if (user == null) {
            return renderError("用户登录已过期!");
        }
        String id = req.getParameter("reportid");
        if (id == null || id.equals("")) {
            return renderError("reportid is null !");
        }

        SendInfo info = sendInfoService.selectById(id);
        try {
            // 保存报送信息消息表
            SendMessage smsg = new SendMessage();
            smsg.setMsgInfo(req.getParameter("info"));
            smsg.setCreatTime(new Date(Long.valueOf(req.getParameter("datetime"))));
            smsg.setPid(info.getUuid());
            smsg.setType(1);
            smsg.setUuid(UUID.randomUUID().toString());
            boolean b = smsgService.insert(smsg);
            if (b == false) {
                return renderError("消息存储失败！");
            }
            // 保存附件信息*s
            String date = new SimpleDateFormat("yyyy_MM").format(new Date());
            // 存储图片的物理路径
            String pic_path = configService.selectConfig("upload_url") + "/" + date;
//			String url =req.getRequestURL().toString().replace(req.getServletPath(), "/upload/"+date+"/");
            String url = req.getContextPath() + "/upload/" + date + "/";
            for (MultipartFile multipartFile : multipartFiles) {
                SendFile ifile = new SendFile();
                // 文件的原始名称
                String originalFilename = multipartFile.getOriginalFilename();
                String newFileName = null;
                if (multipartFile != null && originalFilename != null && originalFilename.length() > 0) {
                    newFileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
                    // 如果文件夹不存在则创建
                    File file = new File(pic_path);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    // 新文件路径实例
                    File targetFile = new File(pic_path, newFileName);
                    // 内存数据读入磁盘
                    multipartFile.transferTo(targetFile);
                    String type = ContentTypeMaps.ContentType(targetFile);
                    ifile.setFileurl(url + newFileName);
                    ifile.setFilename(originalFilename);
                    ifile.setFilesuffix(originalFilename.substring(originalFilename.lastIndexOf(".") + 1));
                    ifile.setOwenid(info.getUuid());
                    ifile.setFiletype(StringUtils.getType(type));
                    ifile.setMsgid(smsg.getUuid());
                    ifile.setFilesource(2);
                    ifile.setUuid(UUID.randomUUID().toString());
                    sendFileService.insert(ifile);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.ErrorLog(e, 1, user.getUuid());
            throw new RuntimeException();
        }
        this.OpLog("添加报送信息", 1, user.getUuid());
        return renderSuccess();
    }

    /**
     * 密码修改
     *
     * @param req
     * @param token
     * @return
     */
    @RequestMapping("/editPassword")
    @ResponseBody
    public Object editPassword(HttpServletRequest req, String token) {
        User user = usrService.selectBytoken(token);
        if (user == null) {
            return renderError("用户登录已过期!");
        }
        try {
            String password = req.getParameter("oldPassword");
            String newPass = req.getParameter("newPassword");
            String pwd = passwordHash.toHex(password, user.getSalt());
            if (!pwd.equals(user.getPassword())) {
                this.OpLog("用户登录失败", 1, user.getId());
                return renderError("原密码不正确");
            }
            String salt = StringUtils.getUUId();
            user.setPassword(passwordHash.toHex(newPass, salt));
            user.setSalt(salt);

            //todo 待测试，根据UUID更新
            EntityWrapper<User> wrapper = new EntityWrapper<User>();
            wrapper.eq("uuid", user.getUuid());
            usrService.update(user, wrapper);
            this.OpLog("修改密码", 1, user.getUuid());
            return renderSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            this.ErrorLog(e, 1, user.getUuid());
            return renderError("系统异常");
        }
    }

    /**
     * 退出登录
     *
     * @param req
     * @param token
     * @return
     */
    @RequestMapping("/logoutPhone")
    public Object logout(HttpServletRequest req, String token) {
        User user = usrService.selectBytoken(token);
        if (user == null) {
            return renderError("用户登录已过期!");
        }
        try {
            user.setToken(null);
            //todo 待测试，根据UUID更新
            EntityWrapper<User> wrapper = new EntityWrapper<User>();
            wrapper.eq("uuid", user.getUuid());
            usrService.update(user, wrapper);
            //usrService.updateById(user);
            this.OpLog("退出登录", 1, user.getUuid());
            return renderSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            this.ErrorLog(e, 1, user.getUuid());
            return renderError("系统异常");
        }
    }

    /**
     * 获取公告列表
     *
     * @param req
     * @param token
     * @return
     */
    @RequestMapping("/showList")
    @ResponseBody
    public Object showList(HttpServletRequest req, String token, int pageLimit, int pageIndex) {
        User user = usrService.selectBytoken(token);
        if (user == null) {
            return renderError("用户登录已过期!");
        }
        try {
            EntityWrapper<NoticeRead> ew = new EntityWrapper<NoticeRead>();
            ew.eq("uid", user.getUuid());
            ew.eq("nstatus", 1);
            PageInfo pageInfo = new PageInfo(pageIndex, pageLimit, "create_time", "desc");
            Page<NoticeRead> pages = new Page<NoticeRead>(pageInfo.getNowpage(), pageInfo.getSize());
            pages = inotService.selectPage(pages, ew);
            JSONArray arr = new JSONArray();
            for (NoticeRead noticeRead : pages.getRecords()) {
                EntityWrapper<Notice> ew1 = new EntityWrapper<>();
                ew1.setSqlSelect("id,title,type,info,unix_timestamp(create_time)*1000 as datetime");
                ew1.eq("uuid", noticeRead.getNid());
                Map<String, Object> n = inService.selectMap(ew1);
                n.put("info", HtmlUtils.htmlUnescape(n.get("info").toString()));
                n.put("flag", noticeRead.getStatus() == 1 ? true : false);
                arr.add(n);
            }
            return renderSuccess(arr);
        } catch (Exception e) {
            e.printStackTrace();
            this.ErrorLog(e, 1, user.getUuid());
            return renderError("系统异常");
        }
    }

    /**
     * 获取新公告
     *
     * @param req
     * @param token
     * @return
     */
    @RequestMapping("/newList")
    @ResponseBody
    public Object newList(HttpServletRequest req, String token) {
        User user = usrService.selectBytoken(token);
        if (user == null) {
            return renderError("用户登录已过期!");
        }
        try {
            EntityWrapper<NoticeRead> ew = new EntityWrapper<NoticeRead>();
            ew.eq("uid", user.getUuid());
            ew.eq("status", 0);
            ew.eq("nstatus", 1);
            ew.orderBy("create_time", true);
            List<NoticeRead> note = inotService.selectList(ew);
            JSONArray arr = new JSONArray();
            for (NoticeRead noticeRead : note) {
                EntityWrapper<Notice> ew1 = new EntityWrapper<>();
                ew1.setSqlSelect("id,title,type,info,unix_timestamp(create_time)*1000 as datetime");
                ew1.eq("uuid", noticeRead.getNid());
                Map<String, Object> n = inService.selectMap(ew1);
                n.put("info", HtmlUtils.htmlUnescape(n.get("info").toString()));
                n.put("flag", false);
                arr.add(n);
            }
            this.OpLog("获取新公告", 1, user.getId());
            JSONObject j = new JSONObject();
            j.put("success", true);
            j.put("total", note.size());
            j.put("obj", arr);
            return j;
        } catch (Exception e) {
            e.printStackTrace();
            this.ErrorLog(e, 1, user.getUuid());
            return renderError("系统异常");
        }
    }

    /**
     * 消息已读
     *
     * @param token
     * @param uuid
     * @return
     */
    @RequestMapping(value = "seen")
    @ResponseBody
    public Object readnotice(String token, String uuid) {
        User user = usrService.selectBytoken(token);
        if (user == null) {
            return renderError("用户登录已过期!");
        }
        EntityWrapper<NoticeRead> ew = new EntityWrapper<NoticeRead>();
        ew.eq("nid", uuid);
        ew.eq("uid", user.getUuid());
        NoticeRead n = inotService.selectOne(ew);
        if (n == null) {
            this.OpLog("读取消息失败", 1, user.getId());
            return renderError("未知消息标识");
        } else {
            n.setStatus(1);
            //todo 待测试，根据 nid、uid 更新
            EntityWrapper<NoticeRead> wrapper = new EntityWrapper<NoticeRead>();
            wrapper.eq("nid", uuid);
            wrapper.eq("uid", user.getUuid());
            inotService.update(n,wrapper);
            //n.updateById();
            this.OpLog("通知公告已阅", 1, user.getUuid());
            return renderSuccess();
        }
    }

    /**
     * 地图标记
     *
     * @param req
     * @param token
     * @return
     */
    @RequestMapping("/mapmark")
    @ResponseBody
    public Object mapmark(HttpServletRequest req, String token) {
        User user = usrService.selectBytoken(token);
        if (user == null) {
            return renderError("用户登录已过期!");
        }
        EntityWrapper<MapMark> ew = new EntityWrapper<>();
        ew.setSqlSelect("id,name title,longitude,latitude");
        ew.eq("type", 0);
        List<Map<String, Object>> mms = mmService.selectMaps(ew);
        this.OpLog("获取地图标记", 1, user.getUuid());
        return renderSuccess(mms);
    }

    /**
     * 版本检查
     *
     * @return
     */
    @RequestMapping("/checkVersion")
    @ResponseBody
    public Object checkversion(HttpServletRequest req) {
        try {
            JSONObject j = new JSONObject();
            String url = req.getRequestURL().toString().split(req.getContextPath())[0];
            EntityWrapper<AppVersion> ew = new EntityWrapper<>();
            ew.eq("status", 1);
            ew.eq("type", 1);
            AppVersion avn = IAppService.selectOne(ew);
            if (avn != null) {
                j.put("appStatus", true);
                j.put("appurl", url + avn.getUrl());
                j.put("appversion", avn.getVersion());
            } else {
                j.put("appStatus", false);
            }
            EntityWrapper<AppVersion> ewpl = new EntityWrapper<>();
            ewpl.eq("status", 1);
            ewpl.eq("type", 3);
            AppVersion pl = IAppService.selectOne(ewpl);
            if (pl != null) {
                j.put("pluginStatus", true);
                j.put("pluginurl", url + pl.getUrl());
                j.put("pluginversion", pl.getVersion());
            } else {
                j.put("pluginStatus", false);
            }
            this.OpLog("版本检查", 1, 0l);
            return renderSuccess(j);
        } catch (Exception e) {
            e.printStackTrace();
            this.ErrorLog(e, 1, 0l);
            return renderError("系统错误！");
        }
    }

    /**
     * 多定位数据上传--------------------------分割线------------10-13  修改到这里
     */
    @RequestMapping("trailList")
    @ResponseBody
    @Transactional
    public Object trailList(String token, HttpServletRequest req) throws SQLException {
        User user = usrService.selectBytoken(token);
        if (user == null) {
            return renderError("用户登录已过期!");
        }
        try {
            String json = req.getParameter("patrols");
            JSONArray arr = JSONArray.fromObject(json);
            for (Object j : arr) {
                JSONObject locate = JSONObject.fromObject(j);
                String pid = locate.getString("patrolid");
                Double longitude = locate.getDouble("longitude");
                Double latitude = locate.getDouble("latitude");
                String uuid = locate.getString("uuid");

                //先更新上一条记录的 patrolDistance 字段
                PatrolTrail last = ptrailService.selectnew(pid);
                Double i = DigestUtils.GetDistance(last.getLongitude(), last.getLatitude(),
                        longitude, latitude);
                EntityWrapper<DailyPatrol> wrapper = new EntityWrapper<DailyPatrol>();
                wrapper.eq("uuid", uuid);
                //TODO 待测试
                DailyPatrol dp = dailypatrolService.selectOne(wrapper);
                dp.setPatrolDistance(dp.getPatrolDistance() + i);
                dailypatrolService.updateById(dp);

                //再插入新数据
                PatrolTrail pt = new PatrolTrail();
                pt.setCreateTime(new Date(Long.valueOf(locate.get("datetime").toString())));
                pt.setLongitude(longitude);
                pt.setLatitude(latitude);
                pt.setPid(pid);
                pt.setInterval(i);
                ptrailService.insert(pt);
            }
            this.OpLog("多定位数据上传成功", 1, user.getUuid());
            return renderSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            this.ErrorLog(e, 1, user.getUuid());
            throw new RuntimeException();
        }
    }

    /**
     * 新增手机直播
     *
     * @param token
     * @return
     */
    @RequestMapping("/onlineLive")
    @ResponseBody
    public Object onlineLive(String token, HttpServletRequest rquest) {
        User user = usrService.selectBytoken(token);
        if (user == null) {
            return renderError("用户登录过期！");
        }
        String tcp = configService.selectConfig("video_tcp");
        String ip = configService.selectConfig("video_ip");
        String port = configService.selectConfig("video_port");
        String app = configService.selectConfig("video_app");
       // String lvid = rquest.getParameter("liveid");
        String uuid = rquest.getParameter("uuid");
        try {
            JSONObject j = new JSONObject();
            EntityWrapper<LiveVideo> ew = new EntityWrapper<>();
            ew.eq("live_id", user.getUuid());
            ew.le("status", 1);
            LiveVideo video = iLiveVideoService.selectOne(ew);
            if (video != null) {
                if (video.getStatus() == 0) {
                    video.setStartTime(new Date());
                    video.setStatus(1);
                    video.setTimeMark(new Date().getTime());
                    iLiveVideoService.updateById(video);
                    j.put("liveid", video.getId());
                    j.put("liveurl", video.getUrl());
                    j.put("uuid", video.getUuid());
                    return renderSuccess(j);
                }
                j.put("liveid", video.getId());
                j.put("liveurl", video.getUrl());
                j.put("uuid", video.getUuid());
                return renderSuccess(j);
            }
            if (uuid == null || uuid.equals("")) {
                String url = tcp + "://" + ip + ":" + port + "/" + app + "/" + DigestUtils.getRandomString(8);
                LiveVideo lv = new LiveVideo();
                lv.setLiveId(user.getUuid());
                //lv.setReceiveId(0L);
                lv.setStartTime(new Date());
                lv.setCreateTime(new Date());
                lv.setType(1);
                lv.setUrl(url);
                lv.setTimeMark(new Date().getTime());
                lv.setStatus(1);
                lv.setPatrolid(rquest.getParameter("patrolid"));
                lv.setUuid(UUID.randomUUID().toString());
                lv.insert();
                j.put("liveid", lv.getId());
                j.put("liveurl", lv.getUrl());
                j.put("uuid", lv.getUuid());
            } else {
                EntityWrapper<LiveVideo> wrapper = new EntityWrapper<LiveVideo>();
                wrapper.eq("uuid", uuid);
                LiveVideo lv = iLiveVideoService.selectOne(wrapper);
                lv.setStartTime(new Date());
                lv.setStatus(1);
                lv.setTimeMark(new Date().getTime());

                iLiveVideoService.update(lv,wrapper);
                j.put("liveid", lv.getId());
                j.put("liveurl", lv.getUrl());
                j.put("liveurl", lv.getUuid());
            }
            this.OpLog("手机直播", 1, user.getUuid());
            return renderSuccess(j);
        } catch (Exception e) {
            e.printStackTrace();
            return renderError("系统异常:" + e);
        }
    }

    /**
     * 手机查询TV端的直播请求
     *
     * @param token
     * @return
     */
    @RequestMapping("/QuerySmart")
    @ResponseBody
    public Object phoneQueryBySmart(String token) {
        User user = usrService.selectBytoken(token);
        if (user == null) {
            return renderError("用户登录过期！");
        }

        try {
            EntityWrapper<LiveVideo> ew = new EntityWrapper<LiveVideo>();
            ew.where("live_id= {0}", user.getUuid());
            ew.eq("status", 0);
            LiveVideo lv = iLiveVideoService.selectOne(ew);
            if (lv == null) {
                return renderError("无请求");
            }
            JSONObject js = new JSONObject();
            js.put("liveid", lv.getId());
            js.put("liveurl", lv.getUrl());
            js.put("uuid", lv.getUuid());
            this.OpLog("手机查询TV端的直播请求成功", 1, user.getUuid());
            return renderSuccess(js);
        } catch (Exception e) {
            e.printStackTrace();
            return renderError("系统异常");
        }
    }

    /**
     * 根据直播id，更改直播状态，直播结束
     *
     * @param token
     * @param liveid
     * @return
     */
    @RequestMapping("/endVideo")
    @ResponseBody
    public Object endVideo(String token, String uuid) {
        User user = usrService.selectBytoken(token);
        if (user == null) {
            return renderError("用户登录过期");
        }
        EntityWrapper<LiveVideo> ew = new EntityWrapper<LiveVideo>();
        ew.eq("uuid", uuid);
        LiveVideo lv = iLiveVideoService.selectOne(ew);
        lv.setStatus(2);
        lv.setEndTime(new Date());
        iLiveVideoService.update(lv,ew);
        this.OpLog("更改直播状态，直播结束", 1, user.getUuid());
        return renderSuccess("直播结束");
    }

    /**
     * 问题反馈
     *
     * @param token
     * @param req
     * @return
     */
    @RequestMapping("question")
    @ResponseBody
    public Object question(String token, HttpServletRequest req) {
        User user = usrService.selectBytoken(token);
        if (user == null) {
            return renderError("用户登录过期");
        }
        return renderSuccess();
    }

    /**
     * 手机查询系统配置信息
     *
     * @param token
     * @param req
     * @return
     */
    @RequestMapping("/QueryConfig")
    @ResponseBody
    public Object QueryConfig(String token, HttpServletRequest req) {
        User user = usrService.selectBytoken(token);
        if (user == null) {
            return renderError("用户登录过期！");
        }
        String selKey = req.getParameter("selKey");
        try {
            EntityWrapper<LiveVideo> ew = new EntityWrapper<LiveVideo>();
            ew.eq("selkey", selKey);
            String s = configService.selectConfig(selKey);
            if (s == null) {
                return renderError("无内容");
            }
            JSONObject js = new JSONObject();
            js.put("value", s);
            this.OpLog("手机查询配置信息成功", 1, user.getUuid());
            return renderSuccess(js);
        } catch (Exception e) {
            e.printStackTrace();
            return renderError("系统异常");
        }
    }


    /**
     * 供视频服务器回调使用，截取视频图片，存储视频路径、图片路径
     *
     * @param req
     * @return
     */
    @RequestMapping("/pushInfo")
    @ResponseBody
    public void pushInfo(@RequestBody VideoInfo videoInfo, HttpServletRequest req, HttpServletResponse res) throws IOException {
        String action = req.getParameter("action");

        System.out.println("回调成功:"+action);
        System.out.println(videoInfo.toString());

        //返回状态给SRS服务器
        res.getWriter().print(0);

        //String ip  = configService.selectConfig("video_ip");
        //String http_port = configService.selectConfig("video_http_port");

        String app = videoInfo.getApp();
        String stream = videoInfo.getStream();
        String file = videoInfo.getFile();
        String cwd = videoInfo.getCwd();
        String fileUrl = "";
        String fileName = "";
        String diskPath = "";
        String video_img = "";
        //fileUrl=fileUrl+ip+":"+http_port;
        if (file != null && file.lastIndexOf(app) > -1) {
            fileUrl += file.substring(file.lastIndexOf(app) - 1);
        }
        if (file != null && file.lastIndexOf(stream) > -1) {
            fileName += file.substring(file.lastIndexOf(stream));
        }
        if (file != null && file.length() > 0) {
            diskPath = cwd + file.substring(1);
        }
        // 存储图片的物理路径
        String pic_path = configService.selectConfig("upload_url") + "/" + "videoImg" + "/";
        File imgfile = new File(pic_path);
        if (!imgfile.exists()) {
            imgfile.mkdirs();
        }
        String result = "";
        if (FFmpegUtils.checkfile(diskPath)) {   //判断路径是不是一个文件
            //执行截取图片任务
            result = FFmpegUtils.processFLV(diskPath, pic_path);
            if (result != "-1") {
                video_img = req.getContextPath() + "/upload/videoImg/" + result;
                System.out.println(video_img);
            }
        }

        VideoFile v = new VideoFile();
        v.setCreateTime(new Date());
        v.setFileName(fileName);
        v.setFileUrl(fileUrl);
        v.setDiskPath(diskPath);
        v.setVideoimg(video_img);
        v.setImgDiskPath(pic_path + result);
        v.setVideotype(0);
        v.setUuid(UUID.randomUUID().toString());

        System.out.println(v.toString());

        videoFileService.insert(v);

    }

    /**
     * 测试用
     *
     * @param req
     * @return
     */
    @RequestMapping("/testPushInfo")
    @ResponseBody
    public void pushInfo(String fileUrl, HttpServletRequest req, HttpServletResponse res) throws IOException {
        System.out.println(fileUrl);
        // 存储图片的物理路径
        String pic_path = configService.selectConfig("upload_url") + "/" + "videoImg" + "/";
        File imgfile = new File(pic_path);
        if (!imgfile.exists()) {
            imgfile.mkdirs();
        }

        if (FFmpegUtils.checkfile(fileUrl)) {   //判断路径是不是一个文件
            //执行转码任务
            String result = FFmpegUtils.processFLV(fileUrl, pic_path);
            if (result != "-1") {
                System.out.println(result);
            }
        }
    }

}
