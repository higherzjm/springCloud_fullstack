package com.allen.springcloud.feign;

public class Police {

	private Integer id;
	private String name;
	private String message;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Police{" +
				"id=" + id +
				", name='" + name + '\'' +
				", message='" + message + '\'' +
				'}';
	}
}
