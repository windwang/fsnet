package fr.univartois.ili.fsnet.fakeDB;

public interface MockLocator {
	
	public Mock locateMock(Class<? extends Mock> clazz);

}
