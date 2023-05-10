package in.librarymanagement.dto;

import java.io.Serializable;

public class Librarian implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer lId;
	private String lName;
	private String lEmail;
	private Long lContact;
	private String lPassword;
	
	public Integer getlId() {
		return lId;
	}
	public void setlId(Integer lId) {
		this.lId = lId;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getlEmail() {
		return lEmail;
	}
	public void setlEmail(String lEmail) {
		this.lEmail = lEmail;
	}
	public Long getlContact() {
		return lContact;
	}
	public void setlContact(Long lContact) {
		this.lContact = lContact;
	}
	public String getlPassword() {
		return lPassword;
	}
	public void setlPassword(String lPassword) {
		this.lPassword = lPassword;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "Librarian [lId=" + lId + ", lName=" + lName + ", lEmail=" + lEmail + ", lContact=" + lContact
				+ ", lPassword=" + lPassword + "]";
	}
	
}
