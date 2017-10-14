package com.wangzhixuan.controller.exchange;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wangzhixuan.commons.base.BaseController;
import com.wangzhixuan.commons.utils.DigestUtils;
import com.wangzhixuan.model.DailyPatrol;
import com.wangzhixuan.model.LiveVideo;
import com.wangzhixuan.model.NoticeRead;
import com.wangzhixuan.model.PatrolTrail;
import com.wangzhixuan.model.PositionMark;
import com.wangzhixuan.model.SendFile;
import com.wangzhixuan.model.SendInfo;
import com.wangzhixuan.model.SendMessage;
import com.wangzhixuan.model.User;
import com.wangzhixuan.service.IDailyPatrolService;
import com.wangzhixuan.service.ILiveVideoService;
import com.wangzhixuan.service.INoticeReadService;
import com.wangzhixuan.service.IPatrolTrailService;
import com.wangzhixuan.service.IPositionMarkService;
import com.wangzhixuan.service.ISendFileService;
import com.wangzhixuan.service.ISendInfoService;
import com.wangzhixuan.service.ISendMessageService;
import com.wangzhixuan.service.ISysConfigService;
import com.wangzhixuan.service.IUserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 数据交换接口-用于接收由区中心转发下来的数据
 */
@Controller
@RequestMapping("/exchange")
public class ExchangeController extends BaseController {

