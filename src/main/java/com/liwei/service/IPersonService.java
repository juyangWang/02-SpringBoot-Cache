package com.liwei.service;

import com.liwei.entity.Person;

/**
 * Created by Administrator on 2016/12/11.
 */
public interface IPersonService {
    Person save(Person person);

    void remove(Long id);

    Person findOne(Person person);

    /**
     * 测试带有条件的缓存
     * @param id
     * @return
     */
    Person saveYoungPeople(Person person);

}
