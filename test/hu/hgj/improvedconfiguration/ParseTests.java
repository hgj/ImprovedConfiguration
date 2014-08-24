package hu.hgj.improvedconfiguration;

import hu.hgj.improvedconfiguration.resources.ResourceHelper;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class ParseTests {

	private static Configuration configuration = null;

	@BeforeClass
	public static void LoadConfigurations() throws IOException {
		configuration = ConfigurationParser.loadConfiguration(new File(ResourceHelper.pathForResource("basic.conf")));
		assertNotNull(configuration);
	}

	@Test
	public void trimming() {
		for (int i = 1; i <= 4; i++) {
			assertEquals("Testing foo" + i, "foo", configuration.get("foo" + i));
		}
	}

	@Test
	public void comments() {
		for (int i = 1; i <= 3; i++) {
			assertEquals("Testing bar" + i, "bar", configuration.get("bar" + i));
		}
	}

	@Test
	public void quotes() {
		for (int i = 1; i <= 6; i++) {
			assertEquals("Testing baz" + i, "baz", configuration.get("baz" + i));
		}
	}

	@Test
	public void overwrite() {
		assertEquals("Testing overwrite", "over", configuration.get("over"));
	}

}
