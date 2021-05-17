package hr.fer.oprpp1.custom.scripting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexerException;import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexerState;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptToken;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptTokenType;



public class SmartScriptLexerTest {
	
	@Test
	public void testNotNull() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		assertNotNull(lexer.nextToken(), "Token was expected but null was returned.");
	}
	
	@Test
	public void testNullInput() {
		assertThrows(NullPointerException.class, () -> new SmartScriptLexer(null));
	}
	
	@Test
	public void testEmpty() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType(), "Empty input must generate only EOF token.");
	}
	
	@Test
	public void testGetReturnsLastNext() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		SmartScriptToken token = lexer.nextToken();
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
	}
	
	@Test
	public void testRadAfterEOF() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		lexer.nextToken();
		// will throw!
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	public void testNoActualContent() {
		SmartScriptLexer lexer = new SmartScriptLexer("   \r\n\t    ");
		
		assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType(), "Input had no content. Lexer should generated only EOF token.");
	}
	
	@Test
	public void testText() {

		SmartScriptLexer lexer = new SmartScriptLexer("  Stefanija\n  Automobil   ");

		SmartScriptToken correctData[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, "Stefanija Automobil"),
			new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testTextEscapeSequence() {
		SmartScriptLexer lexer = new SmartScriptLexer("  \\\\s  \\{  ");

		SmartScriptToken correctData[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, "\\s {"),
			new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testTagTextEscapeSequence() {
		SmartScriptLexer lexer = new SmartScriptLexer("= aaa \"sun\" $}");
		lexer.setState(SmartScriptLexerState.TAG);

		SmartScriptToken correctData[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, "="),
			new SmartScriptToken(SmartScriptTokenType.TEXT, "aaa"),
			new SmartScriptToken(SmartScriptTokenType.TEXT, "\"sun\""),
			new SmartScriptToken(SmartScriptTokenType.END, "$}"),
			new SmartScriptToken(SmartScriptTokenType.EOF, null)		
		};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testInvalidTextEscape() {
		SmartScriptLexer lexer = new SmartScriptLexer("   \\a    ");
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	public void testInvalidTagTextEscape() {
		SmartScriptLexer lexer = new SmartScriptLexer("   \\a    ");
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}
	
	private void checkTokenStream(SmartScriptLexer lexer, SmartScriptToken[] correctData) {
		int counter = 0;
		for(SmartScriptToken expected : correctData) {
			SmartScriptToken actual = lexer.nextToken();
			String msg = "Checking token "+counter + ":";
			assertEquals(expected.getType(), actual.getType(), msg);
			assertEquals(expected.getValue(), actual.getValue(), msg);
			counter++;
		}
	}
	
	
	
	

}
