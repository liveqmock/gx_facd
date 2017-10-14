package com.wangzhixuan.service;

import com.wangzhixuan.model.ItemFile;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 条目附件表 服务类
 * </p>
 *
 * @author sunbq
 * @since 2017-08-22
 */
public interface IItemFileService extends IService<ItemFile> {
	ItemFile selectItemFile(int id);
	ItemFile selectByOwnerid(int id);
}
