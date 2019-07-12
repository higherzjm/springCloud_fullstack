package com.allen.springcloud.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * spring JpaRepository会为PersonDao生成代理类，默认使用jdk代理，service可以直接注入
 * PersonDaoCustom为项目另外自定义的接口，需要有实现类
 */
public interface PersonDao extends JpaRepository<Person, Integer>,
        PersonDaoCustom{

}
