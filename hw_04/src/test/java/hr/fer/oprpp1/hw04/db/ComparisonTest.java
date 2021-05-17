package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ComparisonTest {
	
	 @Test
	    public void lessTest() {
	        assertTrue(ComparisonOperators.LESS.satisfied("Ana", "Marija"));
	        assertFalse(ComparisonOperators.LESS.satisfied("Ivan", "A"));
	    }

	    @Test
	    public void lessOrEqualsTest() {
	        assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("Ana", "Ana"));
	        assertFalse(ComparisonOperators.LESS_OR_EQUALS.satisfied("Tina", "Anamarija"));
	    }

	    @Test
	    public void greaterTest() {
	        assertTrue(ComparisonOperators.GREATER.satisfied("abcd", "a"));
	        assertFalse(ComparisonOperators.GREATER.satisfied("a", "abcd"));
	    }

	    @Test
	    public void greaterEqualsTest() {
	        assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied("abc", "abc"));
	        assertFalse(ComparisonOperators.GREATER_OR_EQUALS.satisfied("a", "aaa"));
	    }

	    @Test
	    public void equalsTest() {
	        assertTrue(ComparisonOperators.EQUALS.satisfied("Ana", "Ana"));
	        assertFalse(ComparisonOperators.EQUALS.satisfied("a", "b"));
	    }

	    @Test
	    public void notEqualsTest() {
	        assertTrue(ComparisonOperators.NOT_EQUALS.satisfied("Ana", "Tina"));
	        assertFalse(ComparisonOperators.NOT_EQUALS.satisfied("Ana", "Ana"));
	    }

	    @Test
	    public void likeTest() {
	        assertTrue(ComparisonOperators.LIKE.satisfied("Tina", "T*"));
	        assertFalse(ComparisonOperators.LIKE.satisfied("AAA", "AA*AA"));
	        assertTrue(ComparisonOperators.LIKE.satisfied("AAAA", "AA*AA"));
	    }

}
