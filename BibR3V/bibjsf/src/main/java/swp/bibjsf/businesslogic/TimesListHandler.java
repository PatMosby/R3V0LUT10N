package swp.bibjsf.businesslogic;

import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.text.DateFormat;

import java.lang.Object;
import java.text.Format;
import java.text.SimpleDateFormat;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.CellEditEvent;

import swp.bibjsf.persistence.Data;
import swp.bibcommon.Borrower;
import swp.bibcommon.Times;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import swp.bibjsf.exception.DataSourceException;

@ManagedBean(name="zeit")
@SessionScoped
public class TimesListHandler {
	Data data;
	final Logger logger = Logger.getLogger(TimesListHandler.class);
	
	public TimesListHandler()throws DataSourceException, NamingException{
	 data = new Data();
	  final Logger logger = Logger.getLogger(TimesListHandler.class);
	}
	
	

	public List<Times> getTimeList(){
		logger.debug("REACHED---GET_Times");
		
		return data.getTimeList();
	}
	
	
	
	
}