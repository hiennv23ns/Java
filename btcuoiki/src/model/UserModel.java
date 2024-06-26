package model;

public class UserModel {
	private String userName;
	private String passWord;
	private String fullName;
	public UserModel(String userName, String passWord) {
		this.userName = userName;
		this.passWord = passWord;
	}
	
	public UserModel() {
		super();
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	
	
}
