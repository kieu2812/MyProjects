package com.graphql.example.domain;

public class User {
    
    private final String id;
    private final String name;
    private final String sex;
    private final String birth;

    public User(String name, String sex) {
        this(null, name, sex, null);
    }
    
    public User(String name, String sex, String birth) {
        this(null, name, sex, birth);
    }

    public User(String id, String name, String sex, String birth) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.birth = birth;
    }

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getSex() {
		return sex;
	}

	public String getBirth() {
		return birth;
	}    
}
