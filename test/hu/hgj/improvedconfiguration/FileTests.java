package hu.hgj.improvedconfiguration;

import hu.hgj.improvedconfiguration.resources.ResourceHelper;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class FileTests {

	@Test
	public void invalidFile() {
		Configuration configuration = null;
		try {
			configuration = ConfigurationParser.loadConfiguration(new File("foobarbaz"));
		} catch (IOException e) {
			// Nothing
		}
		assertNull(configuration);
	}

	@Test
	public void existingFile() {
		Configuration configuration = null;
		try {
			configuration = ConfigurationParser.loadConfiguration(new File(ResourceHelper.pathForResource("basic.conf")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertNotNull(configuration);
	}

}
