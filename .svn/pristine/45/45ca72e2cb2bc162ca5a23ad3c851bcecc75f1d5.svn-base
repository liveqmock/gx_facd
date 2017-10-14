package com.wangzhixuan.service.impl;

import com.wangzhixuan.model.Answer;
import com.wangzhixuan.mapper.AnswerMapper;
import com.wangzhixuan.service.IAnswerService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 答案表 服务实现类
 * </p>
 *
 * @author sunbq
 * @since 2017-08-22
 */
@Service
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements IAnswerService {

	@Autowired
	private AnswerMapper answerMapper;
	
	@Override
	public List<Answer> selectAnswer(int id) {
		
		return answerMapper.selectAnswer(id);
	}

	@Override
	public void deleteAnswer(int id) {
		answerMapper.delectAnswer(id);
		
	}
	
}
