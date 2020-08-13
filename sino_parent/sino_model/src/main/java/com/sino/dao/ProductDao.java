package com.sino.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sino.vo.Product;

@Mapper
public interface ProductDao  extends BaseMapper<Product>{
	
	public List<?> queryList(@Param("keywords")String keywords , @Param("ctgId")int ctgId) ;
}
