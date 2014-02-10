package swp.bibjsf.businesslogic;

import javax.naming.NamingException;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import swp.bibcommon.News;
import swp.bibjsf.exception.BusinessElementAlreadyExistsException;
import swp.bibjsf.exception.DataSourceException;
import swp.bibjsf.persistence.Persistence;
import swp.bibjsf.utils.Constraint;
import swp.bibjsf.utils.OrderBy;



public class NewsHandler extends BusinessObjectHandler<News>{

	private static final long serialVersionUID = -5829638462555290041L;

private static volatile NewsHandler instance;
  
  private Persistence persistence;
  
  protected NewsHandler() throws DataSourceException, NamingException {
      super();
  }
  
  public static synchronized NewsHandler getInstance()
          throws DataSourceException {

      if (instance == null) {
          try {
              instance = new NewsHandler();
          } catch (Exception e) {
              throw new DataSourceException(e.getMessage());
          }
      }
      return instance;
  }
  
  public synchronized List<News> get(List<Constraint> constraints, final int from, final int to, List<OrderBy> order)
          throws DataSourceException {
      return persistence.getNews(constraints, from, to, order);
  }
  
  /**
   * Adds reader to database. Reader must not yet exist.
   *
   * @param reader
   *            new reader to be added
   * @return the ID of the added reader
   * @throws DataSourceException
   *             in case of problems with data source
   * @throws BusinessElementAlreadyExistsException
   *             if the reader exists already in the database
   */
  @Override
  public synchronized int add(News news) throws DataSourceException,
      BusinessElementAlreadyExistsException {
	  logger.debug("NEWSHANDLER add(News news)");
	  try{
    	  persistence.addNews(news);
    	  return 1;}
      catch(Exception e)  { 
    	  logger.debug("NEWSHANDLER add(News news) FAIL!");
    	  return -1;
      }
  }

@Override
public News get(int id) throws DataSourceException {
	return persistence.getNews(id);
}

/**
 * An instance of Reader acting as the prototype of objects handled by this handler.
 */
private static News prototype = new News();

@Override
public News getPrototype() {
	return prototype;
}

@Override
public void delete(List<News> elements) throws DataSourceException {
	persistence.deleteAll(elements);	
}

@Override
public int getNumber(List<Constraint> constraints) throws DataSourceException {
	return persistence.getNumberOfNews(constraints);
}

@Override
public int update(int ID, News newValue) throws DataSourceException {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public void exportCSV(OutputStream outStream) throws DataSourceException {
	// TODO Auto-generated method stub
	
}

@Override
public int importCSV(InputStream inputstream) throws DataSourceException {
	// TODO Auto-generated method stub
	return 0;
} 
}