package hr.fer.zemris.java.hw05.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * The class is used for digesting, encrypting and decrypting files.
 * 
 * @author Petra
 *
 */
public class Crypto {

	/**
	 * Main method
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		if (args.length != 2 && args.length != 3)
			throw new IllegalArgumentException("Input is invalid.\nThe expected input is:\n"
					+ "1) calculating the SHA-256 file digest: 'checksha <file_name>'\n"
					+ "2) encryption: 'encrypt <input_file> <output_file>')\n"
					+ "3) decryption: 'decrypt <input_file> <output_file>')");

		Scanner scanner = null;
		try {
			scanner = new Scanner(System.in);
			switch (args[0]) {
			case "checksha" -> {
				if (args.length != 2) {
					System.out.println("The expected input is: 'checksha <file_name>'");
					System.exit(1);
				}

				System.out.print("Please provide expected sha-256 digest for hw05test.bin:\n> ");
				checksha(args[1], scanner.nextLine());
			}
			case "encrypt", "decrypt" -> {
				if (args.length != 3) {
					System.out.println(
							"The expected input is: 'encrypt <input_file> <output_file>' or 'decrypt <input_file> <output_file>'");
					System.exit(1);
				}

				System.out.print("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):\n> ");
				String password = scanner.nextLine();
				System.out.print("Please provide initialization vector as hex-encoded text (32 hex-digits):\n> ");
				String vector = scanner.nextLine();

				crypt(args[0], args[1], args[2], password, vector);
			}
			default -> throw new IllegalArgumentException("Unexpected value: " + args[0]);
			}
		} finally {
			scanner.close();
		}

	}

	/**
	 * Calculates file checksum and checks if the result equals the given checksum.
	 * 
	 * @param file     file whose checksum will be calculated
	 * @param checksum expected checksum
	 */
	public static void checksha(String file, String checksum) {

		try {
			InputStream iStream = Files.newInputStream(Paths.get(file));
			byte[] buff = new byte[4096];
			MessageDigest md = MessageDigest.getInstance("SHA-256");

			while (true) {
				int r = iStream.read(buff);
				if (r < 1)
					break;
				md.update(buff, 0, r);
			}

			String calculated = Util.bytetohex(md.digest());

			if (checksum.equals(calculated))
				System.out.println("Digesting completed. Digest of " + file + " matches expected digest.");
			else
				System.out.println("Digesting completed. Digest of " + file + " does not match the expected digest.\n"
						+ "Digest was: " + calculated);

		} catch (IOException | NoSuchAlgorithmException e) {
			System.err.println("An error has occurred while calculating the SHA-256 file digest.");
			System.exit(1);
		}

	}

	/**
	 * Encrypts and decrypts files.
	 * 
	 * @param type       encryption or decryption
	 * @param inputFile  input file
	 * @param outputFile output file
	 * @param password   key
	 * @param vector     initialization vector
	 */
	public static void crypt(String type, String inputFile, String outputFile, String password, String vector) {

		boolean encrypt = type.equals("encrypt");
		try {
			InputStream iStream = Files.newInputStream(Paths.get(inputFile));
			OutputStream oStream = Files.newOutputStream(Paths.get(outputFile), StandardOpenOption.CREATE);
			byte[] buff = new byte[4096];

			SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(password), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(vector));

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);

			while (true) {
				int r = iStream.read(buff);
				if (r < 1)
					break;
				oStream.write(cipher.update(buff, 0, r));
			}

			oStream.write(cipher.doFinal());

			System.out.printf("%s completed. Generated file " + outputFile + " based on file " + inputFile,
					encrypt ? "Encryption" : "Decryption");

		} catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			System.err.printf("An error has occurred during %s.", encrypt ? "encryption" : "decryption");
			System.exit(1);
		}

	}

}
