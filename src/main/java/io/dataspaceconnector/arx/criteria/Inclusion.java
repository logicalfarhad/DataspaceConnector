/*
 * ARX: Powerful Data Anonymization
 * Copyright 2012 - 2021 Fabian Prasser and contributors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.dataspaceconnector.arx.criteria;

import io.dataspaceconnector.arx.ARXConfiguration;
import io.dataspaceconnector.arx.DataSubset;
import io.dataspaceconnector.arx.certificate.elements.ElementData;
import io.dataspaceconnector.arx.framework.check.groupify.HashGroupifyEntry;
import io.dataspaceconnector.arx.framework.data.DataManager;
import io.dataspaceconnector.arx.framework.lattice.Transformation;

/**
 * This is a special criterion that does not enforce any privacy guarantees
 * but allows to define a data subset.
 *
 * @author Fabian Prasser
 * @author Florian Kohlmayer
 */
public class Inclusion extends DPresence {
    
    /**  SVUID */
    private static final long serialVersionUID = -3984193225980793775L;
    
    /**
     * Creates a new instance of the enclosure criterion.
     *
     * @param subset Research subset
     */
    public Inclusion(DataSubset subset) {
        super(subset);
    }

    @Override
    public PrivacyCriterion clone(DataSubset subset) {
        return new Inclusion(subset);
    }
    
    @Override
    public int getRequirements(){
        // Requires two counters
        return ARXConfiguration.REQUIREMENT_COUNTER |
               ARXConfiguration.REQUIREMENT_SECONDARY_COUNTER;
    }

    @Override
    public void initialize(DataManager manager, ARXConfiguration config) {
        // Nothing to do
    }

    @Override
    public boolean isAnonymous(Transformation<?> node, HashGroupifyEntry entry) {
        return true;
    }
    
    @Override
    public boolean isLocalRecodingSupported() {
        return true;
    }

    @Override
    public String toString() {
        return "Inclusion";
    }

    @Override
    public ElementData render() {
        ElementData result = new ElementData("Record selection");
        result.addProperty("Number of records", super.getDataSubset().getSize());
        return result;
    }
}
