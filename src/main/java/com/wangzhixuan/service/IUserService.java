package com.wangzhixuan.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.wangzhixuan.commons.result.PageInfo;
import com.wangzhixuan.model.User;
import com.wangzhixuan.model.vo.UserVo;

/**
 *
 * User 表数据服务层接口
 *
 */
public interface IUserService extends IService<User> {

    List<User> selectByLoginName(UserVo userVo);
    
    User selectBytoken(String token);
    
    User selectByTV(String token);

    void insertByVo(UserVo userVo);

    UserVo selectVoById(Long id);

    UserVo selectVoByUUID(String uuid);
    
    void updateByVo(UserVo userVo);

    boolean updateByUUID(User user);

    void updatePwdByUserId(Long userId, String md5Hex);

    void selectDataGrid(PageInfo pageInfo);

    void deleteUserById(Long id);

	User selectByUUid(String uuid);
}