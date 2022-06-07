package com.epam.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResponse {
	private String timeStamp;
	private String status;
	private String errors;
	private String path;
}
