package com.wangzhixuan.controller.phone;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wangzhixuan.commons.base.BaseController;
import com.wangzhixuan.commons.result.PageInfo;
import com.wangzhixuan.model.Answer;
import com.wangzhixuan.model.BorderKnowledge;
import com.wangzhixuan.model.Examination;
import com.wangzhixuan.model.Score;
import com.wangzhixuan.model.Subject;
import com.wangzhixuan.model.SubjectCollection;
import com.wangzhixuan.model.TestDetail;
import com.wangzhixuan.model.TestPaper;
import com.wangzhixuan.model.User;
import com.wangzhixuan.service.IAnswerService;
import com.wangzhixuan.service.IBorderKnowledgeService;
import com.wangzhixuan.service.IExaminationService;
import com.wangzhixuan.service.IScoreService;
import com.wangzhixuan.service.ISubjectCollectionService;
import com.wangzhixuan.service.ISubjectService;
import com.wangzhixuan.service.ITestDetailService;
import com.wangzhixuan.service.ITestPaperService;
import com.wangzhixuan.service.IUserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/phoneExamination")
public class PhoneExaminationController extends BaseController {

	@Autowired
	private IUserService usrService;
	@Autowired
	private ISubjectService subjectService;
	@Autowired
	private IAnswerService answerService;
	@Autowired
	private ISubjectCollectionService scService;
	@Autowired
	private IExaminationService examinationService;
	@Autowired
	private IAnswerService aservice;
	@Autowired
	private IScoreService sService;
	@Autowired
	private ITestPaperService tpService;
	@Autowired
	private ITestDetailService itdSevice;
	@Autowired
	private IBorderKnowledgeService ibService;

	/**
	 * 随机获取练习列表
	 *
	 * @param req
	 * @param
	 * @param token
	 * @return
	 */
	@RequestMapping("/randomList")
	@ResponseBody
	public Object sendlist(HttpServletRequest req, String token) {
		User user = usrService.selectBytoken(token);
		try {
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("num", 10);

			List<Subject> subjects = subjectService.selectByRandom(map);
			JSONArray arr = new JSONArray();
			EntityWrapper<SubjectCollection> se = new EntityWrapper<SubjectCollection>();
			JSONObject json = new JSONObject();
			for (Subject l : subjects) {
				se.eq("subject_id", l.getId());
				se.eq("userId", user.getId());
				Map<String, Object> maps = scService.selectMap(se);
				if (maps != null) {
					json.put("status", true);
				} else {
					json.put("states", true);
				}
				Subject subject = l;
				int subjectsid = subject.getId();
				json.put("id", subjectsid);
				json.put("title", subject.getTitle());
				json.put("level", subject.getLevel());
				EntityWrapper<Answer> wrapper = new EntityWrapper<Answer>();
				wrapper.eq("subject_id", subjectsid);
				List<Answer> answers = answerService.selectList(wrapper);
				json.put("answer", answers);
				arr.add(json);
			}
			return renderSuccess(arr);

		} catch (Exception e) {
			e.printStackTrace();
			// this.ErrorLog(e, 1, user.getId());
			return renderError("系统异常");
		}
	}

	/**
	 * 收藏试题
	 *
	 * @param req
	 * @param id
	 * @param token
	 * @return
	 */
	@RequestMapping("/addFavorite")
	@ResponseBody
	public Object addFavorite(HttpServletRequest req, Integer id, String token) {
		User user = usrService.selectBytoken(token);
		if (user == null) {
			return renderError("用户登录已过期!");
		}
		SubjectCollection sc = new SubjectCollection();
		EntityWrapper<SubjectCollection> se = new EntityWrapper<SubjectCollection>();
		se.eq("subject_id", id);
		se.eq("userId", user.getId());
		Map<String, Object> map = scService.selectMap(se);
		if (map == null) {
			sc.setUserId(user.getId().intValue());
			sc.setSubjectId(id);
			sc.setCreateTime(new Date());
			boolean result = scService.insert(sc);
			if (result) {
				return renderSuccess(sc);
			} else {
				return renderError("收藏失败");
			}
		} else {
			return renderError("已收藏");
		}
	}

