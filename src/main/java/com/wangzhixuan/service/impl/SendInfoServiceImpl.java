package com.wangzhixuan.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wangzhixuan.commons.result.PageInfo;
import com.wangzhixuan.mapper.SendInfoMapper;
import com.wangzhixuan.model.SendInfo;
import com.wangzhixuan.service.ISendInfoService;

/**
 * <p>
 * 报送信息 服务实现类
 * </p>
 *
 * @author sunbq
 * @since 2017-05-23
 */
@Service
public class SendInfoServiceImpl extends ServiceImpl<SendInfoMapper, SendInfo> implements ISendInfoService {

	@Autowired
	private SendInfoMapper sendInfoMapper;
	
	@Override
	public void selectDataGrid(PageInfo pageInfo) {
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageInfo.getNowpage(), pageInfo.getSize());
        page.setOrderByField(pageInfo.getSort());
        page.setAsc(pageInfo.getOrder().equalsIgnoreCase("asc"));
        List<Map<String, Object>> list = sendInfoMapper.selectSendInfoPage(page, pageInfo.getCondition());
        pageInfo.setRows(list);
        pageInfo.setTotal(page.getTotal());
		
	}

	@Override
	public List<Map<String, Object>> getReportList(Map<String, Object> params) {
		
		return sendInfoMapper.selectReportList(params);
	}

	@Override
	public List<Map<String, Object>> getReportList2(Map<String, Object> params) {
		return sendInfoMapper.selectReportList2(params);
	}

	@Override
	public int getReportList2Count(Map<String, Object> params) {
		return sendInfoMapper.selectReportList2Count(params);
	}

	@Override
	public List<SendInfo> selectExcle(Map<String, Object> condition ) {
		List<SendInfo> list = sendInfoMapper.selectExcle(condition);
		return list;
	}

	@Override
	public SendInfo selectByUUid(String uuid) {
		EntityWrapper<SendInfo> en = new EntityWrapper<>();
		en.eq("uuid", uuid);
		return this.selectOne(en);
	}


	

}
