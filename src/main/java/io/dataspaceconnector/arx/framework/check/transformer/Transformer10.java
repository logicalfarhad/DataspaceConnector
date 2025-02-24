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

package io.dataspaceconnector.arx.framework.check.transformer;

import io.dataspaceconnector.arx.ARXConfiguration.ARXConfigurationInternal;
import io.dataspaceconnector.arx.framework.check.distribution.IntArrayDictionary;
import io.dataspaceconnector.arx.framework.data.DataMatrix;
import io.dataspaceconnector.arx.framework.data.GeneralizationHierarchy;

/**
 * The class Transformer10.
 * 
 * @author Fabian Prasser
 * @author Florian Kohlmayer
 */
public class Transformer10 extends AbstractTransformer {

    /**
     * Instantiates a new transformer.
     *
     * @param data the data
     * @param hierarchies the hierarchies
     * @param dataAnalyzed
     * @param dataAnalyzedNumberOfColumns
     * @param dictionarySensValue
     * @param dictionarySensFreq
     * @param config
     */
    public Transformer10(final DataMatrix data,
                         final GeneralizationHierarchy[] hierarchies,
                         final DataMatrix dataAnalyzed,
                         final int dataAnalyzedNumberOfColumns,
                         final IntArrayDictionary dictionarySensValue,
                         final IntArrayDictionary dictionarySensFreq,
                         final ARXConfigurationInternal config) {
        super(data, hierarchies, dataAnalyzed, dataAnalyzedNumberOfColumns, dictionarySensValue, dictionarySensFreq, config);
    }

    @Override
    protected void processAll() {
        for (int i = startIndex; i < stopIndex; i++) {
            // Transform
            buffer.setRow(i);
            data.setRow(i);
            buffer.setValueAtColumn(column0, hierarchy0[data.getValueAtColumn(column0)][level0]);
            buffer.setValueAtColumn(column1, hierarchy1[data.getValueAtColumn(column1)][level1]);
            buffer.setValueAtColumn(column2, hierarchy2[data.getValueAtColumn(column2)][level2]);
            buffer.setValueAtColumn(column3, hierarchy3[data.getValueAtColumn(column3)][level3]);
            buffer.setValueAtColumn(column4, hierarchy4[data.getValueAtColumn(column4)][level4]);
            buffer.setValueAtColumn(column5, hierarchy5[data.getValueAtColumn(column5)][level5]);
            buffer.setValueAtColumn(column6, hierarchy6[data.getValueAtColumn(column6)][level6]);
            buffer.setValueAtColumn(column7, hierarchy7[data.getValueAtColumn(column7)][level7]);
            buffer.setValueAtColumn(column8, hierarchy8[data.getValueAtColumn(column8)][level8]);
            buffer.setValueAtColumn(column9, hierarchy9[data.getValueAtColumn(column9)][level9]);
  
            // Call
            delegate.callAll(i, i);
        }
    }

    @Override
    protected void processGroupify() {
        while (element != null) {
            // Transform
            buffer.setRow(element.representative);
            data.setRow(element.representative);
            buffer.setValueAtColumn(column0, hierarchy0[data.getValueAtColumn(column0)][level0]);
            buffer.setValueAtColumn(column1, hierarchy1[data.getValueAtColumn(column1)][level1]);
            buffer.setValueAtColumn(column2, hierarchy2[data.getValueAtColumn(column2)][level2]);
            buffer.setValueAtColumn(column3, hierarchy3[data.getValueAtColumn(column3)][level3]);
            buffer.setValueAtColumn(column4, hierarchy4[data.getValueAtColumn(column4)][level4]);
            buffer.setValueAtColumn(column5, hierarchy5[data.getValueAtColumn(column5)][level5]);
            buffer.setValueAtColumn(column6, hierarchy6[data.getValueAtColumn(column6)][level6]);
            buffer.setValueAtColumn(column7, hierarchy7[data.getValueAtColumn(column7)][level7]);
            buffer.setValueAtColumn(column8, hierarchy8[data.getValueAtColumn(column8)][level8]);
            buffer.setValueAtColumn(column9, hierarchy9[data.getValueAtColumn(column9)][level9]);

            // Call
            delegate.callGroupify(element.representative, element);

            // Next element
            element = element.nextOrdered;
        }
    }

    @Override
    protected void processSnapshot() {

        startIndex *= ssStepWidth;
        stopIndex *= ssStepWidth;

        for (int i = startIndex; i < stopIndex; i += ssStepWidth) {
            // Transform
            buffer.setRow(snapshot[i]);
            data.setRow(snapshot[i]);
            buffer.setValueAtColumn(column0, hierarchy0[data.getValueAtColumn(column0)][level0]);
            buffer.setValueAtColumn(column1, hierarchy1[data.getValueAtColumn(column1)][level1]);
            buffer.setValueAtColumn(column2, hierarchy2[data.getValueAtColumn(column2)][level2]);
            buffer.setValueAtColumn(column3, hierarchy3[data.getValueAtColumn(column3)][level3]);
            buffer.setValueAtColumn(column4, hierarchy4[data.getValueAtColumn(column4)][level4]);
            buffer.setValueAtColumn(column5, hierarchy5[data.getValueAtColumn(column5)][level5]);
            buffer.setValueAtColumn(column6, hierarchy6[data.getValueAtColumn(column6)][level6]);
            buffer.setValueAtColumn(column7, hierarchy7[data.getValueAtColumn(column7)][level7]);
            buffer.setValueAtColumn(column8, hierarchy8[data.getValueAtColumn(column8)][level8]);
            buffer.setValueAtColumn(column9, hierarchy9[data.getValueAtColumn(column9)][level9]);

            // Call
            delegate.callSnapshot(snapshot[i], snapshot, i);
        }
    }
}
