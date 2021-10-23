package io.dataspaceconnector.arx.certificate.elements;

import java.io.IOException;

import io.dataspaceconnector.arx.certificate.CertificateStyle;

import rst.pdfbox.layout.elements.Document;

/**
 * An abstract element
 * 
 * @author Fabian Prasser
 */
public interface Element {

    /**
     * Renders the element to the given target
     * @param target
     * @param indent
     * @param style
     * @throws IOException 
     */
    public abstract void render(Document target, int indent, CertificateStyle style) throws IOException;
    
}
