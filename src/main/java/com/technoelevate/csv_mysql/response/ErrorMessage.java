package com.technoelevate.csv_mysql.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
	private boolean error;
	private Object message;
	private Object data;
}
