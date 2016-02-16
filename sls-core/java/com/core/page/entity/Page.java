/*
* Page.java
* Created on  2013-10-9 上午9:35
* 版本       修改时间          作者      修改内容
* V1.0.1    2013-10-9       gaoxinyu    初始版本
*
*/
package com.core.page.entity;

import java.util.List;

/**
 * 分页结果集
 *
 * @author gaoxinyu
 * @version 1.0.1
 */
public class Page<T> {

    private PageParameter pageParameter;
    private int totalCount;
    private List<T> resultList;

    public Page() {
    }

    public Page(PageParameter pageParameter, int totalCount, List<T> resultList) {
        this.pageParameter = pageParameter;
        this.totalCount = totalCount;
        this.resultList = resultList;
    }


    public PageParameter getPageParameter() {
        return pageParameter;
    }

    public void setPageParameter(PageParameter pageParameter) {
        this.pageParameter = pageParameter;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }
}
