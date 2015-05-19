package it.ethiclab.dotnet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
	
	/**
     * @since <since-text>
     */
    @Parameter( property = "dotnet.newVersion", required = true )
    private String newVersion;
	
	@Parameter(defaultValue = "${project}", readonly = true)
	private MavenProject project;

	private static final String av = "[assembly: AssemblyVersion(\"";
	private static final String afv = "[assembly: AssemblyFileVersion(\"";
	private static final String aiv = "[assembly: AssemblyInformationalVersion(\"";
	private static final String suffix = "\")]";
	
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
    		
    		try {
				visit(project.getBasedir(), new FileProcessor() {
					
					@Override
					public void onFile(File f) throws IOException {
						processSourceFile(f);
					}
				});
			} catch (IOException e) {
				throw new MojoExecutionException(e.toString(), e);
			}
    		
    		try {
				visit(project.getBasedir(), new FileProcessor() {
					
					@Override
					public void onFile(File f) throws IOException {
						processVersionChangedFile(f);
					}
				});
			} catch (IOException e) {
				throw new MojoExecutionException(e.toString(), e);
			}
    		
    		/**
    		 * TODO: on commit like versions:commit
    		 */
    		try {
				visit(project.getBasedir(), new FileProcessor() {
					
					@Override
					public void onFile(File f) throws IOException {
						processVersionBackupFile(f);
					}
				});
			} catch (IOException e) {
				throw new MojoExecutionException(e.toString(), e);
			}
    		
    	} finally {
    		log.info("VERSION END");
    	}
    }

	private void visit(File f, FileProcessor fp) throws IOException {
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
						fp.onFile(ff);
					} else {
						visit(ff, fp);
					}
				}
			}
		}
	}

	private void processSourceFile(File f) throws IOException {
		if (f.getName().endsWith(".cs")) {
			getLog().info("processing " + f.getAbsolutePath());
			if (hasAssemblyVersionInfo(f))
			{
				changeVersion(f);
			}
		}
	}
	
	private void processVersionBackupFile(File f) throws IOException {
		if (f.getName().endsWith(".versionsBackup")) {
			String fap = f.getAbsolutePath();
			getLog().info("processing " + fap);
			f.delete();
		}
	}
	
	private void processVersionChangedFile(File f) throws IOException {
		if (f.getName().endsWith(".versionsChanged")) {
			String fap = f.getAbsolutePath();
			getLog().info("processing " + fap);
			File ff = new File(fap.substring(0, fap.lastIndexOf('.')));
			f.renameTo(ff);
		}
	}

	private void changeVersion(File f) throws IOException {
		File backup = new File(f.getAbsolutePath() + ".versionsBackup");
		File changed = new File(f.getAbsolutePath() + ".versionsChanged");
		f.renameTo(backup);
		changeVersion(changed, backup);
	}

	private void changeVersion(File f, File backup) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(backup)));
		PrintWriter pw = new PrintWriter(f, project.getProperties().getProperty("project.build.sourceEncoding", "UTF-8"));
		try
		{
			String line = null;
			do {
				line = br.readLine();
				if (line != null) {
					if (line.indexOf(av) >= 0) {
						pw.println(av + getDotNetCanonicalVersion(newVersion) + suffix);
						getLog().info(line);
					}
					else if (line.indexOf(afv) >= 0) {
						pw.println(afv + getDotNetCanonicalVersion(newVersion) + suffix);
						getLog().info(line);
					}
					else if (line.indexOf(aiv) >= 0) {
						pw.println(aiv + newVersion + suffix);
						getLog().info(line);
					} else {
						pw.println(line);
					}
				}
				
			} while (line != null);
			pw.flush();
		}
		finally
		{
			br.close();
			pw.close();
		}
	}

	private String getDotNetCanonicalVersion(String ver) {
		StringBuilder sb = new StringBuilder();
		char[] arr = ver.toCharArray();
		int count = 0;
		for (char c : arr) {
			if (count == 4)
				break;
			if (Character.isDigit(c)) {
				sb.append(c);
			} else if (c == '.') {
				sb.append(c);
				count++;
			}
		}
		
		return sb.toString();
	}

	private boolean hasAssemblyVersionInfo(File f) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		//PrintWriter pw = new PrintWriter(f.getAbsolutePath() + ".work", project.getProperties().getProperty("project.build.sourceEncoding", "UTF-8"));
		try
		{
			String line = null;
			do {
				line = br.readLine();
				if (line != null) {
					if (line.indexOf(av) >= 0) {
						getLog().info(line);
						return true;
					}
					else if (line.indexOf(afv) >= 0) {
						getLog().info(line);
						return true;
					}
					else if (line.indexOf(aiv) >= 0) {
						getLog().info(line);
						return true;
					}
					//pw.println(line);
				}
				
			} while (line != null);
			//pw.flush();
			return false;
		}
		finally
		{
			br.close();
			//pw.close();
		}
	}
}
