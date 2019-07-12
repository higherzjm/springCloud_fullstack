package com.allen.springcloud.jpa;

import java.util.List;

public interface PersonDaoCustom {

	List<Person> javax_queryAllPersons();
	Person javax_getAssisgnPersonBySql(String name);
	String javax_deleteAssisgnPerson(String name);
	Person javax_saveAssisgnPerson(Integer id, String name);
	Person javax_getAssisgnPersonById(Integer id);

}
