package com.technoelevate.csv_mysql.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage {
	private boolean error;
	private String message;
	private Object data;
}
