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

package io.dataspaceconnector.arx.metric;

import io.dataspaceconnector.arx.ARXConfiguration;
import io.dataspaceconnector.arx.DataDefinition;
import io.dataspaceconnector.arx.framework.check.groupify.HashGroupify;
import io.dataspaceconnector.arx.framework.data.Data;
import io.dataspaceconnector.arx.framework.data.DataManager;
import io.dataspaceconnector.arx.framework.data.GeneralizationHierarchy;
import io.dataspaceconnector.arx.framework.lattice.Transformation;

/**
 * This class provides an abstract skeleton for the implementation of metrics.
 * 
 * @author Fabian Prasser
 * @author Florian Kohlmayer
 */
public abstract class MetricDefault extends Metric<InformationLossDefault> {

    /**  SVUID */
    private static final long serialVersionUID = 2672819203235170632L;

    /**
     * 
     * @param monotonicWithGeneralization
     * @param monotonicWithSuppression
     * @param independent
     */
    public MetricDefault(final boolean monotonicWithGeneralization, final boolean monotonicWithSuppression, final boolean independent) {
        super(monotonicWithGeneralization, monotonicWithSuppression, independent, 0.5d);
    }
    
    @Override
    public InformationLoss<?> createMaxInformationLoss() {
        return new InformationLossDefault(Double.MAX_VALUE);
    }

    @Override
    public InformationLoss<?> createMinInformationLoss() {
        return new InformationLossDefault(0d);
    }
    
    @Override
    protected InformationLossDefault getLowerBoundInternal(final Transformation<?> node) {
        return (InformationLossDefault)node.getLowerBound();
    }

    @Override
    protected InformationLossDefault getLowerBoundInternal(final Transformation<?> node, final HashGroupify groupify) {
        return (InformationLossDefault)node.getLowerBound();
    }
    
    @Override
    protected void initializeInternal(final DataManager manager,
                                      final DataDefinition definition, 
                                      final Data input, 
                                      final GeneralizationHierarchy[] hierarchies, 
                                      final ARXConfiguration config) {
        // Empty by design
    }
}