	/**
	 * 取消收藏试题
	 *
	 * @param req
	 * @param id
	 * @param token
	 * @return
	 */
	@RequestMapping("/delFavorite")
	@ResponseBody
	public Object delFavorite(HttpServletRequest req, Integer id, String token) {
		User user = usrService.selectBytoken(token);
		if (user == null) {
			return renderError("用户登录已过期!");
		}
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userid", user.getId());
			map.put("subject_id", id);
			boolean result = scService.deleteByMap(map);
			if (result) {
				return renderSuccess("删除成功");
			} else {
				return renderError("删除失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// this.ErrorLog(e, 1, user.getId());
			return renderError("系统异常");
		}
	}

	/**
	 * 获取收藏试题列表
	 *
	 * @param req
	 * @param
	 * @param token
	 * @return
	 */
	@RequestMapping("/getFavoriteList")
	@ResponseBody
	public Object getFavoriteList(HttpServletRequest req, String token, Integer pageLimit, Integer pageIndex) {
		User user = usrService.selectBytoken(token);
		if (user == null) {
			return renderError("用户登录已过期!");
		}
		EntityWrapper<SubjectCollection> ew = new EntityWrapper<SubjectCollection>();
		ew.eq("userId", user.getId());
		PageInfo pageInfo = new PageInfo(pageIndex, pageLimit, "createTime", "desc");
		Page<SubjectCollection> pages = new Page<SubjectCollection>(pageInfo.getNowpage(), pageInfo.getSize());
		pages = scService.selectPage(pages, ew);
		JSONArray arr = new JSONArray();
		for (SubjectCollection list : pages.getRecords()) {
			EntityWrapper<Subject> es = new EntityWrapper<Subject>();
			es.setSqlSelect("id,title");
			es.eq("id", list.getSubjectId());
			Map<String, Object> maps = subjectService.selectMap(es);
			EntityWrapper<Answer> ee = new EntityWrapper<Answer>();
			ee.setSqlSelect("answerContent");
			ee.eq("subject_id", list.getSubjectId());
			List<Map<String, Object>> selectMaps = aservice.selectMaps(ee);
			maps.put("answer", selectMaps);
			arr.add(maps);
		}
		return renderSuccess(arr);
	}

	/**
	 * 获取考试信息列表
	 *
	 * @param req
	 * @param
	 * @param token
	 * @return
	 */
	@RequestMapping("/getExaminationList")
	@ResponseBody
	public Object getExaminationList(HttpServletRequest req, String token) {
		User user = usrService.selectBytoken(token);
		if (user == null) {
			return renderError("用户登录已过期!");
		}
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			List<Examination> examinations = examinationService.selectByMap(map);
			JSONArray arr = new JSONArray();
			JSONObject json = new JSONObject();
			for (int i = 0; i < examinations.size(); i++) {
				Examination examination = examinations.get(i);
				json.put("id", examination.getId() + "");
				json.put("title", examination.getTitle());
				json.put("info", examination.getInfo());
				json.put("begintime", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(examination.getBeginTime()));
				json.put("endtime", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(examination.getEndTime()));
				arr.add(json);

			}
			// JSONArray jsonArray = JSONArray.fromObject(examinations);
			return renderSuccess(arr);

		} catch (Exception e) {
			e.printStackTrace();
			// this.ErrorLog(e, 1, user.getId());
			return renderError("系统异常");
		}

	}

	/**
	 * 开始考试
	 *
	 * @param req
	 * @param
	 * @param token
	 * @return
	 */
	@RequestMapping("/getStartexamination")
	@ResponseBody
	public Object startText(HttpServletRequest req, String token, Integer id) {
		int ids = 1;
		User user = usrService.selectBytoken(token);
		if (user == null) {
			return renderError("用户登录已过期!");
		}
		JSONArray arr = new JSONArray();
		EntityWrapper<Score> ex = new EntityWrapper<Score>();
		ex.eq("userId", user.getId());
		Score select = sService.selectOne(ex);
		if (select.getScore() == 0) {
			EntityWrapper<TestPaper> es = new EntityWrapper<TestPaper>();
			es.eq("examination_id", id);
			List<TestPaper> slist = tpService.selectList(es);

			for (TestPaper i : slist) {
				EntityWrapper<Subject> ee = new EntityWrapper<Subject>();
				ee.setSqlSelect("title,id");
				ee.eq("id", i.getSubjectId());
				Map<String, Object> selectMap = subjectService.selectMap(ee);
				List<Subject> selectList = subjectService.selectList(ee);
				for (Subject info : selectList) {
					EntityWrapper<Answer> an = new EntityWrapper<Answer>();
					an.setSqlSelect("answerContent,isAnswer,id");
					an.eq("subject_id", info.getId());
					List<Map<String, Object>> selectMapss = aservice.selectMaps(an);
					selectMap.put("answer", selectMapss);
					selectMap.put("ids", ids);
					arr.add(selectMap);
				}
				ids = ids + 1;
			}
		}
		return renderSuccess(arr);
	}

