/*
* PageParameter.java
* Created on  2013-10-15 下午2:45
* 版本       修改时间          作者      修改内容
* V1.0.1    2013-10-15       gaoxinyu    初始版本
*
*/
package com.core.page.entity;

/**
 * 分页参数信息类
 *
 * @author gaoxinyu
 * @version 1.0.1
 */
public class PageParameter {
    private int pageSize;
    private int pageNumber;
    private String sortColumn;
    private String sortDirection = "ASC";

    public PageParameter() {
    }

    public PageParameter(int pageSize, int pageNumber) {
        this(pageSize, pageNumber, "", "");
    }

    public PageParameter(int pageSize, int pageNumber, String sortColumn, String sortDirection) {
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.sortColumn = sortColumn;
        this.sortDirection = sortDirection;
    }

    public boolean checkSortIsNotEmpty() {
        return sortColumn != null && !sortColumn.isEmpty() && sortDirection != null;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getSortColumn() {
        return sortColumn;
    }

    public String getSortColumnAfterReplace() {
        return getSortColumn().replaceAll(".*([';]+|(--)+).*", "");
    }

    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }
}
