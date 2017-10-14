package com.wangzhixuan.mapper;

import com.wangzhixuan.model.ItemFile;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  * 条目附件表 Mapper 接口
 * </p>
 *
 * @author sunbq
 * @since 2017-08-22
 */
public interface ItemFileMapper extends BaseMapper<ItemFile> {
	
	
	ItemFile selectItemFile(@Param("id")int id);
	ItemFile selectByOwnerid(int id);
	
}