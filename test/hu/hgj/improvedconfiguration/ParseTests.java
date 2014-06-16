package hu.hgj.improvedconfiguration;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ParseTests {

	private Configuration basicConfiguration = null;

	@Before
	public void LoadConfigurations() throws IOException {
		basicConfiguration = ConfigurationParser.loadConfiguration(new File(FileTests.basicFilePath));
	}

	@Test
	public void TrimmingTest() {
		for (int i = 1; i <= 4; i++) {
			assertEquals("Testing foo" + i, "foo", basicConfiguration.get("foo" + i));
		}
	}

	@Test
	public void CommentsTest() {
		for (int i = 1; i <= 3; i++) {
			assertEquals("Testing bar" + i, "bar", basicConfiguration.get("bar" + i));
		}
	}

	@Test
	public void QuotesTest() {
		for (int i = 1; i <= 6; i++) {
			assertEquals("Testing baz" + i, "baz", basicConfiguration.get("baz" + i));
		}
	}

	@Test
	public void OverwriteTest() {
		assertEquals("Testing overwrite", "over", basicConfiguration.get("over"));
	}

}
