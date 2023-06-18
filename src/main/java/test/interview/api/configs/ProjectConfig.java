package test.interview.api.configs;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:config.properties"})
public interface ProjectConfig extends Config {
    String baseUrl();
    boolean requestResponseLogging();
}
