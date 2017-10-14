package com.wangzhixuan.service;

import com.wangzhixuan.commons.result.PageInfo;
import com.wangzhixuan.model.BorderKnowledge;
import com.wangzhixuan.model.ItemFile;
import com.wangzhixuan.model.KnowledgeType;

import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 边防知识表 服务类
 * </p>
 *
 * @author sunbq
 * @since 2017-08-22
 */
public interface IBorderKnowledgeService extends IService<BorderKnowledge> {
	void selectDataGrid(PageInfo pageInfo);
	
	boolean insertThree(BorderKnowledge borderKnowledge,KnowledgeType knowledgeType,ItemFile ItemFile, HttpServletRequest req); 
	
	boolean updateThree(BorderKnowledge borderKnowledge,KnowledgeType knowledgeType,int kid,ItemFile ItemFile);
	
}
