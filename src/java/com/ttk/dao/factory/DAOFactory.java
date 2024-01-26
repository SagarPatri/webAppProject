/**
 * @ (#) DAOFactory.java Jul 29, 2005
 * Project      : 
 * File         : DAOFactory.java
 * Author       : Nagaraj D V
 * Company      : 
 * Date Created : Jul 29, 2005
 *
 * @author       :  Nagaraj D V
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.dao.factory;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;

/**
 * An Abstract class DAO Factory for all DAO's
 */
public abstract class DAOFactory {

    /**
     * creates an instance of the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    public abstract BaseDAO getDAO(String dao) throws TTKException;
}//end of class DAOFactory