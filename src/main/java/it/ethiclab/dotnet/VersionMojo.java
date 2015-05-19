package it.ethiclab.dotnet;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name = "version")
public class VersionMojo extends AbstractMojo {
	@Parameter(defaultValue = "${project}", readonly = true)
	private MavenProject project;

	public void execute() throws MojoExecutionException
    {
    	Log log = getLog();
    	try {
    		log.info("VERSION BEGIN");
    		
    		log.info(project.getGroupId());
    		log.info(project.getArtifactId());
    		log.info(project.getVersion());
    		
    		List<String> sources = project.getCompileSourceRoots();
    		for(String s : sources) {
    			log.info("SOURCE ROOT " + s);
    		}
    		
    		log.info("BASE DIR = " + project.getBasedir());
    		
    		visit(project.getBasedir());
    		
    	} finally {
    		log.info("VERSION END");
    	}
    }

	private void visit(File f) {
		if (f.isDirectory()) {
			if (f.getName().compareToIgnoreCase("bin") == 0) {
				return;
			}
			if (f.getName().compareToIgnoreCase("obj") == 0) {
				return;
			}
			if (f.getName().compareToIgnoreCase("target") == 0) {
				return;
			}
			if (f.list() != null) {
				for (String s : f.list()) {
					File ff = Paths.get(f.getAbsolutePath(), s).toFile();
					if (ff.isFile()) {
						onFile(ff);
					} else {
						visit(ff);
					}
				}
			}
		}
	}

	private void onFile(File f) {
		if (f.getName().endsWith(".cs")) {
			getLog().info("processing " + f.getAbsolutePath());
		}
	}
}
