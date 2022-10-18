package musinsa;

import java.io.Serializable;

public class Musinsa implements Comparable<Musinsa>, Serializable {
	private String no;
	private String name;
	private int age;
	private String phone;
	private int clothing;
	private int acc;
	private int sport;
	private int pet;
	private int total;
	private String grade;
	private String save;
	private String dc;

	public Musinsa(String no, String name, int age, String phone, int clothing, int acc, int sport, int pet) {
		this(no, name, age, phone, clothing, acc, sport, pet, 0, null, null, null);

	}

	public Musinsa(String no, String name, int age, String phone, int clothing, int acc, int sport, int pet, int total,
			String grade,String save, String dc) {
		super();
		this.no = no;
		this.name = name;
		this.age = age;
		this.phone = phone;
		this.clothing = clothing;
		this.acc = acc;
		this.sport = sport;
		this.pet = pet;
		this.total = total;
		this.grade = grade;
		this.save = save;
		this.dc = dc;
	}

	

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getClothing() {
		return clothing;
	}

	public void setClothing(int clothing) {
		this.clothing = clothing;
	}

	public int getAcc() {
		return acc;
	}

	public void setAcc(int acc) {
		this.acc = acc;
	}

	public int getSport() {
		return sport;
	}

	public void setSport(int sport) {
		this.sport = sport;
	}

	public int getPet() {
		return pet;
	}

	public void setPet(int pet) {
		this.pet = pet;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	public String getSave() {
		return save;
	}

	public void setSave(String save) {
		this.save = save;
	}

	public String getDc() {
		return dc;
	}

	public void setDc(String dc) {
		this.dc = dc;
	}

	@Override
	public String toString() {
		String str = "〓〓";
		System.out.println(str.repeat(35));
		return "|회원번호 : " + no + "\t|이름 : " + name + "\t|나이 : " + age + "\t\t|전화번호 : " + phone + "\n|의류 : " + clothing
				+ "\t|액세서리 : " + acc + "\t|스포츠용품 : " + sport + "\t|애완용품 : " + pet + "\t|총구매금액 : " + total + "\t|등급 : " + grade+
				"|\n-등급별 혜택-"
				+ "\n|추가적립 : " + save + "|추가할인 : " + dc + "|\n"+str.repeat(35);
		
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Musinsa) {
			return this.no.equals(((Musinsa) obj).no);
		}

		return false;
	}

	@Override
	public int hashCode() {

		return this.no.hashCode();
	}

	@Override
	public int compareTo(Musinsa o) {

		return this.total - this.total;
	}



}
