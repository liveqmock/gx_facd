package com.wangzhixuan.task;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wangzhixuan.commons.base.BaseController;
import com.wangzhixuan.commons.utils.StringUtils;
import com.wangzhixuan.model.DailyPatrol;
import com.wangzhixuan.model.LiveVideo;
import com.wangzhixuan.model.PatrolTrail;
import com.wangzhixuan.service.IDailyPatrolService;
import com.wangzhixuan.service.ILiveVideoService;
import com.wangzhixuan.service.IPatrolTrailService;
import com.wangzhixuan.service.ISysConfigService;

/**
 *  视频回传的定时结束
 * @author sunbq
 */
@Component
public class TestTask extends BaseController{
	protected Logger logger = LogManager.getLogger(getClass());
	
	@Autowired private ILiveVideoService iLiveVideoService;
	@Autowired private IDailyPatrolService dailypatrolService;
	@Autowired private ISysConfigService configService;
	@Autowired private IPatrolTrailService ptrailService;
	@Scheduled(cron = "0 0/20 * * * *")
	public void cronTest() {
		try {
			String DpIds = null;String LvIds = null;
			String abnormal_end = configService.selectConfig("abnormal_end");
			long tm = Long.valueOf(abnormal_end) * 1000 * 60 * 60; 
			long nt = new Date().getTime();
			EntityWrapper<DailyPatrol> wrapper = new EntityWrapper<>();
			wrapper.le("start_time", new Date(nt-tm));
			wrapper.eq("end_status", 0);
			List<DailyPatrol> dplist = dailypatrolService.selectList(wrapper);
			for (DailyPatrol dp : dplist) {
				Date d = new Date();
				dp.setEndStatus(2);
				dp.setEndTime(d);
				int time = StringUtils.getTime(dp.getStartTime(), d);
				dp.setTime(time);
				dp.setEndStatus(1);
				dp.updateById();
				if (DpIds != null) 
					DpIds += ","+dp.getUuid();
				else
					DpIds = dp.getUuid();
			}
			if (DpIds != null) {
				OpLog("结束巡防:"+DpIds, 0, 0l);
			}
			String configH = configService.selectConfig("abnormal_live");
			long H = Long.valueOf(configH);
			Date dt = new Date();
			long t = dt.getTime()-3600000 * H;
			EntityWrapper<LiveVideo> ew = new EntityWrapper<LiveVideo>();
			ew.eq("status", 1);
			ew.lt("start_time", new Date(t));
			ew.where("time_mark is not null ");
			List<LiveVideo> list = iLiveVideoService.selectList(ew);
			for (LiveVideo liveVideo : list) {
				PatrolTrail pt = ptrailService.selectnew(liveVideo.getPatrolid());
				if (pt != null&&pt.getCreateTime().getTime() < t) {
					liveVideo.setEndTime(dt);
					liveVideo.setTimeMark(dt.getTime());
					liveVideo.setStatus(3);
					iLiveVideoService.updateById(liveVideo);
					if (LvIds != null) 
						LvIds += ","+liveVideo.getUuid();
					else
						LvIds = liveVideo.getUuid();
				}
			}
			if (LvIds != null) {
				OpLog("结束视频:"+LvIds, 0, 0l);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
