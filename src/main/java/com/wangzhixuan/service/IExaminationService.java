package com.wangzhixuan.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.wangzhixuan.commons.result.PageInfo;
import com.wangzhixuan.model.Examination;
import com.wangzhixuan.model.User;

/**
 * <p>
 * 考试信息 服务类
 * </p>
 *
 * @author sunbq
 * @since 2017-08-22
 */
public interface IExaminationService extends IService<Examination> {
	
	void selectDataGrid(PageInfo pageInfo);
	
	boolean insertExamination(Examination examination,String ids,String uids);
	
	boolean deleteTestPaper(Examination examination,String da,String ids);
	
	List<User> selectExaminationUser(Map<String,Object> map);
}
