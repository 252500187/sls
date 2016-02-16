/*
* AppRuntimeException.java
* Created on  2013-10-28 下午8:08
* 版本       修改时间          作者      修改内容
* V1.0.1    2013-10-28       gaoxinyu    初始版本
*
*/
package com.core.exception;

/**
 * 类的描述信息
 *
 * @author gaoxinyu
 * @version 1.0.1
 */
public class AppRuntimeException extends RuntimeException {

	private String code = "RUNTIME_EXCEPTION";

    /**
   	 * @param message
   	 */
   	public AppRuntimeException(String message) {
   		super(message);
   	}

	/**
	 * @param message
	 * @param code
	 */
	public AppRuntimeException(String message, String code) {
		super(message);
		this.code = code;
	}

	/**
	 * @param message
	 * @param cause
	 * @param code
	 */
	public AppRuntimeException(String message, Throwable cause, String code) {
		super(message, cause);
		this.code = code;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
}