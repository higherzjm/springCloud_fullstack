package com.allen.springcloud.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
	@Autowired
	private PersonDao personDao;

	/**
	 * spring JpaRepository 实现的接口方法
	 */

	//查询所有
	public List<Person> springJpa_queryAllPersons() {return personDao.findAll();}

	//按主键查询指定model
	public Person springJpa_getASSignPerson(Integer id) {return personDao.findOne(id);}

	//添加指定model
	public Person springJpa_savePerson(String name){
		Person person=personDao.save(new Person(name));
		return person;
	}

	//按主键删除指定model
	public String springJpa_deleteAssignPerson(int id) {
		try {
			Person person=personDao.findOne(id);
			personDao.delete(person);
			return "删除成功";
		}catch (Exception e){
			e.printStackTrace();
			return "删除失败";
		}

	}

	/**
	 * javax PersistenceContext 实现的接口方法
	 */
	public List<Person> javax_queryAllPersons() {
		return personDao.javax_queryAllPersons();
	}

	public Person javax_getAssisgnPersonBySql(String name) {
		return personDao.javax_getAssisgnPersonBySql(name);
	}
	public Person javax_getAssisgnPersonById(Integer id) {
		return personDao.javax_getAssisgnPersonById(id);
	}
	public String javax_deleteAssisgnPerson(String name) {
		return personDao.javax_deleteAssisgnPerson(name);
	}
	public Person javax_saveAssisgnPerson(Integer id, String name) {return personDao.javax_saveAssisgnPerson(id,name);
	}


}
