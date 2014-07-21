package org.pq.config.impl;

import java.util.Properties;

import org.pq.config.base.Configable;
import org.pq.config.base.DefConfigSetter;

public class ConfigBuilder implements DefConfigSetter {

    private Properties properties;

    @Override
    public void setDefConfig(Configable defConfig) {
        properties = new Properties(defConfig != null ? defConfig.getProperties() : null);
    }

    public void addConfig(Configable config) {
        properties.putAll(config.getProperties());
    }

    public Configable buildConfig() {
        return new DefaultConfigable(properties);
    }
}
