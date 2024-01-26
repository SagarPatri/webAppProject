/**
 * @ (#) Bean.java Jul 29, 2005
 * Project      : 
 * File         : Bean.java
 * Author       : Nagaraj D V
 * Company      : 
 * Date Created : Jul 29, 2005
 *
 * @author       :  Nagaraj D V
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.dto;

import java.util.Date;

public interface Bean {

    /**
     * gets id
     * @return id int
     */
    public int getId();

    /**
     * sets id
     * @param id int
     */
    public void setId(int id);

    /**
     * gets timestamp
     * @return timestamp Date
     */
    public Date getTimestamp();

    /**
     * sets timestamp
     * @param timestamp Date
     */
    public void setTimestamp(Date timestamp);
}
