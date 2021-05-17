package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class LexerTest {
	
	@Test
	public void testNotNull() {
		QueryLexer lexer = new QueryLexer("");
		assertNotNull(lexer.nextToken(), "Token was expected but null was returned.");
	}
	
	@Test
	public void testNullInput() {
		assertThrows(NullPointerException.class, () -> new QueryLexer(null));
	}
	
	@Test
	public void testEmpty() {
		 QueryLexer lexer = new  QueryLexer("");
		
		assertEquals( QueryTokenType.EOF, lexer.nextToken().getType(), "Empty input must generate only EOF token.");
	}
	
	@Test
	public void testGetReturnsLastNext() {
		 QueryLexer lexer = new  QueryLexer("");
		
		 QueryToken token = lexer.nextToken();
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
	}
	
	@Test
	public void testRadAfterEOF() {
		QueryLexer lexer = new QueryLexer("");
		lexer.nextToken();
		// will throw!
		assertThrows(QueryLexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	public void testNoActualContent() {
		QueryLexer lexer = new QueryLexer("   \r\n\t    ");
		
		assertEquals(QueryTokenType.EOF, lexer.nextToken().getType(), "Input had no content. Lexer should generated only EOF token.");
	}
	
	@Test
	public void testText() {

		QueryLexer lexer = new QueryLexer("  aaaa=\"ana\"  >= pas ");

		QueryToken correctData[] = {
			new QueryToken(QueryTokenType.FIELD, "aaaa"),
			new QueryToken(QueryTokenType.OPERATOR, "="),
			new QueryToken(QueryTokenType.VALUE, "\"ana\""),
			new QueryToken(QueryTokenType.OPERATOR, ">="),
			new QueryToken(QueryTokenType.FIELD, "pas"),
		};

		checkTokenStream(lexer, correctData);
	}
	
	
	private void checkTokenStream(QueryLexer lexer, QueryToken[] correctData) {
		int counter = 0;
		for(QueryToken expected : correctData) {
			QueryToken actual = lexer.nextToken();
			String msg = "Checking token "+counter + ":";
			assertEquals(expected.getType(), actual.getType(), msg);
			assertEquals(expected.getValue(), actual.getValue(), msg);
			counter++;
		}
	}
}