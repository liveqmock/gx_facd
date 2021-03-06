package com.wangzhixuan.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.wangzhixuan.commons.shiro.ShiroUser;
import com.wangzhixuan.model.DataDictionary;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sunbq
 * @since 2017-09-12
 */
public interface IDataDictionaryService extends IService<DataDictionary> {
	Object getType();
	List<DataDictionary> selectAll();

    List<DataDictionary> selectAllMenu();

    List<DataDictionary> selectAllTree();

    List<DataDictionary> selectTree(ShiroUser shiroUser);
}
