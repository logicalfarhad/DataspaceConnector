/*
 * ARX: Powerful Data Anonymization
 * Copyright 2012 - 2016 Fabian Prasser, Florian Kohlmayer and contributors
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
package io.dataspaceconnector.arx.certificate.elements;

import java.io.IOException;

import io.dataspaceconnector.arx.certificate.CertificateStyle;

import rst.pdfbox.layout.elements.ControlElement;
import rst.pdfbox.layout.elements.Document;

/**
 * PDF page break
 * 
 * @author Fabian Prasser
 */
public class ElementPageBreak implements Element {

    @Override
    public void render(Document target, int indent, CertificateStyle style) throws IOException {
        target.add(ControlElement.NEWPAGE);
    }
}
