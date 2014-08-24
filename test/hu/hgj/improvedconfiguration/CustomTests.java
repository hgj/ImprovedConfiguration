package hu.hgj.improvedconfiguration;

import hu.hgj.improvedconfiguration.resources.ResourceHelper;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CustomTests {

	private static Configuration configuration = null;

	@BeforeClass
	public static void LoadConfigurations() throws IOException {
		configuration = ConfigurationParser.loadConfiguration(new File(ResourceHelper.pathForResource("custom.conf")));
		assertNotNull(configuration);
	}

	@Test
	public void customStrings() {
		HashMap<String, String> dictionary = new HashMap<>();
		dictionary.put("foo", "bar");
		String stringValue;
		int i = 1;
		while ((stringValue = configuration.getCustom("foo" + i, dictionary, false)) != null) {
			assertEquals("Testing custom string foo" + i, "bar", stringValue);
			i++;
		}
	}

	@Test
	public void customStringsRegexp() {
		HashMap<String, String> dictionary = new HashMap<>();
		dictionary.put("(?iu).*bar.*", "foo");
		String stringValue;
		int i = 1;
		while ((stringValue = configuration.getCustom("bar" + i, dictionary, true)) != null) {
			assertEquals("Testing custom string (with regexp) bar" + i, "foo", stringValue);
			i++;
		}
	}

	@Test
	public void customIntegers() {
		HashMap<String, Integer> dictionary = new HashMap<>();
		dictionary.put("foo", 1);
		Integer integerValue;
		int i = 1;
		while ((integerValue = configuration.getCustomInteger("foo" + i, dictionary, false)) != null) {
			assertEquals("Testing custom integer foo" + i, (Integer) 1, integerValue);
			i++;
		}
	}

	@Test
	public void customIntegersRegexp() {
		HashMap<String, Integer> dictionary = new HashMap<>();
		dictionary.put("(?iu).*foo.*", 1);
		Integer integerValue;
		int i = 1;
		while ((integerValue = configuration.getCustomInteger("bar" + i, dictionary, true)) != null) {
			assertEquals("Testing custom integer (with regexp) bar" + i, (Integer) 1, integerValue);
			i++;
		}
	}

}
