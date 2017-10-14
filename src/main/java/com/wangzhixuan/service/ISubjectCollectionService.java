package com.wangzhixuan.service;

import com.wangzhixuan.model.SubjectCollection;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 试题收藏列表 服务类
 * </p>
 *
 * @author sunbq
 * @since 2017-08-22
 */
public interface ISubjectCollectionService extends IService<SubjectCollection> {
    List<Map<String, Object>> selectFavoriteByUserid(Map<String, Object> params);
}
