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

/**
 * A privacy criterion that is implicitly bound to the quasi-identifiers.
 *
 * @author Fabian Prasser
 * @author Florian Kohlmayer
 */
public abstract class ImplicitPrivacyCriterion extends PrivacyCriterion {

    /** SVUID */
    private static final long serialVersionUID = -6467044039242481225L;

    /** 
     * Creates a new instance
     * @param monotonicWithSuppression
     * @param monotonicWithGeneralization
     */
    public ImplicitPrivacyCriterion(boolean monotonicWithSuppression,
                                    boolean monotonicWithGeneralization) {
        super(monotonicWithSuppression, monotonicWithGeneralization);
    }
}
