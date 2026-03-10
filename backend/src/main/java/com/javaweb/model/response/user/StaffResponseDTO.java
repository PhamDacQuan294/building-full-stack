package com.javaweb.model.response.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaffResponseDTO {
	private Long staffId;
	private String fullName;
	private String checked;
}
