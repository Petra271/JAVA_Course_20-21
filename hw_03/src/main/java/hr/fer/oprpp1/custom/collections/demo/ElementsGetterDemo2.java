package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.Collection;
import hr.fer.oprpp1.custom.collections.ElementsGetter;
import hr.fer.oprpp1.custom.collections.LinkedListIndexedCollection;

public class ElementsGetterDemo2 {
	
	public static void main(String[] args) {
		
		//Collection col1 = new ArrayIndexedIndexedCollection();
		//Collection col2 = new ArrayIndexedIndexedCollection();
		
		Collection<String> col1 = new LinkedListIndexedCollection<>();
		Collection<String> col2 = new LinkedListIndexedCollection<>();
		
		col1.add("Ivo");
		col1.add("Ana");
		col1.add("Jasna");
		col2.add("Jasmina");
		col2.add("Stefanija");
		col2.add("Karmela");
		
		ElementsGetter<String> getter1 = col1.createElementsGetter();
		ElementsGetter<String> getter2 = col1.createElementsGetter();
		ElementsGetter<String> getter3 = col2.createElementsGetter();
		
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter2.getNextElement());
		System.out.println("Jedan element: " + getter3.getNextElement());
		System.out.println("Jedan element: " + getter3.getNextElement());


		
	}

}
