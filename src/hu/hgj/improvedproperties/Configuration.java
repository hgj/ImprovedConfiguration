//
// This file is part of the ImprovedConfiguration project.
// Visit the project's GitHub page for details:
//   https://github.com/hgj/ImprovedConfiguration
//
// This work is licensed under a Creative Commons
// Attribution-NonCommercial-ShareAlike 4.0 International License.
// See the LICENSE file or visit the license page for details.
//   http://creativecommons.org/licenses/by-nc-sa/4.0/
//

package hu.hgj.improvedproperties;

import java.util.HashMap;
import java.util.Map;

/**
 * A Configuration object is a wrapper to a {@link HashMap<String, String>}.
 * <p/>
 * If you want to create a new Configuration that should be independent from
 * it's source (with the help of {@link #getMatching(String)} or {@link
 * #getSubset(String)} or {@link #getEntries()}, a shallow copy of the {@link
 * java.util.HashMap} should be enough, since {@link java.lang.String}s are
 * immutable.
 */
public class Configuration implements Cloneable {

	/**
	 * A {@link HashMap} of the entries in the configuration.
	 */
	private final HashMap<String, String> entries;

	/**
	 * A prefix to use before all the keys when getting or setting them.
	 */
	private String prefix = "";

	/**
	 * Construct an empty configuration object.
	 */
	public Configuration() {
		entries = new HashMap<>();
	}

	/**
	 * Wrap around the given {@link HashMap<String, String>} with a {@link
	 * Configuration} object.
	 * @param entries The {@link HashMap<String, String>} to wrap around.
	 */
	public Configuration(HashMap<String, String> entries) {
		this.entries = entries;
	}

	/**
	 * Change the current prefix. The prefix should end with a dot. It is
	 * automatically added if needed.
	 * @param prefix The new prefix.
	 */
	public void setPrefix(String prefix) {
		if (prefix.endsWith(".")) {
			this.prefix = prefix;
		} else {
			this.prefix = prefix + ".";
		}
	}

	/**
	 * Get the current prefix.
	 * @return {@link #prefix}.
	 */
	public String getPrefix() {
		return this.prefix;
	}

	/**
	 * Set all the missing keys to a default value. The current prefix will be
	 * prepended to the argument's keys.
	 * @param defaults A entries for the default values.
	 */
	public void setDefaults(HashMap<String, String> defaults) {
		for (Map.Entry<String, String> defaultEntry : defaults.entrySet()) {
			String key = prefix + defaultEntry.getKey();
			if (!entries.containsKey(key)) {
				entries.put(key, defaultEntry.getValue());
			}
		}
	}

	/**
	 * Sets the configuration {@code key} to {@code value}, the same way as
	 * {@link HashMap#put} does - but the prefix is added to the key.
	 * @param key The key to set.
	 * @param value The value to set the key to.
	 * @return Same as {@link HashMap#put}.
	 * @see HashMap#put
	 */
	public String set(String key, String value) {
		return entries.put(prefix + key, value);
	}

	/**
	 * Gets the configuration value for {@code key} if it exists.
	 * @param key The key to get.
	 * @return Same as {@link HashMap#get}.
	 * @see HashMap#get
	 */
	public String get(String key) {
		return entries.get(prefix + key);
	}

	/**
	 * Gets the {@code value} for the given {@code key} and translates it using
	 * the {@code dictionary} argument. If the value can not be matched against
	 * any key in the {@code dictionary}, the original value is returned. If
	 * more than one {@code key} matches the {@code value}, the latter will be
	 * used. Other than that, the method behaves the same as {@link
	 * #get(java.lang.String)}. The {@code value} is converted to lower case
	 * before matching.
	 * @param key The key to get.
	 * @param dictionary The translation dictionary.
	 * @param useRegexp Use {@link String#matches(java.lang.String)} with the
	 * dictionary keys.
	 * @return Returns the translated {@code value} or the original or {@code
	 * null}.
	 */
	public String getCustom(String key, HashMap<String, String> dictionary, boolean useRegexp) {
		String value = entries.get(prefix + key);
		if (value != null) {
			value = value.toLowerCase();
		} else {
			return null;
		}
		if (useRegexp) {
			for (Map.Entry<String, String> dictionaryEntry : dictionary.entrySet()) {
				if (value.matches(dictionaryEntry.getKey())) {
					value = dictionaryEntry.getValue();
				}
			}
		} else {
			if (dictionary.containsKey(value)) {
				value = dictionary.get(value);
			}
		}
		return value;
	}

