package com.wangzhixuan.mapper;

import com.wangzhixuan.model.OperateLog;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * <p>
  * 操作日志表 Mapper 接口
 * </p>
 *
 * @author sunbq
 * @since 2017-07-06
 */
public interface OperateLogMapper extends BaseMapper<OperateLog> {
	List<Map<String ,Object>> selectOperateLogPage(Pagination page, Map<String, Object> params);
}