    @Autowired
    private IUserService usrService;
    @Autowired
    private IDailyPatrolService dailypatrolService;
    @Autowired
    private ISendFileService sendFileService;
    @Autowired
    private ISendInfoService sendInfoService;
    @Autowired
    private ISysConfigService configService;
    @Autowired
    private IPositionMarkService markService;
    @Autowired
    private IPatrolTrailService ptrailService;
    @Autowired
    private ISendMessageService smsgService;
    @Autowired
    private ILiveVideoService iLiveVideoService;
    @Autowired
    private INoticeReadService inotService;
    /**
     * 手机端登陆
     * 接收由中心下发过来的数据
     *
     * @param
     * @param req
     * @param
     * @return
     * @throws SQLException
     */
    @RequestMapping("/exchangeLoginByPhone")
    @ResponseBody
    @Transactional
    public Object exchangeLoginByPhone(@RequestBody String requestBody, HttpServletRequest req) throws SQLException {
        try {
            System.out.println("exchangeLoginByPhone接收：" + requestBody);
            JSONObject jsonObject = JSONObject.fromObject(requestBody);
            User user = usrService.selectById(jsonObject.getString("id"));
            if(user!=null){
                user.setDevicenum(jsonObject.getString("devicenum"));
                user.setIsGrant(Integer.valueOf(jsonObject.getString("isGrant")));
                user.setToken(jsonObject.getString("token"));
                usrService.updateById(user);
            }
            return renderSuccess("数据传输成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
    /**
     * 主数据：名称，类型，内容，是否有附件： 附件数据：附件文件 和 类型
     * 接收由中心下发过来的上报信息数据
     *
     * @param
     * @param req
     * @param
     * @return
     * @throws SQLException
     */
    @RequestMapping("/exchangeSendInfo")
    @ResponseBody
    @Transactional
    public Object exchangeSendInfo(@RequestBody String requestBody, HttpServletRequest req) throws SQLException {

        try {
            System.out.println("exchangeSendInfo接收：" + requestBody);
            JSONObject jsonObject = JSONObject.fromObject(requestBody);
            String pid =jsonObject.getString("pid");

            //更新上报数量
            DailyPatrol dp = dailypatrolService.selectById(pid);
            dp.setReportcount(dp.getReportcount() + 1);
            dailypatrolService.updateById(dp);

            // 保存报送信息主表
            SendInfo info = new SendInfo();
            //上报人id
            String sendId = jsonObject.getString("sendId");
            info.setSendId(sendId);
            //处理上报时间
            JSONObject dateJson = JSONObject.fromObject(jsonObject.getString("receiveTime"));
            String time = dateJson.getString("time");
            long lt = new Long(time);
            Date date = new Date(lt);
            info.setReceiveTime(date);

            info.setSendInfoName(jsonObject.getString("sendInfoName").toString());
            info.setSendInfoType(Integer.valueOf(jsonObject.getString("sendInfoType")));
            info.setLongitude(Double.valueOf(jsonObject.getString("longitude").toString()));
            info.setLatitude(Double.valueOf(jsonObject.getString("latitude").toString()));
            info.setUrgencyLevel(Integer.valueOf(jsonObject.getString("urgencyLevel").toString()));
            info.setSendInfo(jsonObject.getString("sendInfo"));
            info.setDataStatus(1);
            info.setPid(pid);
            info.setUuid(jsonObject.getString("uuid"));
            boolean a = sendInfoService.insert(info);
            if (!a) {
                return renderError("上报信息存储失败！");
            }
            JSONArray arr =  jsonObject.getJSONArray("flist");
            for (Object object : arr) {
            	JSONObject js = JSONObject.fromObject(object);
            	SendFile entity = new SendFile();
            	entity.setFilename(js.getString("filename"));
            	entity.setFilesuffix(js.getString("filesuffix"));
            	entity.setFilesource(js.getInt("filesource"));
            	entity.setFileurl(js.getString("fileurl"));
            	entity.setFiletype(js.getInt("filetype"));
//            	entity.setKeycode(js.getInt("keycode"));
            	sendFileService.insert(entity);
            	File f= new File(entity.getFileurl());
            	new File(f.getParent()).mkdirs();
            	f.createNewFile();
			}
            //String date = new SimpleDateFormat("yyyy_MM").format(new Date());
            // 存储图片的物理路径
            String pic_path = configService.selectConfig("upload_url") + "/" + date;
//			String url =req.getRequestURL().toString().replace(req.getServletPath(), "/upload/"+date+"/");
            String url = req.getContextPath() + "/upload/" + date + "/";
//			for (MultipartFile multipartFile : multipartFiles) {// 保存附件信息*s
//				SendFile ifile = new SendFile();
//				// 文件的原始名称
//				String originalFilename = multipartFile.getOriginalFilename();
//				String newFileName = null;
//				if (multipartFile != null && originalFilename != null && originalFilename.length() > 0) {
//					newFileName = UUID.randomUUID() + originalFilename;
//					File file = new File(pic_path);// 实例化文件夹，如果文件夹不存在则创建
//					if (!file.exists()) {
//						file.mkdirs();
//					}
//					File targetFile = new File(pic_path, newFileName);// 新文件路径实例
//					multipartFile.transferTo(targetFile);// 内存数据读入磁盘
//					String type = ContentTypeMaps.ContentType(targetFile);// 获取文件的contentType
//					ifile.setFileurl(url + newFileName);
//					ifile.setFilename(originalFilename);
//					ifile.setFilesuffix(originalFilename.substring(originalFilename.lastIndexOf(".") + 1));
//					ifile.setOwenid(info.getId());
//					ifile.setFiletype(StringUtils.getType(type));
//					ifile.setMsgid(0l); // 所属上报信息的消息id
//					ifile.setFilesource(2);// 类型 2:报送信息文件
//					sendFileService.insert(ifile);
//				}
//			}

            this.OpLog("数据接收成功", 1, sendId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return renderSuccess("数据传输成功");
    }

    /**
     * 标记信息保存
     * 接收由中心下发过来的标记信息数据
     *
     * @param
     * @param req
     * @param
     * @return
     * @throws SQLException
     */
    @RequestMapping("/exchangePatrolMark")
    @ResponseBody
    @Transactional
    public Object exchangePatrolMark(@RequestBody String requestBody, HttpServletRequest req) throws SQLException {

        try {
            System.out.println("exchangePatrolMark接收：" + requestBody);
            JSONObject jsonObject = JSONObject.fromObject(requestBody);
            String pid = jsonObject.getString("pid");
            if (pid != null|| !"".equals(pid)) {
                DailyPatrol dp = dailypatrolService.selectById(pid);
                dp.setTagcount(dp.getTagcount() + 1);
                dailypatrolService.updateById(dp);
            }

             String uid = jsonObject.getString("uid");

            String pinfo = jsonObject.getString("info");
            String mmid = jsonObject.getString("mmid");
            PositionMark info = new PositionMark();
            info.setUid(uid);

            //处理上报时间
            JSONObject dateJson = JSONObject.fromObject(jsonObject.getString("createTime"));
            String time = dateJson.getString("time");
            long lt = new Long(time);
            Date date = new Date(lt);
            info.setCreateTime(date);

            info.setMmid(mmid);
            info.setName(jsonObject.getString("name"));
            info.setType(Integer.valueOf(jsonObject.getString("type")));
            info.setInfo(pinfo == null ? "" : pinfo);
            info.setLongitude(Double.valueOf(jsonObject.getString("longitude")));
            info.setLatitude(Double.valueOf(jsonObject.getString("latitude")));
            info.setInfoStatus(0);
            info.setPid(pid);
            info.setUuid(jsonObject.getString("uuid"));
            boolean a = markService.insert(info);
            System.out.println(info.toString());
            if (a == false) {
                this.OpLog("标记信息存储失败！", 1, uid);
                return renderError("标记信息存储失败！");
            }
            JSONArray arr =  jsonObject.getJSONArray("flist");
            for (Object object : arr) {
            	JSONObject js = JSONObject.fromObject(object);
            	SendFile entity = new SendFile();
            	entity.setFilename(js.getString("filename"));
            	entity.setFilesuffix(js.getString("filesuffix"));
            	entity.setFilesource(js.getInt("filesource"));
            	entity.setFileurl(js.getString("fileurl"));
            	entity.setFiletype(js.getInt("filetype"));
//            	entity.setKeycode(js.getInt("keycode"));
            	sendFileService.insert(entity);
            	File f= new File(entity.getFileurl());
            	new File(f.getParent()).mkdirs();
            	f.createNewFile();
			}
            //String date = new SimpleDateFormat("yyyy_MM").format(new Date());
            // 存储图片的物理路径
            String pic_path = configService.selectConfig("upload_url") + "/" + date;
//				String url =req.getRequestURL().toString().replace(req.getServletPath(), "/upload/"+date+"/");
            String url = req.getContextPath() + "/upload/" + date + "/";
//				for (MultipartFile multipartFile : multipartFiles) {
//					SendFile ifile = new SendFile();
//					// 文件的原始名称
//					String originalFilename = multipartFile.getOriginalFilename();
//					String newFileName = null;
//					if (multipartFile != null && originalFilename != null && originalFilename.length() > 0) {
//						newFileName = UUID.randomUUID() + originalFilename;
//
//						// 如果文件夹不存在则创建
//						File file = new File(pic_path);
//						if (!file.exists()) {
//							file.mkdirs();
//						}
//						// 新文件路径实例
//						File targetFile = new File(pic_path, newFileName);
//						// 内存数据读入磁盘
//						multipartFile.transferTo(targetFile);
//						String type = ContentTypeMaps.ContentType(targetFile);
//						ifile.setFileurl(url + newFileName);
//						ifile.setFilename(originalFilename);
//						ifile.setFilesuffix(originalFilename.substring(originalFilename.lastIndexOf(".") + 1));
//						ifile.setFiletype(StringUtils.getType(type));
//						ifile.setOwenid(info.getId());// 所属标记信息id
//						ifile.setFilesource(1);// 类型 1:标记信息文件
//						sendFileService.insert(ifile);
//					}
//				}

            this.OpLog("数据接收成功", 1, uid);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return renderSuccess("数据传输成功");
    }

    /**
     * 巡防轨迹信息保存
     * 接收由中心下发过来的数据
     *
     * @param
     * @param req
     * @param
     * @return
     * @throws SQLException
     */
    @RequestMapping("/exchangeRTposition")
    @ResponseBody
    @Transactional
    public Object exchangeRTposition(@RequestBody String requestBody, HttpServletRequest req) throws SQLException {

        try {
            System.out.println("exchangeRTposition接收：" + requestBody);
            JSONObject jsonObject = JSONObject.fromObject(requestBody);

            JSONObject patrolTrailJson = JSONObject.fromObject(jsonObject.getString("PatrolTrail"));
            JSONObject dailyPatrolJson = JSONObject.fromObject(jsonObject.getString("DailyPatrol"));


            EntityWrapper<DailyPatrol> wrapper = new EntityWrapper<>();
            wrapper.eq("uuid", dailyPatrolJson.getString("uuid"));
            DailyPatrol dp = dailypatrolService.selectOne(wrapper);

            if (dp.getEndTime() != null) {
                return renderError("本次巡防已结束！");
            }

            dp.setPatrolDistance(Double.valueOf(dailyPatrolJson.getString("patrolDistance")));
            dailypatrolService.updateById(dp);

            //处理时间
            JSONObject createTimeJson = JSONObject.fromObject(patrolTrailJson.getString("createTime"));
            String time = createTimeJson.getString("time");
            Date createTime = new Date(new Long(time));

            PatrolTrail pt = new PatrolTrail();
            pt.setCreateTime(createTime);
            pt.setLongitude(Double.valueOf(patrolTrailJson.getString("longitude")));
            pt.setLatitude(Double.valueOf(patrolTrailJson.getString("latitude")));
            pt.setPid(dp.getUuid());
            pt.setMovetype(Integer.valueOf(patrolTrailJson.getString("movetype")));
            pt.setSpeed(Double.valueOf(patrolTrailJson.getString("speed")));
            pt.setInterval(Double.valueOf(patrolTrailJson.getString("interval")));
            ptrailService.insert(pt);

            //this.OpLog("数据接收成功", 1, uid);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return renderSuccess("数据传输成功");
    }

    /**
     * 开始巡防--签到
     * 接收由中心下发过来的数据
     *
     * @param req
     * @return
     */
    @RequestMapping("/exchangePatrolOn")
    @ResponseBody
    @Transactional
    public Object exchangePatrolOn(@RequestBody String requestBody, HttpServletRequest req) throws SQLException {

        try {
            System.out.println("exchangePatrolOn接收：" + requestBody);
            JSONObject jsonObject = JSONObject.fromObject(requestBody);

            JSONObject patrolTrailJson = JSONObject.fromObject(jsonObject.getString("PatrolTrail"));
            JSONObject dailyPatrolJson = JSONObject.fromObject(jsonObject.getString("DailyPatrol"));


//            EntityWrapper<DailyPatrol> wrapper1 = new EntityWrapper<>();
//            wrapper1.eq("uuid", dailyPatrolJson.getString("uuid"));
//            wrapper1.where("end_time is null");
//            wrapper1.eq("route_id", dailyPatrolJson.getString("routeId"));
//            wrapper1.eq("end_status", 0);
//            DailyPatrol dp = dailypatrolService.selectOne(wrapper1);
//            if (dp != null) {
//                return renderError("您有未结束的巡防操作！请先结束再开始新的巡防");
//            }

            //处理时间
            JSONObject startTimeJson = JSONObject.fromObject(dailyPatrolJson.getString("startTime"));
            String startTime = startTimeJson.getString("time");
            Date signDate = new Date(new Long(startTime));

            DailyPatrol sign = new DailyPatrol();
            sign.setStartTime(signDate);
            sign.setRouteId(dailyPatrolJson.getString("routeId"));
            sign.setPatrolId(dailyPatrolJson.getString("patrolId"));
            sign.setPatrolDistance(0.0);
            sign.setReportcount(0);
            sign.setTagcount(0);
            sign.setEndStatus(0);
            sign.setUuid(dailyPatrolJson.getString("uuid"));
            boolean a = dailypatrolService.insert(sign);
            if (!a) {
                //this.OpLog("签到失败", 1, user.getId());
                return renderError("数据保存失败");
            }

            //处理时间
            JSONObject createTimeJson = JSONObject.fromObject(patrolTrailJson.getString("createTime"));
            String createTime = createTimeJson.getString("time");
            Date ptDate = new Date(new Long(createTime));

            PatrolTrail pt = new PatrolTrail();
            pt.setCreateTime(ptDate);
            pt.setLongitude(Double.valueOf(patrolTrailJson.getString("longitude")));
            pt.setLatitude(Double.valueOf(patrolTrailJson.getString("latitude")));
            pt.setPid(sign.getUuid());
            pt.setInterval(0.0);
            pt.setSpeed(0.0);
            pt.setMovetype(100);
            ptrailService.insert(pt);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return renderSuccess("数据传输成功");
    }

    /**
     * 结束巡防--签退
     * 接收由中心下发过来的数据
     *
     * @param
     * @param req
     * @param
     * @return
     * @throws SQLException
     */
    @RequestMapping("/exchangePatrolOff")
    @ResponseBody
    @Transactional
    public Object exchangePatrolOff(@RequestBody String requestBody, HttpServletRequest req) throws SQLException {

        try {
            System.out.println("exchangePatrolOff接收：" + requestBody);
            JSONObject jsonObject = JSONObject.fromObject(requestBody);

            JSONObject patrolTrailJson = JSONObject.fromObject(jsonObject.getString("PatrolTrail"));
            JSONObject dailyPatrolJson = JSONObject.fromObject(jsonObject.getString("DailyPatrol"));

            // 签退
            //DailyPatrol sign = dailypatrolService.selectById(dailyPatrolJson.getString("patrolId"));

            EntityWrapper<DailyPatrol> wrapper = new EntityWrapper<>();
            wrapper.eq("uuid", dailyPatrolJson.getString("uuid"));
            DailyPatrol sign = dailypatrolService.selectOne(wrapper);

            //处理时间
            JSONObject endTimeJson = JSONObject.fromObject(dailyPatrolJson.getString("endTime"));
            String endTime = endTimeJson.getString("time");
            Date signEndTime = new Date(new Long(endTime));

            Date d = new Date();
            sign.setEndTime(signEndTime);
            sign.setEndStatus(1);
            //int nowtime = StringUtils.getTime(sign.getStartTime(), signEndTime);
            sign.setTime(Integer.valueOf(dailyPatrolJson.getString("time")));
            sign.setPatrolDistance(Double.valueOf(dailyPatrolJson.getString("patrolDistance")));
            sign.setEndStatus(1);
            boolean a = dailypatrolService.updateById(sign);
            if (!a) {
                //this.OpLog("退签失败", 1, user.getId());
                return renderError("数据保存失败");
            }

            //处理时间
            JSONObject createTimeJson = JSONObject.fromObject(patrolTrailJson.getString("createTime"));
            String createTime = createTimeJson.getString("time");
            Date ptCreateTime = new Date(new Long(createTime));

            PatrolTrail pt = new PatrolTrail();
            pt.setCreateTime(ptCreateTime);
            pt.setLongitude(Double.valueOf(patrolTrailJson.getString("longitude")));
            pt.setLatitude(Double.valueOf(patrolTrailJson.getString("latitude")));
            pt.setPid(sign.getUuid());
            pt.setInterval(Double.valueOf(dailyPatrolJson.getString("patrolDistance")));
            pt.setSpeed(0.0);
            pt.setMovetype(101);
            ptrailService.insert(pt);

            //this.OpLog("数据接收成功", 1, uid);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return renderSuccess("数据传输成功");
    }

    /**
     * 多定位数据上传
     * 接收由中心下发过来的数据
     */
    @RequestMapping("exchangeTrailList")
    @ResponseBody
    @Transactional
    public Object exchangeTrailList(@RequestBody String requestBody, HttpServletRequest req) throws SQLException {
        try {
            System.out.println("exchangeTrailList接收：" + requestBody);
            JSONObject jsonObject = JSONObject.fromObject(requestBody);

            String json = jsonObject.getString("patrols");
            JSONArray arr = JSONArray.fromObject(json);
            for (Object j : arr) {
                JSONObject locate = JSONObject.fromObject(j);
                String uuid = locate.getString("uuid");
                Double longitude = locate.getDouble("longitude");
                Double latitude = locate.getDouble("latitude");
                PatrolTrail last = ptrailService.selectnew(uuid);
                Double i = DigestUtils.GetDistance(last.getLongitude(), last.getLatitude(),
                        longitude, latitude);

                EntityWrapper<DailyPatrol> wrapper = new EntityWrapper<DailyPatrol>();
                wrapper.eq("uuid", uuid);

                DailyPatrol dp = dailypatrolService.selectOne(wrapper);
                dp.setPatrolDistance(dp.getPatrolDistance() + i);
                dailypatrolService.update(dp,wrapper);

                PatrolTrail pt = new PatrolTrail();
                pt.setCreateTime(new Date(Long.valueOf(locate.get("datetime").toString())));
                pt.setLongitude(longitude);
                pt.setLatitude(latitude);
                pt.setPid(uuid);
                pt.setInterval(i);
                ptrailService.insert(pt);
            }
            //this.OpLog("多定位数据上传成功", 1, user.getId());

        } catch (Exception e) {
            e.printStackTrace();
            // this.ErrorLog(e, 1, user.getId());
            throw new RuntimeException();
        }
        return renderSuccess("数据传输成功");
    }


    /**
     * 追加报送信息
     * 接收由中心下发过来的数据
     *
     * @param req
     * @param
     * @return
     * @throws SQLException
     */
    @RequestMapping("/exchangeAddsendinfo")
    @ResponseBody
    @Transactional
    public Object exchangeAddsendinfo(@RequestBody String requestBody, HttpServletRequest req) throws SQLException {

        try {
            System.out.println("exchangeAddsendinfo接收：" + requestBody);
            JSONObject jsonObject = JSONObject.fromObject(requestBody);

            JSONObject sendMessageJson = JSONObject.fromObject(jsonObject.getString("SendMessage"));
            JSONObject sendInfoJson = JSONObject.fromObject(jsonObject.getString("SendInfo"));

            EntityWrapper<SendInfo> wrapper = new EntityWrapper<>();
            wrapper.eq("uuid", sendInfoJson.getString("uuid"));
            SendInfo info = sendInfoService.selectOne(wrapper);

            //处理时间   SendMessage的createTime属性  写错了：creatTime
            JSONObject createTimeJson = JSONObject.fromObject(sendMessageJson.getString("creatTime"));
            String createTime = createTimeJson.getString("time");
            Date smsgCreateTime = new Date(new Long(createTime));

            // 保存报送信息消息表
            SendMessage smsg = new SendMessage();
            smsg.setMsgInfo(sendMessageJson.getString("msgInfo"));
            smsg.setCreatTime(smsgCreateTime);
            smsg.setPid(info.getUuid());
            smsg.setType(1);
            boolean b = smsgService.insert(smsg);
            if (b == false) {
                return renderError("消息存储失败！");
            }
            JSONArray arr =  jsonObject.getJSONArray("flist");
            for (Object object : arr) {
            	JSONObject js = JSONObject.fromObject(object);
            	SendFile entity = new SendFile();
            	entity.setFilename(js.getString("filename"));
            	entity.setFilesuffix(js.getString("filesuffix"));
            	entity.setFilesource(js.getInt("filesource"));
            	entity.setFileurl(js.getString("fileurl"));
            	entity.setFiletype(js.getInt("filetype"));
//            	entity.setKeycode(js.getInt("keycode"));
            	sendFileService.insert(entity);
            	File f= new File(entity.getFileurl());
            	new File(f.getParent()).mkdirs();
            	f.createNewFile();
			}
            // 保存附件信息*s
            String date = new SimpleDateFormat("yyyy_MM").format(new Date());
            // 存储图片的物理路径
            String pic_path = configService.selectConfig("upload_url") + "/" + date;
//			String url =req.getRequestURL().toString().replace(req.getServletPath(), "/upload/"+date+"/");
            String url = req.getContextPath() + "/upload/" + date + "/";
//            for (MultipartFile multipartFile : multipartFiles) {
//                SendFile ifile = new SendFile();
//                // 文件的原始名称
//                String originalFilename = multipartFile.getOriginalFilename();
//                String newFileName = null;
//                if (multipartFile != null && originalFilename != null && originalFilename.length() > 0) {
//                    newFileName = UUID.randomUUID() + originalFilename;
//                    // 如果文件夹不存在则创建
//                    File file = new File(pic_path);
//                    if (!file.exists()) {
//                        file.mkdirs();
//                    }
//                    // 新文件路径实例
//                    File targetFile = new File(pic_path, newFileName);
//                    // 内存数据读入磁盘
//                    multipartFile.transferTo(targetFile);
//                    String type = ContentTypeMaps.ContentType(targetFile);
//                    ifile.setFileurl(url + newFileName);
//                    ifile.setFilename(originalFilename);
//                    ifile.setFilesuffix(originalFilename.substring(originalFilename.lastIndexOf(".") + 1));
//                    ifile.setOwenid(info.getId());
//                    ifile.setFiletype(StringUtils.getType(type));
//                    ifile.setMsgid(smsg.getId());
//                    ifile.setFilesource(2);
//                    sendFileService.insert(ifile);
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
            //this.ErrorLog(e, 1, user.getId());
            throw new RuntimeException();
        }
        //this.OpLog("添加报送信息", 1, user.getId());
        return renderSuccess("数据传输成功");
    }

    /**
     * 新增手机直播
     * 接收由中心下发过来的数据
     *
     * @return
     */
    @RequestMapping("/exchangeOnlineLive")
    @ResponseBody
    public Object exchangeOnlineLive(@RequestBody String requestBody, HttpServletRequest req) throws SQLException {

        String tcp = configService.selectConfig("video_tcp");
        String ip = configService.selectConfig("video_ip");
        String port = configService.selectConfig("video_port");
        String app = configService.selectConfig("video_app");


        try {
            System.out.println("exchangeOnlineLive接收：" + requestBody);
            JSONObject jsonObject = JSONObject.fromObject(requestBody);

            String lvid = jsonObject.getString("lvid");
            String option = jsonObject.getString("option");
            JSONObject videoJson = JSONObject.fromObject(jsonObject.getString("video"));


            EntityWrapper<LiveVideo> ew = new EntityWrapper<>();
            ew.eq("live_id", videoJson.getString("liveId"));
            ew.le("status", 1);
            LiveVideo video = iLiveVideoService.selectOne(ew);
            if (video != null) {
                if (video.getStatus() == 0) {
                    video.setStartTime(new Date());
                    video.setStatus(1);
                    video.setTimeMark(new Date().getTime());
                    iLiveVideoService.updateById(video);
                    return renderSuccess("数据传输成功");
                }
                return renderSuccess("数据传输成功");
            }

            if (lvid == null || lvid.equals("")) {
                //String url = tcp + "://" + ip + ":" + port + "/" + app + "/" + DigestUtils.getRandomString(8);
                LiveVideo lv = new LiveVideo();
                lv.setLiveId(videoJson.getString("uuid"));
                lv.setReceiveId(videoJson.getString("receiveId"));
                lv.setStartTime(new Date());
                lv.setCreateTime(new Date());
                lv.setType(1);
                lv.setUrl(videoJson.getString("url"));
                lv.setTimeMark(new Date().getTime());
                lv.setStatus(1);
                lv.setPatrolid(videoJson.getString("patrolid"));
                lv.insert();
                return renderSuccess("数据传输成功");
            } else {
                LiveVideo lv = iLiveVideoService.selectById(lvid);
                lv.setStartTime(new Date());
                lv.setStatus(1);
                lv.setTimeMark(new Date().getTime());
                iLiveVideoService.updateById(lv);
                return renderSuccess("数据传输成功");
            }
            //this.OpLog("手机直播", 1, user.getId());
            //return renderSuccess(j);

        } catch (Exception e) {
            e.printStackTrace();
            return renderError("系统异常:" + e);
        }
    }

    /**
     * 更改直播状态，直播结束
     * 接收由中心下发过来的数据
     * @return
     */
    @RequestMapping("/exchangeEndVideo")
    @ResponseBody
    public Object exchangeEndVideo(@RequestBody String requestBody, HttpServletRequest req) throws SQLException {

        System.out.println("exchangeEndVideo接收：" + requestBody);
        JSONObject jsonObject = JSONObject.fromObject(requestBody);


        LiveVideo lv = iLiveVideoService.selectById(jsonObject.getString("id"));
        lv.setStatus(2);
        lv.setEndTime(new Date());
        iLiveVideoService.updateById(lv);
        //this.OpLog("更改直播状态，直播结束", 1, user.getId());
        return renderSuccess("数据传输成功");
    }

    /**
     * 手机端修改密码
     * 接收由中心下发过来的数据
     * @return
     */
    @RequestMapping("/exchangeEditPassword")
    @ResponseBody
    public Object exchangeEditPassword(@RequestBody String requestBody, HttpServletRequest req) throws SQLException {

        System.out.println("exchangeEditPassword接收：" + requestBody);
        JSONObject jsonObject = JSONObject.fromObject(requestBody);

        User user = usrService.selectById(jsonObject.getString("id"));
        user.setPassword(jsonObject.getString("password"));
        user.setSalt(jsonObject.getString("salt"));
        usrService.updateById(user);
        this.OpLog("修改密码", 1, user.getId());
        return renderSuccess("数据传输成功");
    }

    /**
     * 手机端阅毕通知公告
     * 接收由中心下发过来的数据
     * @return
     */
    @RequestMapping("/exchangeSeen")
    @ResponseBody
    public Object exchangeSeen(@RequestBody String requestBody, HttpServletRequest req) throws SQLException {
        System.out.println("exchangeSeen接收：" + requestBody);
        JSONObject jsonObject = JSONObject.fromObject(requestBody);
        EntityWrapper<NoticeRead> ew = new EntityWrapper<NoticeRead>();
        ew.eq("nid", jsonObject.getString("nid"));
        ew.eq("uid", jsonObject.getString("uid"));
        NoticeRead n = inotService.selectOne(ew);
        if (n != null) {
            n.setStatus(1);
            n.updateById();
            //this.OpLog("读取消息成功", 1, user.getId());
        }
        return renderSuccess("数据传输成功");
    }

    @RequestMapping("/md5Validate")
    @ResponseBody
    public String md5Validate(String path){
		try {
			InputStream in = new FileInputStream(new File(path));
			String lmd5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(in);
			return lmd5;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
    }

}
