package org.generation.italy.model;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

public class PhotoForm {

	private Integer id;
	
	@NotEmpty(message="Title is mandatory")
	private String title;
	
	private MultipartFile content;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public MultipartFile getContent() {
		return content;
	}

	public void setContent(MultipartFile content) {
		this.content = content;
	}
	
	
}
