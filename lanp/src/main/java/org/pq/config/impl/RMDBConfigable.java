package org.pq.config.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.pq.config.base.Configable;
import org.pq.core.lang.RClassPath;
import org.pq.core.util.ParamsAppliable;
import org.pq.core.util.ParamsUtils;
import org.pq.config.base.DefConfigSetter;
import org.pq.esql.Esql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import com.google.common.collect.Lists;

public class RMDBConfigable extends DefaultConfigable implements ParamsAppliable, DefConfigSetter {
    private static final Logger logger = LoggerFactory.getLogger(RMDBConfigable.class);

    private String connectionName;
    private String basePackage;
    private String tableName;

    @Override
    public void setDefConfig(Configable defConfig) {
        this.basePackage = defConfig.getStr("bizconfigdir", "bizconfig") + ".";
        Properties properties = new Properties(defConfig.getProperties());
        loadEsqlConfig(properties);

        refreshPropertiesFromDB(properties, "");

        super.setProperties(properties);
    }

    private void loadEsqlConfig(Properties properties) {
        Resource[] iniRes = RClassPath.getResources(basePackage, "**/*.ini");
        logger.info("iniRes files is:{}", iniRes.length);
        for (Resource propRes : iniRes) {
            properties.putAll((new IniConfigable(propRes)).getProperties());
        }
        logger.info("after read ini Files, properties is:{}", properties);

        DefaultConfigable configable = new DefaultConfigable(properties);
        Configable connectionConfigable = configable.subset("connectionName." + connectionName);
        super.setProperties(connectionConfigable.getProperties());
        logger.info("properties is:{}", connectionConfigable.getProperties());
    }

    private void refreshPropertiesFromDB(Properties properties, String prefix) {
        List<Map> configs = new Esql(this).params(basePackage + prefix + "%").dynamics(tableName)
                .select("QueryN3Config").execute();
        for (Map config : configs) {
            String key = MapUtils.getString(config, "KEY");
            String value = MapUtils.getString(config, "VALUE");

            properties.put(key.substring(basePackage.length()), value);
        }
        logger.info("get config from DB is:{}", properties);
    }

    @Override
    public long refreshConfigSet(String prefix) {
        prefix = StringUtils.isEmpty(prefix) ? "" : prefix + (prefix.endsWith(".") ? "" : ".");
        String remoteSumVersion = new Esql(connectionName).params(basePackage + prefix + "%").dynamics(tableName)
                .selectFirst("QueryVersionSum").execute();
        final String sumVersionKey = prefix + "sumversion";
        String localSumVersion = getStr(sumVersionKey);
        if (!StringUtils.equals(remoteSumVersion, localSumVersion)) {
            clearLocalKeys(prefix);
            if (StringUtils.isEmpty(prefix)) {
                Properties properties = new Properties();
                Resource[] iniRes = RClassPath.getResources(basePackage, "**/*.ini");
                for (Resource propRes : iniRes) {
                    properties.putAll((new IniConfigable(propRes)).getProperties());
                }

                getProperties().putAll(properties);
            }
            refreshPropertiesFromDB(getProperties(), prefix);
            getProperties().put(sumVersionKey, remoteSumVersion);
        }

        return Long.valueOf(remoteSumVersion);
    }

    private void clearLocalKeys(String prefix) {
        ArrayList<String> removeKeys = Lists.newArrayList();
        for (Map.Entry<Object, Object> entry : super.getProperties().entrySet()) {
            String key = (String) entry.getKey();

            if (key.startsWith(prefix))
                removeKeys.add(key);
        }

        for (String key : removeKeys)
            super.getProperties().remove(key);

    }

    @Override
    public void applyParams(String[] params) {
        this.connectionName = ParamsUtils.getStr(params, 0, "N3_Config_Connection");
        this.tableName = ParamsUtils.getStr(params, 1, "N3_CONFIG");
    }

}
