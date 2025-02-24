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

package io.dataspaceconnector.arx.metric.v2;

import io.dataspaceconnector.arx.metric.InformationLossWithBound;

/**
 * Information loss with a potential lower bound.
 *
 * @author Fabian Prasser
 */
public class ILSingleDimensionalWithBound extends InformationLossWithBound<ILSingleDimensional> {

    /**
     * Creates a new instance without a lower bound.
     *
     * @param informationLoss
     */
    public ILSingleDimensionalWithBound(double informationLoss) {
        super(new ILSingleDimensional(informationLoss));
    }

    /**
     * Creates a new instance.
     *
     * @param informationLoss
     * @param lowerBound
     */
    public ILSingleDimensionalWithBound(double informationLoss,
                                       double lowerBound) {
        super(new ILSingleDimensional(informationLoss), new ILSingleDimensional(lowerBound));
    }
}
