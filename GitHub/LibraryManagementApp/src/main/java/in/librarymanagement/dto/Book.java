package in.librarymanagement.dto;

import java.io.Serializable;

public class Book implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer bId;
	private String bTitle;
	private String bAuthor;
	private String bCategory;
	private Integer bCount;
	
	@Override
	public String toString() {
		return "Book [bId=" + bId + ", bTitle=" + bTitle + ", bAuthor=" + bAuthor + ", bCategory=" + bCategory
				+ ", bCount=" + bCount + "]";
	}

	public Integer getbId() {
		return bId;
	}

	public void setbId(Integer bId) {
		this.bId = bId;
	}

	public String getbTitle() {
		return bTitle;
	}

	public void setbTitle(String bTitle) {
		this.bTitle = bTitle;
	}

	public String getbAuthor() {
		return bAuthor;
	}

	public void setbAuthor(String bAuthor) {
		this.bAuthor = bAuthor;
	}

	public String getbCategory() {
		return bCategory;
	}

	public void setbCategory(String bCategory) {
		this.bCategory = bCategory;
	}

	public Integer getbCount() {
		return bCount;
	}

	public void setbCount(Integer bCount) {
		this.bCount = bCount;
	}
	
}
