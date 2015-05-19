package it.ethiclab.dotnet;

import java.io.File;
import java.io.IOException;

public interface FileProcessor {
	void onFile(File f) throws IOException;
}
