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

import java.util.Date;

import io.dataspaceconnector.arx.DataType;
import io.dataspaceconnector.arx.DataType.ARXDate;
import io.dataspaceconnector.arx.DataType.ARXDecimal;
import io.dataspaceconnector.arx.DataType.ARXInteger;
import io.dataspaceconnector.arx.DataType.DataTypeWithRatioScale;

/**
 * Parser for values
 * 
 * @author Fabian Prasser
 */
class QualityConfigurationValueParser<T> {
    
    /**
     * Returns a new instance
     * @param type
     * @return
     */
    static QualityConfigurationValueParser<?> create(DataType<?> datatype) {
        if (datatype instanceof ARXDecimal) {
            return new QualityConfigurationValueParser<Double>((ARXDecimal)datatype);
        } else if (datatype instanceof ARXInteger) {
            return new QualityConfigurationValueParser<Long>((ARXInteger)datatype);
        } else if (datatype instanceof ARXDate) {
            return new QualityConfigurationValueParser<Date>((ARXDate)datatype); 
        }
        throw new IllegalArgumentException("Unknown data type");
    }
    
    
    /** Data type*/
    private final DataTypeWithRatioScale<T> type;

    /**
     * Creates a new instance
     * @param type
     */
    QualityConfigurationValueParser(DataTypeWithRatioScale<T> type) {
        this.type = type;
    }

    /**
     * Returns whether the value is accepted
     * @param value
     * @return
     */
    boolean accepts(String value) {
        return type.isValid(value);
    }

    /**
     * Converts a value into a double
     * @param value
     * @return
     */
    double getDouble(String value) {

        // Silently fall back to 0 for NULL values
        if (value == null) {
            return 0d;
        }
        
        // Parse
        Double result = type.toDouble(type.parse(value));
        
        // Silently fall back to 0 for NULL values
        return result != null ? result : 0d;
    }
}
