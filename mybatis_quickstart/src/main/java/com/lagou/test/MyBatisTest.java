package com.lagou.test;

import com.lagou.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class MyBatisTest {

    @Test
    public void mybatisQuickStart() throws IOException {
        //加载配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
//        获取sqlSessionFactoy接口对象
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(resourceAsStream);
//        获取sqlSession会话对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
//        执行sql参数 statementid：namespace.id
        List<User> users = sqlSession.selectList("userMapper.findAll");
        for (User user : users) {
            System.out.println(user);
        }
        sqlSession.close();


    }

    /*测试新增用户*/
    @Test
    public void testSave() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        User user = new User();
        user.setUsername("自动提交事物");
        user.setBirthday(new Date());
        user.setSex("男");
        user.setAddress("北京海淀");
        sqlSession.insert("userMapper.saveUser", user);
        /*需要手动提交事物*/
       /* sqlSession.commit();*/
        sqlSession.close();


    }

@Test
    public void testUpdate() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(4);
        user.setUsername("lucy");
        user.setBirthday(new Date());
        user.setSex("女");
        user.setAddress("北京海淀");

        sqlSession.update("userMapper.updateUser",user);
        /*需要手动提交事物*/
        sqlSession.commit();
        sqlSession.close();

    }

    @Test
    public void testDelete() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        sqlSession.delete("userMapper.deleteUser",4);
        /*需要手动提交事物*/
        sqlSession.commit();
        sqlSession.close();

    }
}
