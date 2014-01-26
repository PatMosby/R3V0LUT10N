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

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import swp.bibcommon.Medium;

/**
 * A list data model for books.
 *
 * @author koschke
 *
 */
public class MediumListDataModel extends ListDataModel<Medium> implements SelectableDataModel<Medium> {

    /**
     * Constructor.
     */
    public MediumListDataModel() {
    }

    /**
     * Constructor.
     *
     * @param data the data to be added to this data model.
     */
    public MediumListDataModel(List<Medium> data) {
        super(data);
    }

    /* (non-Javadoc)
     * @see org.primefaces.model.SelectableDataModel#getRowData(java.lang.String)
     */
    @Override
    public Medium getRowData(String rowKey) {
        final int id = Integer.parseInt(rowKey);
        List<Medium> mediums = (List<Medium>) getWrappedData();

        for(Medium medium : mediums) {
            if(medium.getId() == id) {
                return medium;
            }
        }
        return null;
    }

    /* (non-Javadoc)
     * @see org.primefaces.model.SelectableDataModel#getRowKey(java.lang.Object)
     */
    @Override
    public Object getRowKey(Medium medium) {
        return medium.getId();
    }
}

