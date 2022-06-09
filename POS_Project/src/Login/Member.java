package Login;

class Member {

	private static String StoreName;
	private static int grade;
	private static String uId;
	private static String password;
	private static String phone;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getStoreName() {
		return StoreName;
	}

	public void setStoreName(String storeName) {
		StoreName = storeName;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public void setMember(String uId, String password, String StoreName, int grade) {
		this.uId = uId;
		this.password = password;
		this.StoreName = StoreName;
		this.grade = grade;
	}

	Member(String uId, String password, String StoreName, int grade) {
		this.uId = uId;
		this.password = password;
		this.Phone = Phone;
		this.StoreName = StoreName;
		this.grade = grade;
	}

	public Member() {
	}

	public String toString() {
		return this.uId + this.password + this.phone + this.StoreName + this.grade;
	}
}
