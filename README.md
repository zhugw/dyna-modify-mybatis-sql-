# dyna-modify-mybatis-sql-
Dynamically modify mybatis sql 

# Background

Recently I had a interview and interviewer asked me how to dynamically update some sql without restart server

# Implementation

After track source code, I found the sql is stored in `org.apache.ibatis.session.Configuration#mappedStatements`

So could update sql dynamically using reflection. 

```
MappedStatement mappedStatement = sqlSession.getConfiguration().getMappedStatement(id);
RawSqlSource rawSqlSource = (RawSqlSource) mappedStatement.getSqlSource();
Field sqlSourceField = rawSqlSource.getClass().getDeclaredField("sqlSource");
sqlSourceField.setAccessible(true);
StaticSqlSource sqlSource = (StaticSqlSource) sqlSourceField.get(rawSqlSource);
Field sqlField = sqlSource.getClass().getDeclaredField("sql");
sqlField.setAccessible(true);
sqlField.set(sqlSource,newSql);
```


