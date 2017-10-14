package com.wangzhixuan.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wangzhixuan.model.Organization;

/**
 *
 * Organization 表数据库控制层接口
 *
 */
public interface OrganizationMapper extends BaseMapper<Organization> {
		public List<String> selectOrganizationDePid(String receiver);
		public List<Organization> selectListByCode(String code,Integer isSystem);
}