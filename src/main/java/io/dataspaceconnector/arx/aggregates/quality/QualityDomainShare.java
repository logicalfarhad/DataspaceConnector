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
package io.dataspaceconnector.arx.aggregates.quality;

/**
 * Base class for representing domain shares in this package
 * 
 * @author Fabian Prasser
 */
public interface QualityDomainShare {

    /**
     * Returns the domain size
     * @return
     */
    public abstract double getDomainSize();
    
    /**
     * Returns the domain share
     * @param value
     * @param level
     * @return
     */
    public abstract double getShare(String value, int level);
}
