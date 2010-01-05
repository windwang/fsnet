package test;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import fr.univartois.ili.fsnet.webservice.NouvellesInformations;
import fr.univartois.ili.fsnet.webservice.NouvellesService;
import fr.univartois.ili.fsnet.webservice.NouvellesServiceLocator;

public class Test {
	public static void main(String[]args) throws ServiceException, RemoteException{
	NouvellesService nv =new NouvellesServiceLocator();
	NouvellesInformations nvs=nv.getNouvellesInformationsPort();
	System.out.println(nvs.getEvenement());
	
	
	}

}
