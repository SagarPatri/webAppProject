package com.ttk.dao;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.ttk.common.TTKPropertiesReader;

public class PooledConnection {
public static Connection getConnection(){
Context context = null;
DataSource ds = null;
Connection con = null;

try {
    context = (Context) new InitialContext();
    //ds = (DataSource) context.lookup(jndiPath);
	ds = (DataSource) context.lookup(TTKPropertiesReader.getPropertyValue("DataSourceName"));
    con = ds.getConnection();
} catch (NamingException ne) {
	ne.printStackTrace();
} catch (Exception e) {
	e.printStackTrace();
}//end of (Exception e)
return con;
}
}
