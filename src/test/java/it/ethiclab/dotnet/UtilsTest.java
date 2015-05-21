package it.ethiclab.dotnet;

import org.junit.Assert;
import org.junit.Test;

public class UtilsTest {

	@Test
	public void test_getDotNetCanonicalVersion_beta() {
		String x = Utils.getDotNetCanonicalVersion("0.03.6049-beta-01");
		Assert.assertEquals("0.03.6049", x);
	}
	
	@Test
	public void test_getDotNetCanonicalVersion() {
		String x = Utils.getDotNetCanonicalVersion("1.0.0.0");
		Assert.assertEquals("1.0.0.0", x);
	}
	
	@Test
	public void test_getDotNetCanonicalVersion_Snapshot() {
		String x = Utils.getDotNetCanonicalVersion("1.0.0.0-SNAPSHOT");
		Assert.assertEquals("1.0.0.0", x);
	}
	
	@Test
	public void test_getDotNetCanonicalVersion_QualifierSnapshot() {
		String x = Utils.getDotNetCanonicalVersion("1.0.0.0-RC1-SNAPSHOT");
		Assert.assertEquals("1.0.0.0", x);
	}
	
	@Test
	public void test_getDotNetCanonicalVersionMoreThanFour() {
		String x = Utils.getDotNetCanonicalVersion("1.0.0.0.0");
		Assert.assertEquals("1.0.0.0", x);
	}

	@Test
	public void test_endsWithNumber_SingleDigit() {
		boolean x = Utils.endsWithNumber("RC1");
		Assert.assertEquals(true, x);
	}

	@Test
	public void test_endsWithNumber_False() {
		boolean x = Utils.endsWithNumber("RC");
		Assert.assertEquals(false, x);
	}

	@Test
	public void test_reverse() {
		String x = Utils.reverse("12345");
		Assert.assertEquals("54321", x);
	}

	@Test
	public void test_getLastNumber_SingleDigit() {
		String x = Utils.getLastNumber("RC1");
		Assert.assertEquals("1", x);
	}

	@Test
	public void test_getLastNumber_MultipleDigits() {
		String x = Utils.getLastNumber("RC12345");
		Assert.assertEquals("12345", x);
	}

	@Test
	public void test_getLastNumber_MultipleDigitsWithLeadingZeroes() {
		String x = Utils.getLastNumber("RC00012345");
		Assert.assertEquals("00012345", x);
	}
	
	@Test
	public void test_getQualifierPrefix_SingleDigit() {
		String x = Utils.getQualifierPrefix("RC1");
		Assert.assertEquals("RC", x);
	}

	@Test
	public void test_getQualifierPrefix_MultipleDigits() {
		String x = Utils.getQualifierPrefix("RC12345");
		Assert.assertEquals("RC", x);
	}
	
	@Test
	public void test_getQualifierPrefix_WithTrailingNumbers() {
		String x = Utils.getQualifierPrefix("R123C12345");
		Assert.assertEquals("R123C", x);
	}
	
	@Test
	public void test_getQualifierPrefix_beta() {
		String x = Utils.getQualifierPrefix("-beta-01");
		Assert.assertEquals("-beta-", x);
	}
	
	@Test
	public void test_incrementLatest_NoQualifier4() {
		String x = Utils.incrementLatest("1.0.0.0", "");
		Assert.assertEquals("1.0.0.1", x);
	}

	@Test
	public void test_incrementLatest_NoQualifier3() {
		String x = Utils.incrementLatest("1.0.0", "");
		Assert.assertEquals("1.0.1", x);
	}

	@Test
	public void test_incrementLatest_NoQualifier2() {
		String x = Utils.incrementLatest("1.0", "");
		Assert.assertEquals("1.1", x);
	}

	@Test
	public void test_incrementLatest_NoQualifier1() {
		String x = Utils.incrementLatest("1", "");
		Assert.assertEquals("2", x);
	}

	@Test
	public void test_incrementLatest_QualifierSingleDigit() {
		String x = Utils.incrementLatest("1.0.0.1-RC1", "");
		Assert.assertEquals("1.0.0.1-RC2", x);
	}

	@Test
	public void test_incrementLatest_QualifierTwoDigits() {
		String x = Utils.incrementLatest("1.0.0.1-RC19", "");
		Assert.assertEquals("1.0.0.1-RC20", x);
	}

	@Test
	public void test_incrementLatest_QualifierNoDigits() {
		String x = Utils.incrementLatest("1.0.0.1-RELEASE", "");
		Assert.assertEquals("1.0.0.2-RELEASE", x);
	}
	
	@Test
	public void test_incrementLatest_beta() {
		String x = Utils.incrementLatest("0.03.6049-beta-01", "");
		Assert.assertEquals("0.03.6049-beta-02", x);
	}
	
}
