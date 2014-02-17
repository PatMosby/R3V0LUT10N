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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import swp.bibcommon.BusinessObject;
import swp.bibjsf.businesslogic.BusinessObjectHandler;
import swp.bibjsf.exception.DataSourceException;
import swp.bibjsf.utils.Constraint;
import swp.bibjsf.utils.Constraint.AttributeType;
import swp.bibjsf.utils.EqualConstraint;
import swp.bibjsf.utils.LikeConstraint;
import swp.bibjsf.utils.Messages;
import swp.bibjsf.utils.OrderBy;
import swp.bibjsf.utils.Reflection;

/**
 * A model for data tables for business objects.
 *
 * @author koschke
 *
 * @param <Element> a business object (e.g., reader or book)
 */
public class TableDataModel<Element extends BusinessObject> extends LazyDataModel<Element> {

	/*
	 * Note: This class does not have any own memory of elements. It will
	 * always query the data base. Having a cache of elements brings the
	 * risk of data redundancy.
	 */

	/**
	 * Logger for this class.
	 */
	private static final Logger logger = Logger.getLogger(TableDataModel.class);

    /**
     * Unique number for serialization.
     */
    private static final long serialVersionUID = 939650263357576256L;

    /**
     * The business handler that takes care of the service requests.
     */
    private final BusinessObjectHandler<Element> handler;

    /**
     * Constructor.
     *
     * @throws DataSourceException thrown in case of problems with the data source
     */
    public TableDataModel(BusinessObjectHandler<Element> newHandler) throws DataSourceException {
        this.handler = newHandler;
        // we must count all elements
        logger.debug("TableDataModel constructor ");
        setRowCount(handler.getNumber(null));
    }

    /**
     * Returns all elements in the range [from .. to] fulfilling the search criteria
     * ordered by <code>order</code>. Sets the row count to the number of all elements
     * fulfilling the search criteria not just those retrieved for the range [from .. to].
     *
     * @param constraints constraints that must be fulfilled
     * @param from lower bound of the range of fetched elements
     * @param to upper bound of the range of fetched elements
     * @param order ordering criteria
     *
     * @throws DataSourceException thrown in case of problems with the data source
     */
    private List<Element> retrieveRows(List<Constraint> constraints, final int from, final int to, List<OrderBy> order)
            throws DataSourceException {
        List<Element> elements = handler.get(constraints, from, to, order);
        // totalRowCount needs to be provided so that paginator can display itself
        // according to the logical number of rows to display.
        setRowCount(handler.getNumber(constraints));
        return elements;
    }

    /**
     * Creates a list of constraints from the search field content.
     *
     * @param filters
     * @return list of constraints from the search field content
     */
    private List<Constraint> toConstraints(Map<String, String> filters, Class<Element> clazz) {
        List<Constraint> constraints = new LinkedList<Constraint>();
        if (filters != null) {
        	HashMap<String, Field> fields = Reflection.getTransitiveFields(new HashMap<String, Field>(), clazz);
            for (Map.Entry<String, String> entry : filters.entrySet()) {
                final String key = entry.getKey();
                // key is the field name of an element as used in the facelet code (*.xhtml)
                // filters.get(key) is the value that was entered by the user into the filter field
                try {
                    // we are using reflection here to determine whether the field is a string;
                    // if so, we will use a like-constraint, otherwise an equal-constraint
                	Field field = fields.get(key);
                	if (field == null) {
                		logger.error("No such field '" + key + "'");
                	} else {
                        if (field.getType().isAssignableFrom(String.class)) {
                            // field is a string
                            constraints.add(new LikeConstraint(key, "%" + entry.getValue() + "%", AttributeType.STRING));
                        } else {
                            // FIXME: this attribute is not necessarily an integer, yet, all that matters
                            // is the fact that it does not need to be quoted.
                            constraints.add(new EqualConstraint(key, entry.getValue(), AttributeType.INTEGER));
                        }
                    }
                } catch (SecurityException e) {
                	logger.error("Must not access field '" + key + "' " + e.getLocalizedMessage());
                }
            }
        }
        return constraints;
    }

    /* (non-Javadoc)
     * @see org.primefaces.model.LazyDataModel#load(int, int, java.lang.String, org.primefaces.model.SortOrder, java.util.Map)
     *
     * Required for lazy loading.
     * DataTable calls load whenever a paging, sorting or filtering occurs
     * with the following parameters:
     *  - first: Offset of first data to start from
     *  - pageSize: Number of data to load
     *  - sortField: Name of sort field (e.g. "model" for sortBy="#{....model}")
     *  - sortOrder: SortOrder enum.
     *  - filter: Filter map with field name as key (e.g. "model" for filterBy="#{....model}") and value.
     *
     *  Note: there is a variant load operation with the signature:
     *   List<Element> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters)
     *  That load operation is required in case of single ordering. The data table, however, offers
     *  multi-ordering (several fields can be used for the ordering at once).
     */
    @Override
    public List<Element> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String,String> filters) {
    	//logger.debug("ReaderDataModel.load() [multi]: " + first + " " + pageSize);

        try {
        	return retrieveRows(toConstraints(filters, (Class<Element>)handler.getPrototype().getClass()), first, first + pageSize - 1, toOrdering(multiSortMeta));
        } catch (DataSourceException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                     "Failure in Data Source",
                                      e.getLocalizedMessage()));
            return null;
        }
    }

    /**
     * Returns the ordering criteria obtained passed here from the multi-sort table.
     *
     * @param multiSortMeta
     * @return ordering criteria
     */
    private List<OrderBy> toOrdering(List<SortMeta> multiSortMeta) {
        if (multiSortMeta != null) {
            List<OrderBy> result = new ArrayList<OrderBy>();
            for (SortMeta sm : multiSortMeta) {
                final SortOrder sortOrder = sm.getSortOrder();
                switch (sortOrder) {
                case ASCENDING:  result.add(new OrderBy(sm.getSortField(), true)); break;
                case DESCENDING: result.add(new OrderBy(sm.getSortField(), false)); break;
                case UNSORTED: /* nothing to be done */ break;
                }
            }
            return result;
        } else {
            return null;
        }
    }

    /* (non-Javadoc)
     * @see org.primefaces.model.SelectableDataModel#getRowData(java.lang.String)
     *
     * Required for selection.
     */
    @Override
    public Element getRowData(String rowKey) {
    	try{
    		final int key = Integer.parseInt(rowKey);
    		return handler.get(key);

    	} catch (DataSourceException e) {
    		logger.error("no element found for row key '" + rowKey + "'");
    		FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                     Messages.get("noReadersFound"),
                                      e.getLocalizedMessage()));
    		return null;
    	} catch (Exception e) {
    		logger.error("ReaderDataModel.getRowData(): Unexpected row key = '" + rowKey + "' " + e.getMessage());
    		return null;
    	}
    }

    /* (non-Javadoc)
     * @see org.primefaces.model.SelectableDataModel#getRowKey(java.lang.Object)
     *
     * Required for selection.
     */
    @Override
    public Object getRowKey(Element reader) {
        return reader.getId();
    }
}
