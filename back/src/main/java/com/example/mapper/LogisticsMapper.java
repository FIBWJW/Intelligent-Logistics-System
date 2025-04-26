package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bean.Logistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface LogisticsMapper extends BaseMapper<Logistics> {
    @Select("SELECT * FROM logistics WHERE order_id = #{orderId}")
    Logistics selectByOrderId(Long orderId);
}
