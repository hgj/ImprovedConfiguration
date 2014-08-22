package hu.hgj.improvedconfiguration;

import hu.hgj.improvedconfiguration.resources.ResourceHelper;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class ParseTests {

	private static Configuration basicConfiguration = null;

	@BeforeClass
	public static void LoadConfigurations() throws IOException {
		basicConfiguration = ConfigurationParser.loadConfiguration(new File(ResourceHelper.pathForResource("basic.conf")));
		assertNotNull(basicConfiguration);
	}

	@Test
	public void trimming() {
		for (int i = 1; i <= 4; i++) {
			assertEquals("Testing foo" + i, "foo", basicConfiguration.get("foo" + i));
		}
	}

	@Test
	public void comments() {
		for (int i = 1; i <= 3; i++) {
			assertEquals("Testing bar" + i, "bar", basicConfiguration.get("bar" + i));
		}
	}

	@Test
	public void quotes() {
		for (int i = 1; i <= 6; i++) {
			assertEquals("Testing baz" + i, "baz", basicConfiguration.get("baz" + i));
		}
	}

	@Test
	public void overwrite() {
		assertEquals("Testing overwrite", "over", basicConfiguration.get("over"));
	}

}