	/**
	 * Retrieves and converts the {@code value} to {@link Boolean}.
	 * @param key The key to get.
	 * @return The {@code value}'s boolean meaning.
	 * @see Configuration#getBooleanValue(String)
	 */
	public Boolean getBoolean(String key) {
		String value = entries.get(prefix + key);
		return Configuration.getBooleanValue(value);
	}

	/**
	 * Get the boolean value of a String, according to the following rules:
	 * "false", "off", "no", "n" and numbers less than 1 become {@code false},
	 * all other become {@code true}. The comparison is case-insensitive.
	 * @param value The String value to be converted.
	 * @return The boolean value of the String argument.
	 */
	public static Boolean getBooleanValue(String value) {
		if (value != null) {
			value = value.trim().toLowerCase();
			try {
				int intValue = Integer.parseInt(value);
				return intValue > 0;
			} catch (NumberFormatException e) {
				return !("false".equals(value) || "off".equals(value) || "no".equals(value) || "n".equals(value));
			}
		} else {
			return null;
		}
	}

	/**
	 * Retrieves and converts the {@code value} to {@link Integer}. The
	 * conversion is done with a simple {@link Integer#parseInt(java.lang.String)}.
	 * @param key The key to get.
	 * @return The {@code} decimal value of the key.
	 */
	public Integer getInteger(String key) {
		String value = entries.get(prefix + key);
		if (value != null) {
			return Integer.parseInt(value.trim());
		} else {
			return null;
		}
	}

	/**
	 * The same as {@link #getCustom(java.lang.String, java.util.HashMap,
	 * boolean)}, but returns {@link Integer} and expects the dictionary
	 * accordingly.
	 * @param key The key to get.
	 * @param dictionary The translation dictionary.
	 * @param useRegexp Use {@link String#matches(java.lang.String)} with the
	 * dictionary keys.
	 * @return Returns the translated {@code value} or the original or {@code
	 * null}.
	 */
	public Integer getCustomInteger(String key, HashMap<String, Integer> dictionary, boolean useRegexp) {
		String value = entries.get(prefix + key);
		if (value == null) {
			return null;
		} else {
			value = value.toLowerCase();
			Integer integerValue = null;
			try {
				integerValue = Integer.parseInt(value);
			} catch (NumberFormatException e) {
				if (useRegexp) {
					for (Map.Entry<String, Integer> dictionaryEntry : dictionary.entrySet()) {
						if (value.matches(dictionaryEntry.getKey())) {
							integerValue = dictionaryEntry.getValue();
						}
					}
				} else {
					if (dictionary.containsKey(value)) {
						integerValue = dictionary.get(value);
					}
				}
			}
			return integerValue;
		}
	}

	/**
	 * Get all configuration entries.
	 * @return All the configuration entries.
	 */
	public HashMap<String, String> getEntries() {
		return entries;
	}

	/**
	 * Return a subset of the configuration entries which keys match the given
	 * regexp pattern.
	 * @param pattern The regexp pattern to match the keys.
	 * @return The matched configuration entries.
	 */
	public HashMap<String, String> getMatching(String pattern) {
		if (pattern.isEmpty()) {
			return null;
		}
		HashMap<String, String> matchingEntries = new HashMap<>();
		for (Map.Entry<String, String> entry : entries.entrySet()) {
			if (entry.getKey().matches(pattern)) {
				matchingEntries.put(entry.getKey(), entry.getValue());
			}
		}
		return matchingEntries;
	}

	/**
	 * Return a subset of the configuration entries which keys match the given
	 * prefix.
	 * @param prefix The prefix to match the keys.
	 * @return The matched configuration entries.
	 */
	public Configuration getSubset(String prefix) {
		if (prefix.isEmpty()) {
			return this;
		}
		HashMap<String, String> matchingEntries = new HashMap<>();
		for (Map.Entry<String, String> entry : entries.entrySet()) {
			if (entry.getKey().startsWith(prefix)) {
				matchingEntries.put(entry.getKey(), entry.getValue());
			}
		}
		return new Configuration(matchingEntries);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Configuration clone() {
		// NOTE: Since Strings are immutable, this shallow copy should be enough
		return new Configuration((HashMap<String, String>) this.entries.clone());
	}
}
