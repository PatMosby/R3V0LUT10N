package swp.bibjsf.presentation;

//import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import swp.bibcommon.Times;

@ManagedBean
@RequestScoped
public class TimesForm extends BusinessObjectForm<Times>{

	private static final long serialVersionUID = 3640330979965256886L;

	public void setDay(String day){
		element.setDay(day);
	}
	
	public String getDay(){
		return element.getDay();
	}
	
	public void setOpen(String open){
		element.setOpen(open);
	}

	public String getOpen(){
		return element.getOpen();
	}
	
	public void setClose(String close){
		element.setClose(close);
	}

	public String getClose(){
		return element.getClose();
	}

	@Override
	public String save() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
     *  Language of a book. A two-letter ISO 639-1 code such as 'fr', 'en', etc.
     */
//    private String[] languages = {"de", "en", "fr", "es", "it"};
//
//    public String[] getLanguages() {
//    	return languages.clone();
//    }
//
//    public void setLanguages(String[] languages) {
//    	this.languages = languages.clone();
//    }
}
