package it.ethiclab.dotnet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name = "release")
public class ReleaseMojo extends AbstractMojo {
	
	/**
     * @since <since-text>
     */
    @Parameter( property = "dotnet.removeQualifier", defaultValue = "false", required = false )
    private boolean removeQualifier;
	
	@Parameter(defaultValue = "${project}", readonly = true)
	private MavenProject project;

	private static final String versionfile = "version.txt";
	private static final String SNAPSHOT_SUFFIX = "-SNAPSHOT";
	
	public void execute() throws MojoExecutionException
    {
    	Log log = getLog();
    	try {
    		log.info("RELEASE BEGIN");
    		
    		log.info("BASE DIR = " + project.getBasedir());
    		
    		File f = Paths.get(project.getBasedir().getAbsolutePath(), versionfile).toFile();
    		
    		if (f.exists()) {
				try {
					String ver = Utils.readLine(f);
	    			String versionRoot = Utils.getDotNetCanonicalVersion(ver);
					if (ver != null && ver.endsWith(SNAPSHOT_SUFFIX)) {
						String releasedVersion = ver.substring(0, ver.indexOf(SNAPSHOT_SUFFIX));
						if (removeQualifier) {
							releasedVersion = versionRoot;
						}
						Utils.writeLine(f, releasedVersion);
					} else {
						throw new MojoExecutionException("version " + ver + " is not an SNAPSHOT.");
					}
				} catch (IOException e) {
					throw new MojoExecutionException(e.toString(), e);
				}
    		}
    		
    	} finally {
    		log.info("RELEASE END");
    	}
    }
}
