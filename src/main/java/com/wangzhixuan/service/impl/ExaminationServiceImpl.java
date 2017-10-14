package com.wangzhixuan.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wangzhixuan.commons.result.PageInfo;
import com.wangzhixuan.mapper.ExaminationMapper;
import com.wangzhixuan.mapper.ScoreMapper;
import com.wangzhixuan.mapper.TestPaperMapper;
import com.wangzhixuan.model.Examination;
import com.wangzhixuan.model.Score;
import com.wangzhixuan.model.TestPaper;
import com.wangzhixuan.model.User;
import com.wangzhixuan.service.IExaminationService;

/**
 * <p>
 * 考试信息 服务实现类
 * </p>
 *
 * @author sunbq
 * @since 2017-08-22
 */
@Service
public class ExaminationServiceImpl extends ServiceImpl<ExaminationMapper, Examination> implements IExaminationService {

	@Autowired
	private ExaminationMapper examinationMapper;
	
	  @Autowired
	   private ScoreMapper scoreMapper;;
	  
	  @Autowired
	   private TestPaperMapper testPaperMapper;
	
	@Override
	public void selectDataGrid(PageInfo pageInfo) {
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageInfo.getNowpage(), pageInfo.getSize());
        page.setOrderByField(pageInfo.getSort());
        page.setAsc(pageInfo.getOrder().equalsIgnoreCase("asc"));
        List<Map<String, Object>> list = examinationMapper.selectExaminationPage(page, pageInfo.getCondition());
        pageInfo.setRows(list);
        pageInfo.setTotal(page.getTotal());
		
	}

	@Override
	public boolean insertExamination(Examination examination,String ids,String uids) {
		try{
			 examinationMapper.insertAndGetId(examination);
				
			 String[] a = ids.split(",");
		   TestPaper t = new TestPaper();
		 for (int i=0;i<a.length;i++) {
			 if(Integer.parseInt(a[i])!=0){
				 t.setSubjectId(Integer.parseInt(a[i]));
			    	t.setExaminationId(examination.getId());
			    	testPaperMapper.insert(t);
			 }
		    	
			}
		 String[] b = uids.split(",");
		 Score s = new Score();
		 for (int i=0;i<b.length;i++) {
			 if(Integer.parseInt(b[i])!=0){
				s.setExaminationId(examination.getId());
				s.setUserId(Integer.parseInt(b[i]));
				scoreMapper.insert(s);
			 }
			}
		    
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	
	}

	@Override
	public boolean deleteTestPaper(Examination examination, String da, String ids) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<User> selectExaminationUser(Map<String, Object> map) {
		
		return examinationMapper.selectExaminationUser(map);
	}
	
}
