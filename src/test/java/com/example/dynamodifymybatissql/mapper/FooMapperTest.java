package com.example.dynamodifymybatissql.mapper;

import org.apache.ibatis.binding.MapperProxy;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

/**
 * Created by zhuguowei on 5/14/17.
 */
@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FooMapperTest {
    @Autowired
    private FooMapper fooMapper;
    @Autowired
    private SqlSession sqlSession;

    private final String id = "com.example.dynamodifymybatissql.mapper.FooMapper.getTime";

    @Test
    public void getSqlSessionReflectly() throws Exception {
//        System.out.println(fooMapper.getClass()); //com.sun.proxy.$Proxy76
        Field hField = fooMapper.getClass().getDeclaredField("h");
        System.out.println(hField);

        MapperProxy mapperProxy = (MapperProxy) hField.get(fooMapper);
        Field sqlSessionField = mapperProxy.getClass().getDeclaredField("sqlSession");

        SqlSession sqlSession = (SqlSession) sqlSessionField.get(mapperProxy);
        System.out.println(sqlSession);
    }

    @Test
    public void testGetSql() throws Exception {

        String sql = getSql(id);

        assertEquals("select now()", sql);

        String newSql = "select current_timestamp()";
        updateSql(id, newSql);

        assertEquals(newSql,getSql(id));

    }

    private String getSql(String id) throws NoSuchFieldException, IllegalAccessException {
        StaticSqlSource sqlSource = getStaticSqlSource(id);
        Field sqlField = sqlSource.getClass().getDeclaredField("sql");
        sqlField.setAccessible(true);
        return (String) sqlField.get(sqlSource);
    }

    private StaticSqlSource getStaticSqlSource(String id) throws NoSuchFieldException, IllegalAccessException {
        MappedStatement mappedStatement = sqlSession.getConfiguration().getMappedStatement(id);
        RawSqlSource rawSqlSource = (RawSqlSource) mappedStatement.getSqlSource();
        Field sqlSourceField = rawSqlSource.getClass().getDeclaredField("sqlSource");
        sqlSourceField.setAccessible(true);
        return (StaticSqlSource) sqlSourceField.get(rawSqlSource);
    }

    private void updateSql(String id,String newSql) throws NoSuchFieldException, IllegalAccessException {
        StaticSqlSource sqlSource = getStaticSqlSource(id);
        Field sqlField = sqlSource.getClass().getDeclaredField("sql");
        sqlField.setAccessible(true);
        sqlField.set(sqlSource,newSql);
    }


    @Test
    public void getTime() throws Exception {

        System.out.println(fooMapper.getTime());
        System.out.println();
        // same sql and in the same transaction query directly from cache
        System.out.println(fooMapper.getTime());
        System.out.println();
        // update sql
        String newSql = "select 'hello'";
        updateSql(id,newSql);

        assertEquals("hello",fooMapper.getTime());


    }

}