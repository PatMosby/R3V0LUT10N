package swp.bibjsf.presentation;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import swp.bibcommon.Reader;
import swp.bibjsf.businesslogic.ReaderHandler;
import swp.bibjsf.exception.DataSourceException;


@ManagedBean
@SessionScoped
public class ShowUserProfil {
	
	/**
	 * Unige ID for serialization.
	 */
	private static final long serialVersionUID = -494454216495818388L;
	private Reader reader;
	
	public ShowUserProfil() {
		super();
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

}
