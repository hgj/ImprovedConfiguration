package hu.hgj.improvedconfiguration;

import hu.hgj.improvedconfiguration.resources.ResourceHelper;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class IncludeTests {

	@Test
	public void includeOne() throws IOException {
		Configuration configuration = ConfigurationParser.loadConfiguration(new File(ResourceHelper.pathForResource("include1.conf")));
		assertNotNull(configuration);
		assertEquals(configuration.get("basic"), "basic");
	}

	@Test
	public void includeTwo() throws IOException {
		Configuration configuration = ConfigurationParser.loadConfiguration(new File(ResourceHelper.pathForResource("include2.conf")));
		assertNotNull(configuration);
		assertEquals(configuration.get("basic"), "basic");
	}

}
