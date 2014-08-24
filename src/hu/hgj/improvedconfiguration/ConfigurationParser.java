//
// This file is part of the ImprovedConfiguration project.
// Visit the project's GitHub page for details:
//   https://github.com/hgj/ImprovedConfiguration
//
// This work is licensed under a Creative Commons
// Attribution-NonCommercial-ShareAlike 4.0 International License.
// See the LICENSE file or visit the license page for details.
//   http://creativecommons.org/licenses/by-nc-sa/4.0/
//

package hu.hgj.improvedconfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ConfigurationParser parses configuration files and procudes {@link
 * Configuration}.
 */
public class ConfigurationParser {

	private static Pattern keyValuePattern = Pattern.compile("\\s*(.*?)\\s*=\\s*(\"(?<I1>.*)\"|'(?<I2>.*)'|(?<I3>.*))\\s*");
	private static Pattern includePattern = Pattern.compile("\\s*include\\s*(\"(?<I1>.*)\"|'(?<I2>.*)'|(?<I3>.*))\\s*");

	private static HashSet<String> includedFiles = new HashSet<>();

	public static Configuration loadConfiguration(File file) throws IOException {
		includedFiles = new HashSet<>();
		Configuration configuration = new Configuration();
		// TODO: Parsing time?
		parseConfiguration(configuration, file);
		return configuration;
	}

	private static void parseConfiguration(Configuration configuration, File file) throws IOException {
		if (includedFiles.contains(file.getAbsolutePath())) {
			// TODO: Logger support
			//System.err.println("WARNING: Already included '" + file.getAbsolutePath() + "', skipping.");
			return;
		}
		BufferedReader reader = new BufferedReader(new FileReader(file));
		includedFiles.add(file.getAbsolutePath());
		for (String originalLine; (originalLine = reader.readLine()) != null; ) {
			// Trim the line
			String line = originalLine.trim();
			// Remove comments
			// TODO: Ability to escape hashmark
			line = line.replaceAll("#.*", "");
			// Drop if empty
			if (line.isEmpty()) {
				continue;
			}
			// The line should be 'key = value' or 'include file' format
			if (line.matches(".+=.+")) {
				Matcher keyValueMatcher = keyValuePattern.matcher(line);
				if (keyValueMatcher.matches()) {
					String key = keyValueMatcher.group(1);
					String value = null;
					for (String name : Arrays.asList("I1", "I2", "I3")) {
						value = keyValueMatcher.group(name);
						if (value != null) {
							break;
						}
					}
					if (value != null) {
						if (!key.isEmpty() && !value.isEmpty()) {
							configuration.set(key, value.trim());
							continue;
						}
					}
				}
			} else if (line.matches("\\s*include.+")) {
				Matcher includeFileMatcher = includePattern.matcher(line);
				if (includeFileMatcher.matches()) {
					String fileName = null;
					for (String name : Arrays.asList("I1", "I2", "I3")) {
						fileName = includeFileMatcher.group(name);
						if (fileName != null) {
							break;
						}
					}
					File filePath = null;
					if (fileName != null) {
						fileName = fileName.trim();
						filePath = new File(fileName);
						if (!filePath.isAbsolute()) {
							filePath = new File((new File(file.getAbsolutePath())).getParent() + File.separator + fileName);
						}
						parseConfiguration(configuration, filePath);
					}
					continue;
				}
			}
			// TODO: Logger support
			//System.err.println("WARNING: Invalid line '" + originalLine + "' in configuration file '" + file.getPath() + "'");
		}
	}

}
