package com.wangzhixuan.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wangzhixuan.commons.shiro.ShiroUser;
import com.wangzhixuan.mapper.DataDictionaryMapper;
import com.wangzhixuan.mapper.DataTypeMapper;
import com.wangzhixuan.model.DataDictionary;
import com.wangzhixuan.model.DataType;
import com.wangzhixuan.service.IDataDictionaryService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sunbq
 * @since 2017-09-12
 */
@Service
public class DataDictionaryServiceImpl extends ServiceImpl<DataDictionaryMapper, DataDictionary> implements IDataDictionaryService {
    
	@Autowired
	private DataTypeMapper dataTypeMapper;
	
	@Autowired
	private DataDictionaryMapper dataDictionaryMapper;
	
	
	@Override
	public Object getType() {
		EntityWrapper<DataType> ew=new EntityWrapper<DataType>();
		List<DataType> list = dataTypeMapper.selectList(ew);
		JSONArray jsonArray = new JSONArray();
		List<List<DataDictionary>> list2 = new ArrayList<List<DataDictionary>>();
		for(int i=0; i<list.size(); i++) {
			JSONObject jsonDataType = new JSONObject();
			jsonDataType.put("id", list.get(i).getId());
			jsonDataType.put("name", list.get(i).getName());
			jsonDataType.put("code", list.get(i).getCode());
			jsonDataType.put("code", list.get(i).getRemake());
			jsonDataType.put("pid", "null");
			jsonArray.add(jsonDataType);
			EntityWrapper<DataDictionary> wrapper = new EntityWrapper<DataDictionary>();
			if(list.get(i).getName()!=null) {
				wrapper.eq("pid", list.get(i).getId());
			}
			List<DataDictionary> dataList = dataDictionaryMapper.selectList(wrapper);
			list2.add(dataList);
		}
		JSONObject jsonDataDataDictionary = new JSONObject();
		for(int j=0;j<list2.size();j++) {
			List<DataDictionary> myList = (List<DataDictionary>) list2.get(j);
			for(int k=0;k<myList.size();k++ ) {
				jsonDataDataDictionary.put("id", myList.get(k).getId());
				jsonDataDataDictionary.put("name", myList.get(k).getName());
				jsonDataDataDictionary.put("pid", myList.get(k).getPid());
				jsonArray.add(jsonDataDataDictionary);
			}
		}
		return jsonArray;
	}


	@Override
	public List<DataDictionary> selectAll() {
		EntityWrapper<DataDictionary> wrapper = new EntityWrapper<DataDictionary>();
        wrapper.orderBy("seq");
		return dataDictionaryMapper.selectList(wrapper);
	}


	@Override
	public List<DataDictionary> selectAllMenu() {
	        // 查询所有菜单
	     EntityWrapper<DataDictionary> ew = new EntityWrapper<DataDictionary>();
	     ew.isNull("pid");
	      List<DataDictionary> dataDic=  dataDictionaryMapper.selectList(ew);
	      
	        if (dataDic == null) {
	            return null;
	        }
	        for (DataDictionary dataDictionary : dataDic) {
	        	dataDictionary.setText(dataDictionary.getName());
			}
	        return dataDic;
	}


	@Override
	public List<DataDictionary> selectAllTree() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<DataDictionary> selectTree(ShiroUser shiroUser) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
