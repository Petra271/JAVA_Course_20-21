package hr.fer.zemris.java.hw05.crypto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A utility class for {@link Crypto}. Transforms byte arrays to Strings and
 * Strings to byte arrays.
 * 
 * @author Petra
 *
 */
public class Util {

	/**
	 * Takes byte array and turns it into a String.
	 * 
	 * @param bytes input byte array
	 * @return string
	 */
	public static String bytetohex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();

		for (byte b : bytes) {
			String s = String.format("%02x", b);
			sb.append(s);
		}

		return sb.toString();

	}

	/**
	 * Takes a String and turns it into a byte array.
	 * 
	 * @param string input string
	 * @return byte array
	 */
	public static byte[] hextobyte(String string) {
		if (string.length() % 2 != 0)
			throw new IllegalArgumentException("Key length is not an even number.");

		Pattern pattern = Pattern.compile("[0-9a-fA-F]+");
		Matcher matcher = pattern.matcher(string);
		if (!matcher.matches())
			throw new IllegalArgumentException("The given key contains invalid characters.");

		if (string.length() == 0)
			return new byte[0];

		byte[] bytes = new byte[string.length() / 2];
		for (int i = 0; i < string.length(); i += 2) {
			byte firstPart = (byte) (Character.digit(string.charAt(i), 16) << 4);
			byte secondPart = (byte) (Character.digit(string.charAt(i + 1), 16));
			bytes[i / 2] = (byte) (firstPart + secondPart);
		}

		return bytes;

	}
}
