/*
* ExcelVO.java
* Created on  2012-4-17 上午10:06
* 版本       修改时间          作者      修改内容
* V1.0.1    2012-4-17      gaoxy    初始版本
*
* Copyright (c) 2010 长春吉成科技有限公司 版权所有
* CHANGCHUN GENGHIS TECHNOLOGY CO.,LTD. All Rights Reserved.
*/
package com.sls.util;

import java.util.Arrays;

/**
 * excel对象
 *
 * @author wangf
 * @version 1.0.1
 */
public class ExcelVO {
    private String fileName;
    private String title;
    private int tableHeadRowNum;
    private String[] colTitle;
    private int[] width;
    private int[] align;
    //需要合计列的每个单元格在setCellValue的时候，参数必须为double类型的数据，即使用public void setCellValue(double value)方法
    private boolean[] sumFlag;

    public ExcelVO() {
        this.tableHeadRowNum = 1;
    }

    /**
     * @param fileName        文件名
     * @param title           标题
     * @param colTitle        表头
     * @param tableHeadRowNum 表头行数
     * @param width           各列宽
     * @param align           对齐方式
     * @param sumFlag         各列合计标志位,如果不需要可设置为null
     */
    public ExcelVO(String fileName, String title, int tableHeadRowNum, String[] colTitle, int[] width, int[] align, boolean[] sumFlag) {
        this.fileName = fileName;
        this.title = title;
        this.tableHeadRowNum = tableHeadRowNum;
        this.colTitle = Arrays.copyOf(colTitle, colTitle.length);
        this.width = Arrays.copyOf(width,width.length);
        this.align = Arrays.copyOf(align,align.length);
        this.sumFlag = Arrays.copyOf(sumFlag,sumFlag.length);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTableHeadRowNum() {
        return tableHeadRowNum;
    }

    public void setTableHeadRowNum(int tableHeadRowNum) {
        this.tableHeadRowNum = tableHeadRowNum;
    }

    public String[] getColTitle() {
        return colTitle;
    }

    public void setColTitle(String[] colTitle) {
        this.colTitle = Arrays.copyOf(colTitle,colTitle.length);
    }

    public int[] getWidth() {
        return width;
    }

    public void setWidth(int[] width) {
        this.width = Arrays.copyOf(width,width.length);
    }

    public int[] getAlign() {
        return align;
    }

    public void setAlign(int[] align) {
        this.align = Arrays.copyOf(align,align.length);
    }

    public boolean[] getSumFlag() {
        return sumFlag;
    }

    public void setSumFlag(boolean[] sumFlag) {
        this.sumFlag = Arrays.copyOf(sumFlag,sumFlag.length);
    }
}
