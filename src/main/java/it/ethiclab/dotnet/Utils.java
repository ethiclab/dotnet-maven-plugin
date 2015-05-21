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
			} else if (c != '.') {
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

	public static String incrementLatest(String ver, String qualifier) {
		StringBuilder sb = new StringBuilder();
		
		String root = getDotNetCanonicalVersion(ver);
		boolean hasQualifier = ver.compareTo(root) != 0;
		
		String[] arr = root.split("\\.", 4);
		for (int i = 0; i < arr.length - 1; i++) {
			sb.append(arr[i]);
			sb.append('.');
		}
		
		if (hasQualifier) {
			String qualifierPart = ver.substring(root.length());
			if (endsWithNumber(qualifierPart)) {
				sb = new StringBuilder();
				String buildToIncrement = getLastNumber(qualifierPart);
				sb.append(root);
				sb.append(getQualifierPrefix(qualifierPart));
				sb.append(getFormattedBuildNumber(buildToIncrement));
			} else {
				String buildToIncrement = arr[arr.length - 1];
				sb.append(getFormattedBuildNumber(buildToIncrement));
				sb.append(qualifierPart);
			}
		} else {
			String buildToIncrement = arr[arr.length - 1];
			sb.append(getFormattedBuildNumber(buildToIncrement));
		}
		
		if (qualifier != null) {
			sb.append(qualifier);
		}
		
		return sb.toString();
	}
	
	public static String getFormattedBuildNumber(String s) {
		int buildNumber = Integer.parseInt(s);
		buildNumber++;
		if (s.startsWith("0")) {
			return String.format("%0" + s.length() + "d", buildNumber);
		} else {
			return "" + buildNumber;
		}
	}

	public static String getQualifierPrefix(String s) {
		String rev = reverse(s);
		char[] arr = rev.toCharArray();

		int remove = 0;
		
		for (char c : arr) {
			if(Character.isDigit(c)) {
				remove++;
			} else {
				break;
			}
		}
		
		return s.substring(0, s.length() - remove);
	}

	public static String getLastNumber(String s) {
		String rev = reverse(s);
		char[] arr = rev.toCharArray();
		StringBuilder sb = new StringBuilder();

		for (char c : arr) {
			if(Character.isDigit(c)) {
				sb.insert(0, c);
			} else {
				break;
			}
		}
		
		return sb.toString();
	}

	public static String reverse(String s) {
		char[] arr = s.toCharArray();
		char[] r = new char[arr.length];
		int j = arr.length;
		for (int i = 0; i < arr.length; i++) {
			j--;
			r[j] = arr[i];
		}
		return new String(r);
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
