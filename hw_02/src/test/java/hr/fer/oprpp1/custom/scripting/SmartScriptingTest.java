package hr.fer.oprpp1.custom.scripting;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexerException;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptToken;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptTokenType;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;
import hr.fer.oprpp1.hw02.prob1.Lexer;
import hr.fer.oprpp1.hw02.prob1.Token;
import hr.fer.oprpp1.hw02.prob1.TokenType;

public class SmartScriptingTest {

	@Test
	public void ex1Test() {
		SmartScriptParser parser = new SmartScriptParser(readExample(1));
		DocumentNode doc = parser.getDocumentNode();

		assertEquals(TextNode.class, doc.getChild(0).getClass());
		assertEquals(1, doc.numberOfChildren());
	}

	@Test
	public void ex2Test() {
		SmartScriptParser parser = new SmartScriptParser(readExample(2));
		DocumentNode doc = parser.getDocumentNode();

		assertEquals(TextNode.class, doc.getChild(0).getClass());
		assertEquals(1, doc.numberOfChildren());
	}

	@Test
	void ex3Test() {
		SmartScriptParser parser = new SmartScriptParser(readExample(3));
		DocumentNode doc = parser.getDocumentNode();

		assertEquals(TextNode.class, doc.getChild(0).getClass());
		assertEquals(1, doc.numberOfChildren());
	}

	@Test
	void ex4Test() {
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(readExample(4)));
	}

	@Test
	void ex5Test() {
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(readExample(5)));
	}

	@Test
	void ex6Test() {
		SmartScriptParser parser = new SmartScriptParser(readExample(6));
		DocumentNode doc = parser.getDocumentNode();
		// System.out.println(doc.toString());
		assertEquals(TextNode.class, doc.getChild(0).getClass());
		assertEquals(2, doc.numberOfChildren());
	}

	@Test
	void ex7Test() {
		SmartScriptParser parser = new SmartScriptParser(readExample(7));
		DocumentNode doc = parser.getDocumentNode();
		assertEquals(TextNode.class, doc.getChild(0).getClass());
		assertEquals(2, doc.numberOfChildren());
	}

	@Test
	void ex8Test() {
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(readExample(8)));
	}

	@Test
	void ex9Test() {
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(readExample(9)));
	}
	
	@Test
	void ex10Test() {
		SmartScriptParser parser = new SmartScriptParser(readExample(10));
		DocumentNode doc = parser.getDocumentNode();
		assertEquals(0, doc.getChild(0).numberOfChildren());
		assertEquals(3, doc.getChild(1).numberOfChildren());
		assertEquals(0, doc.getChild(2).numberOfChildren());
		assertEquals(5, doc.getChild(3).numberOfChildren());
		assertEquals(4, doc.numberOfChildren());
	}

	private String readExample(int n) {
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/primjer" + n + ".txt")) {
			if (is == null)
				throw new RuntimeException("Datoteka extra/primjer" + n + ".txt je nedostupna.");
			byte[] data = is.readAllBytes();
			String text = new String(data, StandardCharsets.UTF_8);
			return text;
		} catch (IOException ex) {
			throw new RuntimeException("Greška pri čitanju datoteke.", ex);
		}
	}

}
