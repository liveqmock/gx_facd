package com.wangzhixuan.service;

import com.wangzhixuan.commons.result.PageInfo;
import com.wangzhixuan.model.OperateLog;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 操作日志表 服务类
 * </p>
 *
 * @author sunbq
 * @since 2017-07-06
 */
public interface IOperateLogService extends IService<OperateLog> {
	  void selectDataGrid(PageInfo pageInfo);
	  /**
	   * 
	   * @param msg 操作日志内容	
	   * @param i  操作平台0-pc后台 1-移动端 2-TV端
	   * @param userid  用户id
	   * @param flag  操作成功-失败
	   */
	  void saveLog(String msg,int i,Long userid,boolean flag);
	  
	 
}
