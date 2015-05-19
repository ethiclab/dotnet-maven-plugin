package it.ethiclab.dotnet;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import static java.lang.System.out;

@Mojo( name = "init", defaultPhase = LifecyclePhase.PROCESS_SOURCES )
public class InitMojo extends AbstractMojo
{
    public void execute() throws MojoExecutionException
    {
    	out.println("execute " + this.getClass());
    }
}
