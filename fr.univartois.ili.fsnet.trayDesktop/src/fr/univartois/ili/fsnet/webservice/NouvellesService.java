/**
 * NouvellesService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fr.univartois.ili.fsnet.webservice;

public interface NouvellesService extends javax.xml.rpc.Service {
    public java.lang.String getNouvellesInformationsPortAddress();

    public fr.univartois.ili.fsnet.webservice.NouvellesInformations getNouvellesInformationsPort() throws javax.xml.rpc.ServiceException;

    public fr.univartois.ili.fsnet.webservice.NouvellesInformations getNouvellesInformationsPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
