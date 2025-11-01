package org.example.camera;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;
import org.xml.sax.SAXException;

public class CustomErrorHandler implements ErrorHandler {

    @Override
    public void warning(SAXParseException e) {
        System.err.println("[WARNING] " + e.getMessage());
        System.err.println("Line: " + e.getLineNumber() + ", Column: " + e.getColumnNumber());
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        System.err.println("[ERROR] " + e.getMessage());
        System.err.println("Line: " + e.getLineNumber() + ", Column: " + e.getColumnNumber());
        throw e;
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        System.err.println("[FATAL ERROR] " + e.getMessage());
        System.err.println("Line: " + e.getLineNumber() + ", Column: " + e.getColumnNumber());
        throw e;
    }
}



