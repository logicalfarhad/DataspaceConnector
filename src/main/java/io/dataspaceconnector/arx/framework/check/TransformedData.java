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

package io.dataspaceconnector.arx.framework.check;

import io.dataspaceconnector.arx.framework.data.Data;

/**
 * Helper class to convey buffers.
 * 
 * @author Fabian Prasser
 * @author Florian Kohlmayer
 *
 */
public class TransformedData {

    /** The GH buffer */
    public Data                       bufferGeneralized;

    /** The OT buffer */
    public Data                       bufferMicroaggregated;

    /** The properties */
    public TransformationResult         properties;
    
    /**
     * Instantiate the helper object.
     * 
     * @param bufferGH
     * @param bufferOT
     */
    public TransformedData(Data bufferGH, Data bufferOT, TransformationResult properties) {
        this.bufferGeneralized = bufferGH;
        this.bufferMicroaggregated = bufferOT;
        this.properties = properties;
    }
}
