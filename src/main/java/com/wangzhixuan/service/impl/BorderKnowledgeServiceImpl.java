package com.wangzhixuan.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wangzhixuan.commons.result.PageInfo;
import com.wangzhixuan.mapper.BorderKnowledgeMapper;
import com.wangzhixuan.mapper.ItemFileMapper;
import com.wangzhixuan.model.BorderKnowledge;
import com.wangzhixuan.model.ItemFile;
import com.wangzhixuan.model.KnowledgeType;
import com.wangzhixuan.service.IBorderKnowledgeService;

/**
 * <p>
 * 边防知识表 服务实现类
 * </p>
 *
 * @author sunbq
 * @since 2017-08-22
 */
@Service
public class BorderKnowledgeServiceImpl extends ServiceImpl<BorderKnowledgeMapper, BorderKnowledge> implements IBorderKnowledgeService {

	@Autowired
	private BorderKnowledgeMapper borderKnowledgeMapper;
	
	@Autowired
	private ItemFileMapper itemFileMapper;
	
	
	@Override
	public void selectDataGrid(PageInfo pageInfo) {
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageInfo.getNowpage(), pageInfo.getSize());
        page.setOrderByField(pageInfo.getSort());
        page.setAsc(pageInfo.getOrder().equalsIgnoreCase("asc"));
        List<Map<String, Object>> list = borderKnowledgeMapper.selectBorderKnowledgePage(page, pageInfo.getCondition());
        pageInfo.setRows(list);
        pageInfo.setTotal(page.getTotal());
	}

	@Override
	public boolean insertThree(BorderKnowledge borderKnowledge, KnowledgeType knowledgeType, ItemFile ItemFile,
			HttpServletRequest req) {
		
		
		
		borderKnowledge.setStatus(1);
		borderKnowledge.setCreateTime(new Date());
		borderKnowledge.setTypeId(knowledgeType.getId());
		borderKnowledge.setContent(req.getParameter("content"));
		
		borderKnowledgeMapper.insertAndGetId(borderKnowledge);
		
		ItemFile.setOwnerid(borderKnowledge.getId());
		ItemFile.setType(1);
		itemFileMapper.insert(ItemFile);
		
		return true;
	}

	@Override
	public boolean updateThree(BorderKnowledge borderKnowledge, KnowledgeType knowledgeType, int kid,
			ItemFile ItemFile) {
		
		BorderKnowledge bk = borderKnowledgeMapper.selectById(borderKnowledge.getId());
		bk.setContent(HtmlUtils.htmlUnescape(borderKnowledge.getContent()));
		bk.setTitle(borderKnowledge.getTitle());
		bk.setTypeId(borderKnowledge.getTypeId());
		 borderKnowledgeMapper.updateById(bk);
		
		
		if(ItemFile!=null){
			itemFileMapper.updateById(ItemFile);	
		}
		
		return true;
	}

	
}
