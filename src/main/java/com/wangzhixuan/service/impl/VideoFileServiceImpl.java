package com.wangzhixuan.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wangzhixuan.commons.result.PageInfo;
import com.wangzhixuan.mapper.VideoFileMapper;
import com.wangzhixuan.model.VideoFile;
import com.wangzhixuan.service.IVideoFileService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sunbq
 * @since 2017-08-07
 */
@Service
public class VideoFileServiceImpl extends ServiceImpl<VideoFileMapper, VideoFile> implements IVideoFileService {

	@Autowired
	private  VideoFileMapper videoFileMapper;
	@Override
	public void selectDataGrid(PageInfo pageInfo) {
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageInfo.getNowpage(), pageInfo.getSize());
        List<Map<String, Object>> list = videoFileMapper.selectvoidFilePage(page, pageInfo.getCondition());
        pageInfo.setRows(list);
        pageInfo.setTotal(page.getTotal());
	}
	@Override
	public VideoFile selectByInputValue(String fileName) {
		Map<String, Object> m=new HashMap<String, Object>();
		if(fileName!=null&&fileName!="") {
			m.put("fileName", fileName);
		}
		return videoFileMapper.selectByInputValue(m);
	}
	@Override
	public VideoFile selectByUUid(String uuid) {
		EntityWrapper<VideoFile> en = new EntityWrapper<>();
		en.eq("uuid", uuid);
		return this.selectOne(en);
	}
	
}
