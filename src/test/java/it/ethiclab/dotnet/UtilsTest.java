package it.ethiclab.dotnet;

import org.junit.Assert;
import org.junit.Test;

public class UtilsTest {

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
}
