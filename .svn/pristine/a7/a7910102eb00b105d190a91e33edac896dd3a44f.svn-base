package com.wangzhixuan.service.impl;

import com.wangzhixuan.model.SubjectCollection;
import com.wangzhixuan.mapper.SubjectCollectionMapper;
import com.wangzhixuan.service.ISubjectCollectionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 试题收藏列表 服务实现类
 * </p>
 *
 * @author sunbq
 * @since 2017-08-22
 */
@Service
public class SubjectCollectionServiceImpl extends ServiceImpl<SubjectCollectionMapper, SubjectCollection> implements ISubjectCollectionService {

    @Autowired
    private SubjectCollectionMapper subjectCollectionMapper;

    @Override
    public List<Map<String, Object>> selectFavoriteByUserid(Map<String, Object> params) {
        return subjectCollectionMapper.selectFavoriteByUserid(params);
    }
}
