/**
 * @ (#) BaseDAO.java Jul 29, 2005
 * Project      : 
 * File         : BaseDAO.java
 * Author       : Nagaraj D V
 * Company      : 
 * Date Created : Jul 29, 2005
 *
 * @author       :  Nagaraj D V
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.dao;



/**
 * Data Access Object(DAO) interface
 */
public interface BaseDAO {

	/**
	 * insert a record
	 * @param bean Bean
	 * @throws TTKException
	 */
	//public abstract void insert(BaseVO bean) throws TTKException;

	/**
	 * update a record
	 * @param bean Bean to be updated
	 * @throws TTKException
	 */
	//public abstract void update(BaseVO bean) throws TTKException;

	/**
	 * delete records
	 * @param id String
	 * @throws TTKException
	 */
	//public abstract void delete(String id) throws TTKException;

	/**
	 * Retrieve a record
	 * @param id Long the id of the record to be retrieved
	 * @return Bean
	 * @throws TTKException
	 */
	//public abstract BaseVO findByPrimaryKey(Long id) throws TTKException;
	
	/**
	 * Retrieve a BaseVO object
	 * @param baseVO BaseVO object
	 * @return BaseVO object
	 * @throws TTKException
	 */
	//public abstract BaseVO findByPrimaryKey(BaseVO baseVO) throws TTKException;

	/**
	 * This method returns the collection of value objects  
	 * @param query String which contains the SQL statement to be executed
	 * @exception throws TTKException
	 */
	//public abstract Collection findAll(String query) throws TTKException;

	/**
	 * Dynamic sql
	 * @param sqlString String
	 * @param sqlParams Object[]
	 * @return Collection
	 * @throws TTKException
	 */
	//public abstract Collection findByDynamicWhere(String sqlString, Object[] sqlParams) throws TTKException;

	/**
	 * Returns sql count
	 * @param sqlWhere String
	 * @return Collection
	 * @throws TTKException
	 */
	//public abstract int count(String sqlWhere) throws TTKException;
	
	/**
	 * Returns the dynamic where clause and sort order for the sql query
	 * @param alSearchCriteria ArrayList which contains the collection of SearchCriteria objects
	 * @param strSortColumnName String object which contains sort column name
	 * @param strSortOrder String object which contains sort order
	 * @param strStartRow String object which contains starting row number for the query
	 * @param strEndRow String object which contains ending row number for the query
	 * @return String which contains the where clause for the sql
	 * @throws TTKException
	 */
	//public abstract String buildParam(ArrayList alSearchCriteria, String  strSortColumnName, String strSortOrder, String strStartRow, String strEndRow) throws TTKException;

}
