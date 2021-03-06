package com.wangzhixuan.service;

import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.service.IService;
import com.wangzhixuan.commons.result.PageInfo;
import com.wangzhixuan.model.Answer;
import com.wangzhixuan.model.Subject;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 题库表 服务类
 * </p>
 *
 * @author sunbq
 * @since 2017-08-22
 */
public interface ISubjectService extends IService<Subject> {
    List<Subject> selectByRandom(Map<String, Object> params);
    
    List<Subject> selectPage(Map<String, Object> params);
	void selectDataGrid(PageInfo pageInfo);
	
	boolean insertAnswer(Subject subject,Answer answer1,HttpServletRequest req);
	
	boolean updataAnswer(Subject subject,HttpServletRequest req);
}
