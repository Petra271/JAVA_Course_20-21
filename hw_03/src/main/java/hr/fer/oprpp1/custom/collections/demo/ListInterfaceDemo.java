package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.Collection;
import hr.fer.oprpp1.custom.collections.LinkedListIndexedCollection;
import hr.fer.oprpp1.custom.collections.List;

public class ListInterfaceDemo {

	public static void main(String[] args) {
		
		List<String> col1 = new ArrayIndexedCollection<>();
		List<String> col2 = new LinkedListIndexedCollection<>();
		
		col1.add("Ivana");
		col2.add("Jasna");
		
		Collection<String> col3 = col1;
		Collection<String> col4 = col2;
		
		col1.get(0);
		col2.get(0);
		//col3.get(0); neće se prevesti! Razumijete li zasto?
		//col4.get(0); neće se prevesti! Razumijete li zasto?
		// neće se prevesti zato što objekte tipa ArrayLinkedCollection
		// i LinkedListCollection gledamo "kroz naočale" sučelja Collection,
		// a to sučelje nema metodu get(int index)
		
		col1.forEach(System.out::println); // Ivana
		col2.forEach(System.out::println); // Jasna
		col3.forEach(System.out::println); // Ivana
		col4.forEach(System.out::println); // Jasna
	}

}
