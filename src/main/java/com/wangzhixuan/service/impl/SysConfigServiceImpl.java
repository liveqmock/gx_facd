package com.wangzhixuan.service.impl;

import com.wangzhixuan.model.SysConfig;
import com.wangzhixuan.mapper.SysConfigMapper;
import com.wangzhixuan.service.ISysConfigService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 配置信息表 服务实现类
 * </p>
 *
 * @author sunbq
 * @since 2017-07-07
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements ISysConfigService {

	@Override
	public String selectConfig(String key) {
		EntityWrapper<SysConfig> ew = new EntityWrapper<>();
		ew.eq("selkey", key);
		SysConfig sys = this.selectOne(ew);
		return sys==null?null:sys.getValue();
	}
	
}
