package com.wangzhixuan.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.wangzhixuan.model.DailyPatrol;

/**
 * <p>
  * 巡防（考勤表） Mapper 接口
 * </p>
 *
 * @author sunbq
 * @since 2017-06-05
 */
public interface DailyPatrolMapper extends BaseMapper<DailyPatrol> {
	
		List<Map<String, Object>> selectbyPage(Pagination page, Map<String, Object> params);
		
		List<Map<String, Object>> selectrpList(@Param("year") Integer year,@Param("uid") String uid);
}