package org.tieland.nectar;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.Properties;

/**
 * <p>Ribbon Eager ApplicationContextInitializer</p>
 *
 * @author zhouxiang
 * @date 2021/2/18
 */
@Slf4j
public class NectarRibbonEagerApplicationContextInitializer implements ApplicationContextInitializer {

    private static boolean initialized = false;

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        if(!initialized){
            String enabled = configurableApplicationContext.getEnvironment().getProperty("nectar.ribbon.eager.enabled");
            log.debug("nectar.ribbon.eager.enabled:{}", enabled);
            if(Boolean.TRUE.toString().equals(enabled)){
                MutablePropertySources propertySources = configurableApplicationContext.getEnvironment().getPropertySources();
                Properties properties = new Properties();
                properties.put("ribbon.eager-load.enabled", "true");
                propertySources.addFirst(new PropertiesPropertySource("nectar", properties));
            }

            initialized = true;
        }

    }
}
