package Login;

public class Member {
	
	private String StoreName = "";
	private int grade;
	
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
	
	Member(String StoreName, int grade) {
		this.StoreName = StoreName;
		this.grade = grade;
	}
	
	public String toString() {
		return this.StoreName + this.grade;
		
	}
}
