package com.liwei.service;

import com.liwei.CacheApplication;
import com.liwei.entity.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by liwei on 17/4/26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CacheApplication.class)
public class IPersonServiceTest {


    @Autowired
    private IPersonService personService;

    /**
     * CachePut 还有更新缓存的作用
     *
     * 测试方法：
     * 1、使用 flushdb 清空 Redis 缓存
     * 2、执行本单元测试
     * 3、使用 keys * 查看缓存是否生成
     * 4、查看数据库看看是否有数据
     *
     */
    @Test
    public void testCachePut(){
        Person person = new Person();
        person.setId(3L);
        person.setName("zhouguang");
        person.setAddress("SJTU");
        person.setAge(24);
        personService.save(person);
    }

    /**
     * 测试方法：
     * 1、执行一遍单元测试方法，查询数据库中已经有的 id 的数据，此时看到控制台有 SQL 语句
     * 说明这次的单元测试方法执行过程中，缓存没有，所以到数据库里查询。
     * 数据库查询完成以后，Spring 的缓存框架就把数据库返回的信息放到缓存中。
     * 所以使用 keys * 查看 Redis ，会看到 Redis 中的数据
     * 2、再执行一次查询方法，看到控制台不会有 SQL 语句输出
     */
    @Test
    public void testCacheAble(){
        Person person = new Person();
        person.setId(3L);
        Person result = personService.findOne(person);
        System.out.println(result);
    }

    @Test
    public void savePersonCondition(){
        Person person = new Person();
        person.setName("李威");
        person.setAge(5);
        person.setAddress("中国人民大学");
        Person result = personService.saveYoungPeople(person);
        System.out.println(result);

    }


}