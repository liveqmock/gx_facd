package com.wangzhixuan.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wangzhixuan.commons.result.PageInfo;
import com.wangzhixuan.mapper.OperateLogMapper;
import com.wangzhixuan.model.OperateLog;
import com.wangzhixuan.service.IOperateLogService;

/**
 * <p>
 * 操作日志表 服务实现类
 * </p>
 *
 * @author sunbq
 * @since 2017-07-06
 */
@Service
public class OperateLogServiceImpl extends ServiceImpl<OperateLogMapper, OperateLog> implements IOperateLogService {

	@Autowired
	private OperateLogMapper logMapper;
	/**
	 * 操作日志条件查询实现
	 */
	@Override
	public void selectDataGrid(PageInfo pageInfo) {
		Page<OperateLog> page = new Page<OperateLog>(pageInfo.getNowpage(), pageInfo.getSize());
		List<Map<String,Object>> list = logMapper.selectOperateLogPage(page, pageInfo.getCondition());
        pageInfo.setRows(list);
        pageInfo.setTotal(page.getTotal());
		
	}

	@Override
	public void saveLog(String msg,int i,Long userid,boolean flag) {
		OperateLog l = new OperateLog();
		l.setMsg(msg);
		l.setTerrace(i);
		l.setType(flag==false?"error":"log");
		l.setUserid(userid);
		logMapper.insert(l);
	}


	
}
