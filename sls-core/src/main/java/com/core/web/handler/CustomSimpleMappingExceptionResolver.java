/*
* CustomSimpleMappingExceptionResolver.java
* Created on  2013-11-5 下午8:37
* 版本       修改时间          作者      修改内容
* V1.0.1    2013-11-5       gaoxinyu    初始版本
*
*/
package com.core.web.handler;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CustomSimpleMappingExceptionResolver extends
        SimpleMappingExceptionResolver {

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response,
                                              Object handler, Exception ex) {
        String viewName = determineViewName(ex, request);
        if (viewName != null) {
            if (isJsonReturn(request)) {
                try {
                    PrintWriter writer = response.getWriter();
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    writer.write(ex.getMessage() == null ? ex.toString() : ex.getMessage());
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            } else {
                Integer statusCode = determineStatusCode(request, viewName);
                if (statusCode != null) {
                    applyStatusCodeIfPossible(request, response, statusCode);
                }
                return getModelAndView(viewName, ex, request);
            }
        } else {
            return null;
        }
    }

    private boolean isJsonReturn(HttpServletRequest request){
        String xRequestedWith = request.getHeader("X-Requested-With");
        return request.getHeader("accept").contains("application/json") ||
                (xRequestedWith != null && xRequestedWith.contains("XMLHttpRequest"));
    }
}
