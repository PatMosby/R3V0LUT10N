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

import javax.annotation.security.DeclareRoles;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * Bean to handle authentication.
 *
 * @author D. Lüdemann
 *
 */
@ManagedBean
@RequestScoped
@DeclareRoles({"ADMIN", "USER"})
public class AuthBackingBean {

  private static Logger log = Logger.getLogger(AuthBackingBean.class.getName());

  public String logout() {
	  String result="/index?faces-redirect=true";
	  //    String result="index";
	  FacesContext context = FacesContext.getCurrentInstance();
	  HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();

	  try {
		  request.logout();
	  } catch (ServletException e) {
		  log.error("ERROR in logout: " + e.getLocalizedMessage());
		  result = "/loginError?faces-redirect=true";
	  }

	  return result;
  }
}