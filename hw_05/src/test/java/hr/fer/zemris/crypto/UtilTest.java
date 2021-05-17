package hr.fer.zemris.crypto;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw05.crypto.Util;

public class UtilTest {

	@Test
	public void testByteToHex() {
		assertEquals("01ae22", Util.bytetohex(new byte[] { 1, -82, 34 }));
		assertNotEquals("01ae22", Util.bytetohex(new byte[] { 1, -82, 3 }));
	}

	@Test
	public void testHexToByte() {
		assertArrayEquals(new byte[] { 1, -82, 34 }, Util.hextobyte("01aE22"));
	}

	@Test
	public void testUnevenLenghtShouldThrow() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("023"));
	}

	@Test
	public void testInvalidCharactersShouldThrow() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("02p"));
	}

}
