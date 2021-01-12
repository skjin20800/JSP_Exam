
package com.jkb.exam.dto;

import lombok.Data;

@Data
public class CommonRespDto<T> {
	private int statusCode; // 1, -1
	private T data;
}