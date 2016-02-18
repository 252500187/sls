/*
* BaseDao.java
* Created on  2013-10-18 上午8:57
* 版本       修改时间          作者      修改内容
* V1.0.1    2013-10-18       gaoxinyu    初始版本
*
*/
package com.core.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import javax.sql.DataSource;

/**
 * BaseDao
 *
 * @author gaoxinyu
 * @version 1.0.1
 */
public class BaseDao extends NamedParameterJdbcDaoSupport {

    @Autowired
    public final void setSuperDataSource(DataSource dataSource) {
        setDataSource(dataSource);
    }

    protected int getSum(int[] numbers) {
        if (numbers == null) {
            return 0;
        }
        int result = 0;
        for (int aNumber : numbers) {
            result += aNumber;
        }
        return result;
    }

}
