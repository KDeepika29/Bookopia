package com.book.exchange.platform.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtils {
	public static Properties poolConfig;

	public Properties getPoolConfig() {
		if (poolConfig == null) {
			loadProperties();
		}
		return poolConfig;

	}

	public static void loadProperties() {
		try (InputStream poolConfiguration = ConfigUtils.class.getClassLoader()
				.getResourceAsStream("poolconfig.properties");) {
			poolConfig= new Properties();
			poolConfig.load(poolConfiguration);
		}catch(IOException ex) {
			
		}
	}

}
