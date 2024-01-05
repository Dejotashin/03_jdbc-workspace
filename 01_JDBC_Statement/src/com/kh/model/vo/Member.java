package com.kh.model.vo;

import java.sql.Date;

// VO(Value Object)
// 한명의 회원에 대한 데이터들을 기록할 수 있는 저장용 객체
public class Member {

	// 여러타입 담기위한 나만의 자료형
	
	// 필드
	// 필드는 기본적으로 DB 컬럼명과 유사하게 작업
	private int userNO; 
	private String userId; 
	private String userPwd; 
	private String userName; 
	private String gender;
	private int age; 
	private String email; 
	private String phone; 
	private String address; 
	private String hobby; 
	private Date enrollDate; // java.sql.Date import 해야됨!!
	
	// 생성자부
	// 기본생성자, 매개변수 생성자
	
	public Member() {}

	public Member(int userNO, String userId, String userPwd, String userName, String gender, int age, String email,
			String phone, String address, String hobby, Date enrollDate) {
		super();
		this.userNO = userNO;
		this.userId = userId;
		this.userPwd = userPwd;
		this.userName = userName;
		this.gender = gender;
		this.age = age;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.hobby = hobby;
		this.enrollDate = enrollDate;
	}

	public Member(String userId, String userPwd, String userName, String gender, int age, String email, String phone,
			String address, String hobby) {
		super();
		this.userId = userId;
		this.userPwd = userPwd;
		this.userName = userName;
		this.gender = gender;
		this.age = age;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.hobby = hobby;
	}

	public int getUserNO() {
		return userNO;
	}

	public void setUserNO(int userNO) {
		this.userNO = userNO;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public Date getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}

	@Override
	public String toString() {
		return "Member [userNO=" + userNO + ", userId=" + userId + ", userPwd=" + userPwd + ", userName=" + userName
				+ ", gender=" + gender + ", age=" + age + ", email=" + email + ", phone=" + phone + ", address="
				+ address + ", hobby=" + hobby + ", enrollDate=" + enrollDate + "]";
	}
	

	
}
