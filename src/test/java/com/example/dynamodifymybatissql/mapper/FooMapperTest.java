package com.example.dynamodifymybatissql.mapper;

import org.apache.ibatis.binding.MapperProxy;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;

/**
 * Created by zhuguowei on 5/14/17.
 */
@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FooMapperTest {
    @Autowired
    private FooMapper fooMapper;
    @Test
    public void getSqlSession() throws Exception {
//        System.out.println(fooMapper.getClass()); //com.sun.proxy.$Proxy76
        Field hField = fooMapper.getClass().getDeclaredField("h");
        System.out.println(hField);

        MapperProxy mapperProxy = (MapperProxy) hField.get(fooMapper);
        Field sqlSessionField = mapperProxy.getClass().getDeclaredField("sqlSession");

        SqlSession sqlSession = (SqlSession) sqlSessionField.get(mapperProxy);
        System.out.println(sqlSession);
    }
    @Test
    public void getTime() throws Exception {

        System.out.println(fooMapper.getTime());
        System.out.println();
        System.out.println(fooMapper.getTime());


    }

}