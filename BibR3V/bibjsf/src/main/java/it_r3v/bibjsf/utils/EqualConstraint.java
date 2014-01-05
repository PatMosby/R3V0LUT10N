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

package it_r3v.bibjsf.utils;

/**
 * Requires that the attribute values must be equal (=).
 *
 * @author koschke
 *
 */
public class EqualConstraint extends Constraint {

    /**
     * Constructor.
     *
     * @param attribute the attribute of the element upon which the
     *    constraint is to be placed
     * @param property the property the attribute must fulfill
     * @param type type of the attribute to be compared
     */
    public EqualConstraint(final String attribute,
            final String property, final AttributeType type) {
        super(attribute, property, type);
    }

    /**
     * Constructor.
     *
     * @param attribute the attribute of the element upon which the constraint is
     *     to be placed
     * @param property the property the attribute must fulfill
     * @param type the attribute type
     */
    public EqualConstraint(final String attribute, final int property, final AttributeType type) {
        super(attribute, ((Integer) property).toString(), type);
    }

}
