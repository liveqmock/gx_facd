package com.wangzhixuan.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wangzhixuan.commons.result.PageInfo;
import com.wangzhixuan.mapper.AnswerMapper;
import com.wangzhixuan.mapper.SubjectMapper;
import com.wangzhixuan.model.Answer;
import com.wangzhixuan.model.Subject;
import com.wangzhixuan.service.ISubjectService;

/**
 * <p>
 * 题库表 服务实现类
 * </p>
 *
 * @author sunbq
 * @since 2017-08-22
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements ISubjectService {

    @Autowired
    private SubjectMapper subjectMapper;
  

    @Override
    public List<Subject> selectByRandom(Map<String, Object> params) {
        return subjectMapper.selectByRandom(params);
    }

	@Autowired
	private AnswerMapper answerMapper;
	
	@Override
	public void selectDataGrid(PageInfo pageInfo) {
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageInfo.getNowpage(), pageInfo.getSize());
        page.setOrderByField(pageInfo.getSort());
        page.setAsc(pageInfo.getOrder().equalsIgnoreCase("asc"));
        List<Map<String, Object>> list = subjectMapper.selectSubjectPage(page, pageInfo.getCondition());
        pageInfo.setRows(list);
        pageInfo.setTotal(page.getTotal());
	}

	@Override
	public boolean insertAnswer(Subject subject, Answer answer1,HttpServletRequest req) {
		String level =  req.getParameter("level");
		String title =  req.getParameter("title");
		String analysis = req.getParameter("analysis");
	
		String answerContent =  req.getParameter("answerContent");
		String isAnswer =  req.getParameter("isAnswer");
		
		String answerContent2 =  req.getParameter("answerContent2");
		String isAnswer2 =  req.getParameter("isAnswer2");
		
		String answerContent3 =  req.getParameter("answerContent3");
		String isAnswer3 =  req.getParameter("isAnswer3");
		
		String answerContent4 =  req.getParameter("answerContent4");
		String isAnswer4 =  req.getParameter("isAnswer4");
		
		String answerContent5 =  req.getParameter("answerContent5");
		String isAnswer5 =  req.getParameter("isAnswer5");
		
		String answerContent6 =  req.getParameter("answerContent6");
		String isAnswer6 =  req.getParameter("isAnswer6");
	
		
				
		
		
		subject.setLevel(Integer.parseInt(level));
		subject.setTitle(title);
		subject.setAnalysis(analysis);
		subjectMapper.insertAndGetId(subject);
		
		
		answer1.setCreateTime(new Date());
		answer1.setSeq(1);
		answer1.setIsAnswer(Integer.parseInt(isAnswer));
		answer1.setSubjectId(subject.getId());
		answer1.setAnswerContent(answerContent);
		answerMapper.insert(answer1);
		
		Answer answer2 = new Answer();
		Answer answer3 = new Answer();
		Answer answer4 = new Answer();
		Answer answer5 = new Answer();
		Answer answer6 = new Answer();
		
		answer2.setCreateTime(new Date());
		answer2.setSeq(2);
		answer2.setIsAnswer(Integer.parseInt(isAnswer2));
		answer2.setSubjectId(subject.getId());
		answer2.setAnswerContent(answerContent2);
		answerMapper.insert(answer2);
		
		if(isAnswer3!=null&&!isAnswer3.equals("")&&answerContent3!=null&&!answerContent3.equals("")&&Integer.parseInt(isAnswer3)!=2){
			answer3.setCreateTime(new Date());
			answer3.setSeq(3);
			answer3.setIsAnswer(Integer.parseInt(isAnswer3));
			answer3.setSubjectId(subject.getId());
			answer3.setAnswerContent(answerContent3);
			answerMapper.insert(answer3);
		}
		
		if(isAnswer4!=null&&!isAnswer4.equals("")&&answerContent4!=null&&!answerContent4.equals("")&&Integer.parseInt(isAnswer4)!=2){
			answer4.setCreateTime(new Date());
			answer4.setSeq(4);
			answer4.setIsAnswer(Integer.parseInt(isAnswer4));
			answer4.setSubjectId(subject.getId());
			answer4.setAnswerContent(answerContent4);
			answerMapper.insert(answer4);
		}
		
		if(isAnswer5!=null&&!isAnswer5.equals("")&&answerContent5!=null&&!answerContent5.equals("")&&Integer.parseInt(isAnswer5)!=2){
			answer5.setCreateTime(new Date());
			answer5.setSeq(5);
			answer5.setIsAnswer(Integer.parseInt(isAnswer5));
			answer5.setSubjectId(subject.getId());
			answer5.setAnswerContent(answerContent5);
			answerMapper.insert(answer5);
		}
		

		if(isAnswer6!=null&&!isAnswer6.equals("")&&answerContent6!=null&&!answerContent6.equals("")&&Integer.parseInt(isAnswer6)!=2){
			answer6.setCreateTime(new Date());
			answer6.setSeq(6);
			answer6.setIsAnswer(Integer.parseInt(isAnswer6));
			answer6.setSubjectId(subject.getId());
			answer6.setAnswerContent(answerContent6);
			answerMapper.insert(answer6);
		}
		
		
		return true;
	}

	@Override
	public boolean updataAnswer(Subject subject, HttpServletRequest req) {
	
		String level =  req.getParameter("level");
		String title =  req.getParameter("title");
		String analysis = req.getParameter("analysis");
	
		String id1= req.getParameter("id1");
		String answerContent =  req.getParameter("answerContent1");
		String isAnswer =  req.getParameter("isAnswer1");
		
		String id2= req.getParameter("id2");
		String answerContent2 =  req.getParameter("answerContent2");
		String isAnswer2 =  req.getParameter("isAnswer2");
		
		String id3= req.getParameter("id3");
		String answerContent3 =  req.getParameter("answerContent3");
		String isAnswer3 =  req.getParameter("isAnswer3");
		
		String id4= req.getParameter("id4");
		String answerContent4 =  req.getParameter("answerContent4");
		String isAnswer4 =  req.getParameter("isAnswer4");
		
		String id5= req.getParameter("id5");
		String answerContent5 =  req.getParameter("answerContent5");
		String isAnswer5 =  req.getParameter("isAnswer5");
		
		String id6= req.getParameter("id6");
		String answerContent6 =  req.getParameter("answerContent6");
		String isAnswer6 =  req.getParameter("isAnswer6");
		
		
		Subject s =subjectMapper.selectById(subject.getId());
		s.setTitle(title);
		s.setLevel(Integer.parseInt(level));
		s.setAnalysis(analysis);
		subjectMapper.updateById(s);
		
		Answer a1 = answerMapper.selectById(id1);
		Answer a2 = answerMapper.selectById(id2);
		Answer a3 = answerMapper.selectById(id3);
		Answer a4 = answerMapper.selectById(id4);
		Answer a5 = answerMapper.selectById(id5);
		Answer a6 = answerMapper.selectById(id6);
		
		a1.setIsAnswer(Integer.parseInt(isAnswer));
		a1.setAnswerContent(answerContent);
		answerMapper.updateById(a1);
		
		a2.setIsAnswer(Integer.parseInt(isAnswer2));
		a2.setAnswerContent(answerContent2);
		answerMapper.updateById(a2);
		
		
		if(a3!=null&&!a3.equals("")){
			a3.setIsAnswer(Integer.parseInt(isAnswer3));
			a3.setAnswerContent(answerContent3);
			answerMapper.updateById(a3);
			
		}
		
		if(a4!=null&&!a4.equals("")){
			a4.setIsAnswer(Integer.parseInt(isAnswer4));
			a4.setAnswerContent(answerContent4);
			answerMapper.updateById(a4);
		}
		
		if(a5!=null&&!a5.equals("")){
			a5.setIsAnswer(Integer.parseInt(isAnswer5));
			a5.setAnswerContent(answerContent5);
			answerMapper.updateById(a5);
		}
		
		if(a6!=null&&!a6.equals("")){
			a6.setIsAnswer(Integer.parseInt(isAnswer6));
			a6.setAnswerContent(answerContent6);
			answerMapper.updateById(a6);
		}
	
		
		return true;
	}

	@Override
	public List<Subject> selectPage(Map<String, Object> params) {
		
		return subjectMapper.selectPage(params);
	}
	
}
