package swp.bibjsf.presentation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import swp.bibcommon.Borrower;
import swp.bibcommon.Reader;
import swp.bibjsf.businesslogic.ReaderHandler;
import swp.bibjsf.exception.DataSourceException;
import swp.bibjsf.persistence.Data;

import org.apache.log4j.Logger;


@ManagedBean
@SessionScoped
public class ShowUserProfil {
	
	/**
	 * Unige ID for serialization.
	 */
	private static final long serialVersionUID = -494454216495818388L;
	private Reader reader;
	final Logger logger = Logger.getLogger(ShowUserProfil.class);
	Data data = new Data();
	
	public ShowUserProfil() throws DataSourceException, NamingException{
		super();
		data = new Data();
	}
	
	public String getUser() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
		return request.getRemoteUser();
	}
	
	
	public Reader getReaderByUsername(){		
		try {
			ReaderHandler rh = ReaderHandler.getInstance();
			String name = getUser();
			reader = rh.getReaderByUsername(name);
			
		} catch (DataSourceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reader;
		
	}
	
	
	public List<Borrower> getBorrower(){
		logger.debug("REACHED---GET_Borrower");
		
		List<Borrower> borrowerList = new ArrayList<>();
		return data.getBorrower();
	}

}
