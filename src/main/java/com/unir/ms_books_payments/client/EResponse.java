package com.unir.ms_books_payments.client;

public class EResponse<T> {

	private T body;
	private String error;
	private String code;

	public EResponse() {
	}

	public EResponse(T body, String error, String code) {
		this.body = body;
		this.error = error;
		this.code = code;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}