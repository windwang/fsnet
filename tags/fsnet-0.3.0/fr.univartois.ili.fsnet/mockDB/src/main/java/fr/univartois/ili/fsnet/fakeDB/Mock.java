package fr.univartois.ili.fsnet.fakeDB;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;

public abstract class Mock {

	public abstract void create(EntityManager em);
	
	public abstract void link(EntityManager em, MockLocator locator);
	
	private static Random rand = new Random(new Date().getTime());
	
	public List<String> generateIdentifiers(int size, String seed) {
		List<String> results = new ArrayList<String>();
		for (int i = 0 ; i < size ; i++) {
			results.add(seed + i);
		}
		return results;
	}
	
	public int getRandomInt() {
		return rand.nextInt();
	}
	
	public boolean getRandomBoolean() {
		return rand.nextBoolean();
	}
	
}
