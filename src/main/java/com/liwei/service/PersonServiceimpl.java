package com.liwei.service;

import com.liwei.dao.PersonRepository;
import com.liwei.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/12/11.
 */
@Service
public class PersonServiceimpl implements IPersonService {
    @Autowired
    private PersonRepository personRepository;

    /**
     * 无论如何都将方法的返回值设置到缓存中。
     * value 是缓存的名称，key 是 “=” 符号指定的，key 对应的 value 是方法的返回值
     * <p>
     * 如果这样配置 key = "#person.id" 控制台会报类型转换异常，使用 Spring EL 可以解决这个问题
     *
     * @param person
     * @return
     */
    @Override
    @CachePut(value = "people", key = "T(java.lang.String).valueOf(#person.id)")
    public Person save(Person person) {
        Person person1 = personRepository.save(person);
        System.out.println("缓存了 id 为 " + person1.getId() + " 的数据");
        // 返回的 person 对象含有自增长的主键 id 的值
        return person1;
    }

    /**
     * `@CacheEvict` 将一条或者多条数据从缓存中删除
     *
     * @param id
     */
    @Override
    @CacheEvict(value = "people")
    public void remove(Long id) {
        System.out.println("删除了 " + id + " 的缓存");
        // 这里为了做测试，不做实际的删除操作
        personRepository.delete(id);
    }


    /**
     * `@Cacheable` 在方法执行之前 Spring 先查看缓存中是否有数据，
     * 如果有数据，则直接返回缓存数据；
     * 如果没有数据，调用方法并将返回值放进缓存
     *
     * @param person
     * @return
     */
    @Override
    @Cacheable(value = "people", key = "T(java.lang.String).valueOf(#person.id)")
    public Person findOne(Person person) {
        System.out.println("执行了查询方法");
        return personRepository.findOne(person.getId());
    }

    /**
     * condition ="#person.age gt 10"  表示 age 小于 10 的才缓存
     * @param id
     * @return
     */
    @Override
    @CachePut(value = "people",key = "T(java.lang.String).valueOf(#person.id)",condition ="#person.age gt 10" )
    public Person saveYoungPeople(Person person) {
        return personRepository.save(person);
    }
}
