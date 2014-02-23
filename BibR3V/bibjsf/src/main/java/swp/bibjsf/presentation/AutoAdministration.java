/*
 * Copyright (c) 2013 AG Softwaretechnik, University of Bremen, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package swp.bibjsf.presentation;

import java.io.Serializable;

import javax.annotation.security.DeclareRoles;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.NamingException;

import swp.bibjsf.businesslogic.AdministrationHandler;
import swp.bibjsf.exception.DataSourceException;

/**
 * Thread, welcher gestartet wird, wenn das automatische Backup aktiviert wurde. Sichert die Daten 
 * täglich
 *
 * @author Pupat
 *
 */
@ManagedBean
@SessionScoped
@DeclareRoles({"ADMIN"})
public class AutoAdministration extends Thread implements Serializable {

    /**
     * Unique ID for serialization.
     */
    private static final long serialVersionUID = -649625566653922056L;    
    
	protected AutoAdministration() throws DataSourceException,
    NamingException { }

  /**
  * Returns the only instance of AdministrationHandler (singleton).
  */
    private static volatile AutoAdministration instance;

    /**
     * beendet die instance, damit ein neuer thread gestartet werden kann
     * 
     * @throws DataSourceException
     */
    public static void endInstance()
            throws DataSourceException {
    		try {
    			instance = null;
    		} catch (Exception e) {
    			throw new DataSourceException(e.getMessage());
    		}
    	}
  /**
  * Returns the only instance of AutoAdministration (singleton).
  *
  * @return the only instance of AdministrationHandler (singleton); may be null
  * @throws DataSourceException thrown in case of problems with the data source
  */
    public static synchronized AutoAdministration getInstance()
                              throws DataSourceException {

          if (instance == null) {
               try {
                    instance = new AutoAdministration();
                    } catch (Exception e) {
                 throw new DataSourceException(e.getMessage());
                    }
         }
         return instance;
     }      
    
    /**
     * Startet den Thread, läuft solange bis er ein interrupt bekommt
     * Ruft täglich backup() auf
     */

    public void run() {
    	while(true){
          try {
            sleep(86400000); //Ein Tag
            AdministrationHandler.getInstance().backupDB();
          } catch(InterruptedException ie) {
            Thread.currentThread().interrupt();
            if(Thread.currentThread().isInterrupted())
            {
                break;
            }
          }
          catch(DataSourceException e) {
          }
        }
    }
    
    
    
}
