package cse135.model;

public class User {
	private String name;
	private int age;
	private String state;
	private String role;
	private long id;
	
	public User() {
		this.setName("");
		this.setAge(0);
		this.setState("");
		this.setRole("");
	}
	
	public User(String name, int age, String state, String role, long id) {
		this.setName(name);
		this.setAge(age);
		this.setState(state);
		this.setRole(role);
		this.setId(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public boolean isOwner() {
		return role.contentEquals("owner");
	}
	


}
