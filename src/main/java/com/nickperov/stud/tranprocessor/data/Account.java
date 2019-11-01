package com.nickperov.stud.tranprocessor.data;

public class Account extends AbstractDataEntity {
	
	Account(String code) {
		this.code = code;
	}
	
	private String code;
	
	public String getCode() {
		return code;
	}
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Account [id:").append(id).append("; ");
		sb.append("code:").append(code).append("]");
		return sb.toString();
	}
}