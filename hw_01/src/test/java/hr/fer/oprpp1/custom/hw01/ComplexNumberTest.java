package hr.fer.oprpp1.custom.hw01;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import hr.fer.oprpp1.hw01.ComplexNumber;

public class ComplexNumberTest {
	
	@Test
	public void testFromReal() {
		assertEquals("5.00", ComplexNumber.fromReal(5).toString());
	}
	
	@Test
	public void testFromImaginary() {
		assertEquals("5.00i", ComplexNumber.fromImaginary(5).toString());
	}
	
	@Test
	public void testFromMagnitudeAndAngle() {
		assertEquals("1.35+1.48i", ComplexNumber.fromMagnitudeAndAngle(2, 0.83).toString());
	}
	
	@Test
	public void testParseIPlacedBeforeImaginaryPart() {
		assertThrows(NumberFormatException.class, () -> ComplexNumber.parse("i5"));
	}
	
	@Test 
	public void testParseMultipleSigns() {
		assertThrows(NumberFormatException.class, () -> ComplexNumber.parse("7+-5i"));
	}
	
	@Test 
	public void testParseInvalidCharacters() {
		assertThrows(NumberFormatException.class, () -> ComplexNumber.parse("7+4a"));
	}
	
	@Test
	public void testParseJustImaginaryPositive() {
		assertEquals(ComplexNumber.fromImaginary(1).toString(), ComplexNumber.parse("i").toString());
		assertEquals(ComplexNumber.fromImaginary(1).toString(), ComplexNumber.parse("+i").toString());
	}
	
	@Test
	public void testParseJustImaginaryNegative() {
		assertEquals(ComplexNumber.fromImaginary(-1).toString(), ComplexNumber.parse("-i").toString());
	}
	
	@Test
	public void testParseJustRealPositive() {
		assertEquals(ComplexNumber.fromReal(5).toString(), ComplexNumber.parse("+5").toString());
		assertEquals(ComplexNumber.fromReal(5).toString(), ComplexNumber.parse("5").toString());
	}
	
	@Test
	public void testParseJustRealNegative() {
		assertEquals(ComplexNumber.fromReal(-5).toString(), ComplexNumber.parse("-5").toString());
	}
	
	@Test 
	public void testParse() {
		assertEquals(new ComplexNumber(-5, 7).toString(), ComplexNumber.parse("-5+7i").toString());
		assertEquals(new ComplexNumber(-5, -7).toString(), ComplexNumber.parse("-5-7i").toString());
		assertEquals(new ComplexNumber(5, 7).toString(), ComplexNumber.parse("5+7i").toString());
		assertEquals(new ComplexNumber(5, -7).toString(), ComplexNumber.parse("5-7i").toString());
	}
	
	@Test
	public void testGetReal() {
		assertEquals((double)8.95, new ComplexNumber(8.95, 10).getReal());
	}
	
	@Test
	public void testGetImaginary() {
		assertEquals((double)10, new ComplexNumber(8.95, 10).getImaginary());
	}
	
	@Test
	public void testGetMagnitude() {
		assertEquals((double)8.60, (double) Math.round((new ComplexNumber(5, 7).getMagnitude()) * 100) / 100);
	}
	
	@Test
	public void testGetAngle() {
		assertEquals((double)0.95, (double) Math.round((new ComplexNumber(5, 7).getAngle()) * 100) / 100);
		assertEquals((double)0.79, (double) Math.round((new ComplexNumber(1, 1).getAngle()) * 100) / 100);
		assertEquals((double)5.5, (double) Math.round((new ComplexNumber(-1, 1).getAngle()) * 100) / 100);
		assertEquals((double)0.79, (double) Math.round((new ComplexNumber(-1, -1).getAngle()) * 100) / 100);
		assertEquals((double)5.5, (double) Math.round((new ComplexNumber(1, -1).getAngle()) * 100) / 100);
	}
	
	@Test
	public void testAdd() {
		assertEquals(new ComplexNumber(21, 15).toString(), new ComplexNumber(10, 2).add(
				new ComplexNumber(11, 13)).toString());
	}
	
	@Test
	public void testSub() {
		assertEquals(new ComplexNumber(-1, -5).toString(), new ComplexNumber(10, 8).sub(
				new ComplexNumber(11, 13)).toString());
	}
	
	@Test
	public void testMul() {
		assertEquals(new ComplexNumber(4, 17).toString(), new ComplexNumber(2, 1).mul(
				new ComplexNumber(5, 6)).toString());
	}
	
	@Test
	public void testDiv() {
		assertEquals(new ComplexNumber(-0.15, -2.77).toString(), new ComplexNumber(8, -6).div(
				new ComplexNumber(2, 3)).toString());
	}
	
	@Test
	public void testPower() {
		assertEquals(new ComplexNumber(154, 414).toString(), new ComplexNumber(7, 3).power(3).toString());
	}
	
	@Test
	public void testRoot() {
		String[] numbers = {new ComplexNumber(1.95, 0.26).toString(), 
				new ComplexNumber(-1.20, 1.56).toString(),
				new ComplexNumber(-0.75, -1.82).toString()};
		ComplexNumber[] roots = new ComplexNumber(7, 3).root(3);
		String[] rootsString = new String[3];
		int i = 0;
		for(ComplexNumber c : roots) {
			rootsString[i] = c.toString();
			i++;
		}
		assertArrayEquals(numbers, rootsString);
	}
	
	
	


}
