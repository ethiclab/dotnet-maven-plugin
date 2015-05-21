package it.ethiclab.dotnet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Utils {
	public static String getDotNetCanonicalVersion(String ver) {
		StringBuilder sb = new StringBuilder();
		char[] arr = ver.toCharArray();
		int count = 0;
		for (char c : arr) {
			if (count == 4)
				break;
			if (Character.isDigit(c)) {
				sb.append(c);
			} else if (count < 3 && c == '.') {
				sb.append(c);
				count++;
			} else if (count == 3) {
				break;
			}
		}
		
		return sb.toString();
	}

	public static String readLine(File f) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		try
		{
			return br.readLine();
		}
		finally
		{
			br.close();
		}
	}

	public static void writeLine(File f, String releasedVersion) throws IOException {
		PrintWriter pw = new PrintWriter(f);
		try
		{
			pw.println(releasedVersion);
			pw.flush();
		}
		finally
		{
			pw.close();
		}
	}

	public static boolean endsWithNumber(String s) {
		char[] arr = s.toCharArray();
		boolean r = false;

		for (char c : arr) {
			r = Character.isDigit(c);
		}
		
		return r;
	}
}
