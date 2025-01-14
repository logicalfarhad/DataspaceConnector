/*
 * Copyright 2020 Fraunhofer Institute for Software and Systems Engineering
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.dataspaceconnector.common.ids.policy;

/**
 * This class provides a usage control framework enum.
 */
public enum UsageControlFramework {

    /**
     * Usage control (enforcement) inside the connector.
     */
    INTERNAL("INTERNAL"),

    /**
     * Usage control framework MyData.
     */
    MY_DATA("MY_DATA");

    /**
     * The usage control framework.
     */
    private final String framework;

    UsageControlFramework(final String string) {
        framework = string;
    }

    @Override
    public String toString() {
        return framework;
    }
}
