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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import swp.bibjsf.businesslogic.AdministrationHandler;
import swp.bibjsf.presentation.Administration;
import swp.bibjsf.exception.DataSourceException;
import swp.bibjsf.utils.Messages;

/**
 * Functions reserved for the administrator, that is, administrations of backups.
 *
 * @author koschke
 *
 */
@ManagedBean
@SessionScoped
@DeclareRoles({"ADMIN"})
public class Administration  implements Serializable {

    /**
     * Unique ID for serialization.
     */
    private static final long serialVersionUID = -649625566653922056L;

   // AutoAdministration auto = new AutoAdministration();

    
    /**
     * Action to reset the database.
     *
     * @return navigation result
     */
    public String resetDB() {
        try {
            AdministrationHandler.getInstance().resetDB();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,  Messages.get("success"),  Messages.get("resetDBsuccess"));
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "success";
        } catch (DataSourceException e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, Messages.get("resetDBfailed"),
                    e.getLocalizedMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "error";
        }
    }

    /**
     * Action to backup the database.
     *
     * @return navigation result
     */
    public String backupDB() {
        try {
            AdministrationHandler.getInstance().backupDB();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,  Messages.get("success"),  Messages.get("backupDBsuccess"));
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "success";
        } catch (DataSourceException e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, Messages.get("backupDBfailed"),
                    e.getLocalizedMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "error";
        }
    }

    /**
     * Action to restore the database from a previous backup.
     *
     * @return navigation result
     */
    public String restoreDB() {
        try {
            AdministrationHandler.getInstance().restoreDB();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,  Messages.get("success"),  Messages.get("restoreDBsuccess"));
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "success";
        } catch (DataSourceException e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, Messages.get("restoreDBfailed"),
                    e.getLocalizedMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "error";
        }
    }
    
    /**
     * author: Pupat
     * startet das autobackup
     */
    public void autoBackUp(){
        try{
           AutoAdministration.getInstance().start();
        } catch (DataSourceException e) {
        	
        }
    }
    
    /**
     * author: Pupat
     * beendet das autobackup und die instance
     */
    public void autoBackUpEnd(){
        try{
            AutoAdministration.getInstance().interrupt();
            AutoAdministration.endInstance();
         } catch (DataSourceException e) {
         	
         }
    }
    
   
}
