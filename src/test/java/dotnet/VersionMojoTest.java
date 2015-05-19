package dotnet;

import org.junit.Assert;
import org.junit.Test;

import it.ethiclab.dotnet.VersionMojo;

public class VersionMojoTest {

	@Test
	public void testGetDotNetCanonicalVersion() {
		VersionMojo versionMojo = new VersionMojo();
		String x = versionMojo.getDotNetCanonicalVersion("1.0.0.0");
		Assert.assertEquals("1.0.0.0", x);
	}
	
	@Test
	public void testGetDotNetCanonicalVersionSnapshot() {
		VersionMojo versionMojo = new VersionMojo();
		String x = versionMojo.getDotNetCanonicalVersion("1.0.0.0-SNAPSHOT");
		Assert.assertEquals("1.0.0.0", x);
	}
	
	@Test
	public void testGetDotNetCanonicalVersionQualifierSnapshot() {
		VersionMojo versionMojo = new VersionMojo();
		String x = versionMojo.getDotNetCanonicalVersion("1.0.0.0-RC1-SNAPSHOT");
		Assert.assertEquals("1.0.0.0", x);
	}
	
	@Test
	public void testGetDotNetCanonicalVersionMoreThanFour() {
		VersionMojo versionMojo = new VersionMojo();
		String x = versionMojo.getDotNetCanonicalVersion("1.0.0.0.0");
		Assert.assertEquals("1.0.0.0", x);
	}
}
