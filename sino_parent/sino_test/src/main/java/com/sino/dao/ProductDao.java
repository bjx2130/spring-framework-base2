package com.sino.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sino.vo.Product;

@Mapper
public interface ProductDao  extends BaseMapper<Product>{

}
