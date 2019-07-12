package com.allen.springcloud.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

public class PersonDaoImpl implements PersonDaoCustom {
	public PersonDaoImpl() {
		/**
		 * 约定大于配置，会实例化的原因是存在
		 * 前缀名称为PersonDao且继承JpaRepository的接口
		 */
		System.out.println("实例化PersonDaoImpl");
	}

	@PersistenceContext  //声明是一个持久上下文
	private EntityManager em;

	public List<Person> javax_queryAllPersons() {
		Query q = em.createQuery("from Person");
		return q.getResultList();
	}

	public Person javax_getAssisgnPersonBySql(String name) {
		Query q =em.createQuery("from Person p  where p.name='"+name+"'");
		if (q.getResultList().size()==0){
			return null;
		}else{
			return (Person) q.getResultList().get(0);
		}
	}

	public Person javax_getAssisgnPersonById(Integer id) {
		Person person=em.find(Person.class,id);
		return person;

	}
	@Transactional //删除必须添加上该事物注解
	public String javax_deleteAssisgnPerson(String name) {
		List<Person> persons=null;
		try {
			Query q =em.createQuery("from Person p  where p.name='"+name+"'");
			if (q.getResultList().size()==0){
				return "无此用户";
			}else{
				persons=q.getResultList();
			}
			for (Person person:persons){
				em.remove(person);
			}
			return "删除成功";
		}catch (Exception e) {
			e.printStackTrace();
			return "删除失败";
		}

	}
	@Transactional //增删改必须加上事物注解
	public Person javax_saveAssisgnPerson(Integer id, String name) {
		Person person=null;
		if (id==0){
			person=new Person(name);//新增,主键数据库设置为默认递增
		}else {
			person=new Person(id,name);//修改
		}
		em.merge(person);
		return person;
	}
}
