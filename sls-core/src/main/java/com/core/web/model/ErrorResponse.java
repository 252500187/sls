/*
* ErrorResponse.java
* Created on  2013-10-28 下午8:18
* 版本       修改时间          作者      修改内容
* V1.0.1    2013-10-28       gaoxinyu    初始版本
*
*/
package com.core.web.model;

/**
 * ErrorResponse
 *
 * @author gaoxinyu
 * @version 1.0.1
 */
public class ErrorResponse implements RestResponse {
    private String code;
    private String message;

    public ErrorResponse() {
    }

    public ErrorResponse(String message) {
        this.message = message;
    }

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
