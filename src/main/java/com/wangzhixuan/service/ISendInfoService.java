package com.wangzhixuan.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.wangzhixuan.commons.result.PageInfo;
import com.wangzhixuan.model.SendInfo;

/**
 * <p>
 * 报送信息 服务类
 * </p>
 *
 * @author sunbq
 * @since 2017-05-23
 */
public interface ISendInfoService extends IService<SendInfo> {
	
	void selectDataGrid(PageInfo pageInfo);
	
	List<SendInfo> selectExcle(Map<String, Object> condition );
	
	public List<Map<String, Object>> getReportList(Map<String, Object> params);
	
	public List<Map<String, Object>> getReportList2(Map<String, Object> params);
	
	public int getReportList2Count(Map<String, Object> params);

	SendInfo selectByUUid(String uuid);
	
}
