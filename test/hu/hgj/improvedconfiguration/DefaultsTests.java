package hu.hgj.improvedconfiguration;

import hu.hgj.improvedconfiguration.resources.ResourceHelper;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import static org.junit.Assert.*;

public class DefaultsTests {

	private static Configuration configuration = null;

	@BeforeClass
	public static void LoadConfigurations() throws IOException {
		configuration = ConfigurationParser.loadConfiguration(new File(ResourceHelper.pathForResource("defaults.conf")));
		assertNotNull(configuration);
	}

	@Test
	public void defaults() {
		HashMap<String, String> defaults = new HashMap<>();
		defaults.put("default0", "foobar0");
		defaults.put("default1", "foobar1");
		defaults.put("default2", "foobar2");
		defaults.put("default3", "foobar3");
		configuration.setDefaults(defaults);
		HashMap<String, String> expected = new HashMap<>();
		expected.put("default0", "foobar0");
		expected.put("default1", "default1");
		expected.put("default2", "default2");
		expected.put("default3", "foobar3");
		for (Entry<String, String> entry : expected.entrySet()) {
			assertEquals("Testing " + entry.getKey(), entry.getValue(), configuration.get(entry.getKey()));
		}
	}

}
