package in.librarymanagement.dto;

import java.io.Serializable;

public class Student implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer sId;
	private String sName;
	private String sEmail;
	private Long sContact;
	private String sPassword;
	private Integer bookLimit;
	
	public Integer getBookLimit() {
		return bookLimit;
	}
	public void setBookLimit(Integer bookLimit) {
		this.bookLimit = bookLimit;
	}
	public Integer getsId() {
		return sId;
	}
	public void setsId(Integer sId) {
		this.sId = sId;
	}
	public String getsName() {
		return sName;
	}
	public void setsName(String sName) {
		this.sName = sName;
	}
	public String getsEmail() {
		return sEmail;
	}
	public void setsEmail(String sEmail) {
		this.sEmail = sEmail;
	}
	public Long getsContact() {
		return sContact;
	}
	public void setsContact(Long sContact) {
		this.sContact = sContact;
	}
	public String getsPassword() {
		return sPassword;
	}
	public void setsPassword(String sPassword) {
		this.sPassword = sPassword;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "Student [sId=" + sId + ", sName=" + sName + ", sEmail=" + sEmail + ", sContact=" + sContact
				+ ", sPassword=" + sPassword + "]";
	}
	
}
