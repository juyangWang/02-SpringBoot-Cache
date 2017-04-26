package com.liwei.web;

import com.liwei.entity.Person;
import com.liwei.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2016/12/11.
 */
@RestController
public class PersonController {
    @Autowired
    private IPersonService personService;

    // 测试 URL
    // http://localhost:8080/put?address=北京&name=liweiwei&age=24
    // http://localhost:8080/put?address=上海&name=zhouguang&age=26

    /**
     * 插入或者更新
     *
     * @CachePut 注解，执行完 Service 方法，然后更新缓存
     *
     * @param person
     * @return
     */
    @RequestMapping("/put")
    public Person put(Person person){
        return personService.save(person);
    }

    // 测试 URL
    // http://localhost:8080/getFromCache?id=1
    /**
     * `@Cacheable` 在方法执行之前 Spring 先查看缓存中是否有数据，
     * 如果有数据，则直接返回缓存数据；
     * 如果没有数据，调用方法并将返回值放进缓存
     *
     * @param person
     * @return
     */
    @RequestMapping("/getFromCache")
    public Person getFromCache(Person person){
        Person person1 = personService.findOne(person);
        return person1;
    }

    // http://localhost:8080/remove_cache?id=9

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping("/remove_cache")
    public String removeCache(Long id){
        personService.remove(id);
        return "ok";
    }
}
