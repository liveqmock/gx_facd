package com.wangzhixuan.mapper;

import com.wangzhixuan.model.VideoFile;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author sunbq
 * @since 2017-08-07
 */
public interface VideoFileMapper extends BaseMapper<VideoFile> {
	List<Map<String, Object>> selectvoidFilePage(Pagination page, Map<String, Object> params);
    VideoFile selectByInputValue(Map<String, Object> params);
}