package fr.univartois.ili.fsnet.webservice;

public class NouvellesInformationsProxy implements fr.univartois.ili.fsnet.webservice.NouvellesInformations {
  private String _endpoint = null;
  private fr.univartois.ili.fsnet.webservice.NouvellesInformations nouvellesInformations = null;
  
  public NouvellesInformationsProxy() {
    _initNouvellesInformationsProxy();
  }
  
  public NouvellesInformationsProxy(String endpoint) {
    _endpoint = endpoint;
    _initNouvellesInformationsProxy();
  }
  
  private void _initNouvellesInformationsProxy() {
    try {
      nouvellesInformations = (new fr.univartois.ili.fsnet.webservice.NouvellesServiceLocator()).getNouvellesInformationsPort();
      if (nouvellesInformations != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)nouvellesInformations)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)nouvellesInformations)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (nouvellesInformations != null)
      ((javax.xml.rpc.Stub)nouvellesInformations)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public fr.univartois.ili.fsnet.webservice.NouvellesInformations getNouvellesInformations() {
    if (nouvellesInformations == null)
      _initNouvellesInformationsProxy();
    return nouvellesInformations;
  }
  
  public int getNumberOfNewEvents() throws java.rmi.RemoteException{
    if (nouvellesInformations == null)
      _initNouvellesInformationsProxy();
    return nouvellesInformations.getNumberOfNewEvents();
  }
  
  public int getNumberOfNewAnnonce() throws java.rmi.RemoteException{
    if (nouvellesInformations == null)
      _initNouvellesInformationsProxy();
    return nouvellesInformations.getNumberOfNewAnnonce();
  }
  
  
}