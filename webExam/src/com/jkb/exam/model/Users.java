package com.jkb.exam.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
private int id;
private String username;
private String password;
private String email;
private String role;
private Timestamp createDate;

public String getUsername() {
	return username.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
}

}

