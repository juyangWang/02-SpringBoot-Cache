package com.liwei.dao;

import com.liwei.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2016/12/11.
 */
public interface PersonRepository extends JpaRepository<Person,Long> {
}