	/**
	 * 考试分数
	 *
	 * @param req
	 * @param
	 * @param token
	 * @return
	 */
	@RequestMapping("/setExaminationInfo")
	@ResponseBody
	public void startText(HttpServletRequest req, Score score, String token, Integer examinationId) {
		User user = usrService.selectBytoken(token);
		// Score scores = new Score();
		// scores.setUserId(user.getId().intValue());
		// scores.setExaminationId(examinationId);
		// scores.setScore(score);
		EntityWrapper<Score> es = new EntityWrapper<Score>();
		es.eq("userId", user.getId());
		es.eq("examination_id", examinationId);
		sService.update(score, es);
	}

	/**
	 * 考试分数
	 *
	 * @param req
	 * @param
	 * @param token
	 * @return
	 */
	@RequestMapping("/setExaminationRightWrong")
	@ResponseBody
	public void startRightWrong(HttpServletRequest req, String token, Integer examinationId, Integer subjectId,
			Integer choose) {
		User user = usrService.selectBytoken(token);
		TestDetail testDetail = new TestDetail();
		testDetail.setUserId(user.getId().intValue());
		testDetail.setExaminationId(examinationId);
		testDetail.setSubjectId(subjectId);
		testDetail.setChoose(choose);
		itdSevice.insert(testDetail);
	}

	/**
	 * 边防知识历史查询
	 *
	 * @param req
	 * @param
	 * @param token
	 * @return
	 */
	@RequestMapping("/getCategoryHistoryInfo")
	@ResponseBody
	public Object getExaninationhistory(HttpServletRequest req, String token, Integer id) {
		User user = usrService.selectBytoken(token);
		if (user == null) {
			return renderError("用户登录已过期!");
		}
		EntityWrapper<BorderKnowledge> ex = new EntityWrapper<BorderKnowledge>();
		ex.eq("typeId", id);
		List<Map<String, Object>> selectMaps = ibService.selectMaps(ex);
		return renderSuccess(selectMaps);
	}

	/**
	 * 获取考试信息状态
	 *
	 * @param req
	 * @param
	 * @param token
	 * @return
	 */
	@RequestMapping("/getExaminationStatus")
	@ResponseBody
	public Object getExaminationStatus(HttpServletRequest req, String token, Integer examinationId) {
		User user = usrService.selectBytoken(token);
		if (user == null) {
			return renderError("用户登录已过期!");
		}

		EntityWrapper<Score> ex = new EntityWrapper<Score>();
		ex.eq("userId", user.getId());
		ex.eq("examination_id", examinationId);
		List<Score> list = sService.selectList(ex);
		JSONArray arr = new JSONArray();
		for (Score l : list) {
			if (l.getScore() == 0) {
				arr.add("false");
			} else {

				arr.add("true");
			}
		}
		return renderSuccess(arr);
	}

	/**
	 * 获取收藏详细页
	 *
	 * @param req
	 * @param
	 * @param token
	 * @return
	 */
	@RequestMapping("/getFavoriteListInfo")
	@ResponseBody
	public Object getExaminationInfo(HttpServletRequest req, String token, Integer id) {
		User user = usrService.selectBytoken(token);
		if (user == null) {
			return renderError("用户登录已过期!");
		}
		JSONArray arr = new JSONArray();
		EntityWrapper<Subject> ew = new EntityWrapper<Subject>();
		ew.eq("id", id);
		Subject list = subjectService.selectOne(ew);
		Map<String, Object> map = subjectService.selectMap(ew);
		EntityWrapper<Answer> ee = new EntityWrapper<Answer>();
		ee.setSqlSelect("answerContent");
		ee.eq("subject_id", list.getId());
		List<Map<String, Object>> selectMaps = aservice.selectMaps(ee);
		map.put("answer", selectMaps);
		arr.add(map);
		return renderSuccess(arr);
	}
}
