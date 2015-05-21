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

@Mojo(name = "next")
public class NextMojo extends AbstractMojo {
	
	/**
     * @since <since-text>
     */
    @Parameter( property = "dotnet.qualifier", defaultValue = "", required = false )
    private String qualifier;
	
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
					if (ver != null && ver.endsWith(SNAPSHOT_SUFFIX)) {
						throw new MojoExecutionException("version " + ver + " is already an SNAPSHOT.");
					} else {
						String nextVersion = Utils.incrementLatest(ver, qualifier);
						Utils.writeLine(f, nextVersion + SNAPSHOT_SUFFIX);
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
