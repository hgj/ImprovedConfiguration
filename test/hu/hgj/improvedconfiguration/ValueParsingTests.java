package hu.hgj.improvedconfiguration;

import hu.hgj.improvedconfiguration.resources.ResourceHelper;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class ValueParsingTests {

	private static Configuration configuration = null;

	@BeforeClass
	public static void LoadConfigurations() throws IOException {
		configuration = ConfigurationParser.loadConfiguration(new File(ResourceHelper.pathForResource("values.conf")));
		assertNotNull(configuration);
	}

	@Test
	public void strings() {
		for (int i = 1; i <= 3; i++) {
			assertEquals("Testing string" + i, "string" + i, configuration.get("string" + i));
		}
	}

	@Test
	public void specialStrings() {
		String[] expectedValues = new String[]{
				"foo ' bar",
				"foo \\\" bar",
				"\"",
				"'foo\"bar'baz\""
		};
		for (int i = 0; i < expectedValues.length; i++) {
			int j = i + 1;
			assertEquals("Testing specialString" + j, expectedValues[i], configuration.get("specialString" + j));
		}
	}

	@Test
	public void booleans() {
		Boolean booleanValue;
		int i = 1;
		while ((booleanValue = configuration.getBoolean("boolean" + i)) != null) {
			assertEquals("Testing boolean" + i, (i % 2 == 1), booleanValue);
			i++;
		}
		System.out.println("Tested " + (i-1) + " booleans.");
	}

	@Test
	public void integers() {
		Integer integerValue;
		int i = 1;
		while ((integerValue = configuration.getInteger("integer" + i)) != null) {
			assertEquals("Testing integer" + i, (long) i, (long) integerValue);
			i++;
		}
		System.out.println("Tested " + (i-1) + " integers.");
	}

	@Test
	public void negativeIntegers() {
		Integer integerValue;
		int i = 1;
		while ((integerValue = configuration.getInteger("negativeInteger" + i)) != null) {
			assertEquals("Testing integer" + i, (long) -i, (long) integerValue);
			i++;
		}
		System.out.println("Tested " + (i-1) + " negative integers.");
	}

	@Test
	public void floatsAndDoubles() {
		Float[] expectedFloats = new Float[] {
				1f, 2.0f, 3.14f, 4.4444f, 5.0000001f
		};
		Double[] expectedDoubles = new Double[] {
				1d, 2.0d, 3.14d, 4.4444d, 5.0000001d
		};
		for (int i = 0; i < expectedFloats.length; i++) {
			int j = i + 1;
			assertEquals("Testing float" + j, expectedFloats[i], configuration.getFloat("float" + j));
			assertEquals("Testing double" + j, expectedDoubles[i], configuration.getDouble("float" + j));
		}
	}

}
