package cse135.model;

public class User {
	private String name;
	private int age;
	private String state;
	private boolean owner;
	private long id;
	
	public User() {
		this.setName("");
		this.setAge(0);
		this.setState("");
		this.setOwner(false);
	}
	
	public User(String name, int age, String state, boolean owner, long id) {
		this.setName(name);
		this.setAge(age);
		this.setState(state);
		this.setOwner(owner);
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

	public boolean isOwner() {
		return owner;
	}

	public void setOwner(boolean owner) {
		this.owner = owner;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	


}
