package phoneticsearch;

import java.util.Date;

public final class Person {
	private int score;
	private final String name;
	private final String firstname;
	private final Date birthday;
	private final String address;
	private final String zipcode;
	private final String city;
	private final String phone;

	public Person(final String name, final String firstname, final Date birthday, final String address, final String zipcode, final String city, final String phone) {
		this.name = name;
		this.firstname = firstname;
		this.birthday = birthday;
		this.address = address;
		this.zipcode = zipcode;
		this.city = city;
		this.phone = phone;
	}

	public int getScore() {
		return score;
	}

	public void setScore(final int score) {
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public String getFirstname() {
		return firstname;
	}

	public Date getBirthday() {
		return birthday;
	}

	public String getAddress() {
		return address;
	}

	public String getZipcode() {
		return zipcode;
	}

	public String getCity() {
		return city;
	}

	public String getPhone() {
		return phone;
	}
}
