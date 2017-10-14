package com.wangzhixuan.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.wangzhixuan.commons.result.TreeCode;
import com.wangzhixuan.model.Organization;

/**
 *
 * Organization 表数据服务层接口
 *
 */
public interface IOrganizationService extends IService<Organization> {

    List<TreeCode> selectTree();
    List<TreeCode> selectTree2();

    List<Organization> selectTreeGrid();
    List<String> selectOrganizationDePid(String receiver);
    List<Organization> selectListByCode(String code,Integer isSystem);
}