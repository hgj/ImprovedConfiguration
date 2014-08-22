package hu.hgj.improvedconfiguration.resources;

import java.io.File;

public class ResourceHelper {

	public static String pathForResource(String relativePath) {
		String testResourcesPath = "test/hu/hgj/improvedconfiguration/resources/".replaceAll("/", File.separator);
		return testResourcesPath + relativePath;
	}

}
