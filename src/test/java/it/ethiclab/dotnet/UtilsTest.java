package it.ethiclab.dotnet;

import org.junit.Assert;
import org.junit.Test;

public class UtilsTest {

	@Test
	public void testGetDotNetCanonicalVersion() {
		String x = Utils.getDotNetCanonicalVersion("1.0.0.0");
		Assert.assertEquals("1.0.0.0", x);
	}
	
	@Test
	public void testGetDotNetCanonicalVersionSnapshot() {
		String x = Utils.getDotNetCanonicalVersion("1.0.0.0-SNAPSHOT");
		Assert.assertEquals("1.0.0.0", x);
	}
	
	@Test
	public void testGetDotNetCanonicalVersionQualifierSnapshot() {
		String x = Utils.getDotNetCanonicalVersion("1.0.0.0-RC1-SNAPSHOT");
		Assert.assertEquals("1.0.0.0", x);
	}
	
	@Test
	public void testGetDotNetCanonicalVersionMoreThanFour() {
		String x = Utils.getDotNetCanonicalVersion("1.0.0.0.0");
		Assert.assertEquals("1.0.0.0", x);
	}
}
