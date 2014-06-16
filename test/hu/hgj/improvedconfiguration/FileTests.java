package hu.hgj.improvedconfiguration;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class FileTests {

	static String testResourcesPath = "test/hu/hgj/improvedconfiguration/resources/".replaceAll("/", File.separator);
	static String basicFilePath = testResourcesPath + "basic.conf";

	@Test
	public void InvalidFileTest() {
		Configuration configuration = null;
		try {
			ConfigurationParser.loadConfiguration(new File("foobarbaz"));
		} catch (IOException e) {
			// Nothing
		}
		assertNull(configuration);
	}

	@Test
	public void ExistingFileTest() {
		Configuration configuration = null;
		try {
			configuration = ConfigurationParser.loadConfiguration(new File(basicFilePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertNotNull(configuration);
	}

}
