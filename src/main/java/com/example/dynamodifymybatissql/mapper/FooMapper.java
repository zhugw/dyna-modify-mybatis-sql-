package com.example.dynamodifymybatissql.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by zhuguowei on 5/14/17.
 */
@Mapper
public interface FooMapper {
    @Select("select now()")
    String getTime();
}
