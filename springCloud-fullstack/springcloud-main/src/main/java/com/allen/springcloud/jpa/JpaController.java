package com.allen.springcloud.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class JpaController {
	
	@Autowired
	private PersonService personService;

	//如下是spring JpaRepository 数据库的操作 begin

	/**
	 * localhost:9000/springJpa_queryAllPersons
	 * @return 查询所有person
	 */
	@RequestMapping(value = "/springJpa_queryAllPersons", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Person> springJpa_queryAllPersons() {
		return personService.springJpa_queryAllPersons();
	}

	/**
	 * localhost:9000/springJpa_getASSignPerson/15
	 * @return 查询指定id 的person
	 */
	@RequestMapping(value = "/springJpa_getASSignPerson/{id}", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Person springJpa_getASSignPerson(@PathVariable int id) {
		return personService.springJpa_getASSignPerson(id);
	}

	/**
	 * localhost:9000/springJpa_savePerson/王五
	 * @return 添加指定person
	 */
	@RequestMapping(value = "/springJpa_savePerson/{name}", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Person springJpa_savePerson(@PathVariable String name) {
		return personService.springJpa_savePerson(name);
	}

	/**
	 * localhost:9000/springJpa_deleteAssignPerson/1
	 * @return 删除指定id 的person
	 */
	@RequestMapping(value = "/springJpa_deleteAssignPerson/{id}", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String springJpa_deleteAssignPerson(@PathVariable int id) {
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
		String time=simpleDateFormat.format(new Date());
		return time+":"+personService.springJpa_deleteAssignPerson(id);
	}
    //-------------end-----------------


	//如下是javax PersistenceContext 数据库的操作 begin
	/**
	 * localhost:9000/javax_queryAllPersons
	 * 查询所有person
	 * @return
	 */
	@RequestMapping(value = "/javax_queryAllPersons", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Person> javax_queryAllPersons() {
		return personService.javax_queryAllPersons();
	}

	/**
	 * localhost:9000/javax_getAssisgnPersonBySql/张三3
	 * 通过用户名查询指定person
	 * @return
	 */
	@RequestMapping(value = "/javax_getAssisgnPersonBySql/{name}", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Person javax_getAssisgnPersonBySql(@PathVariable String name) {
		return personService.javax_getAssisgnPersonBySql(name);
	}

	/**
	 * localhost:9000/javax_getAssisgnPersonById/1
	 * 通过id查询指定person
	 * @return
	 */
	@RequestMapping(value = "/javax_getAssisgnPersonById/{id}", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Person javax_getAssisgnPersonById(@PathVariable Integer id) {
		return personService.javax_getAssisgnPersonById(id);
	}
	/**
	 * localhost:9000/javax_deleteAssisgnPerson/涂13
	 * 删除指定person
	 * @return
	 */
	@RequestMapping(value = "/javax_deleteAssisgnPerson/{name}", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String javax_deleteAssisgnPerson(@PathVariable String name) {
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
		String time=simpleDateFormat.format(new Date());
		return time+":"+personService.javax_deleteAssisgnPerson(name);
	}
	/**
	 * localhost:9000/javax_saveAssisgnPerson/11/张三3
	 * 添加或修改指定person：当id参数值为0表示添加，不为0则为修改
	 * @return
	 */
	@RequestMapping(value = "/javax_saveAssisgnPerson/{id}/{name}", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Person javax_saveAssisgnPerson(@PathVariable Integer id, @PathVariable String name) {
		return personService.javax_saveAssisgnPerson(id,name);
	}
	//-------------------------end-------
}
