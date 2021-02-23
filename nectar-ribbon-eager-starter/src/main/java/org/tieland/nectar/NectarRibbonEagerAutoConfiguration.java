package org.tieland.nectar;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration;
import org.springframework.cloud.netflix.ribbon.RibbonEagerLoadProperties;
import org.springframework.cloud.openfeign.NectarRibbonEagerProxy;
import org.springframework.cloud.openfeign.ribbon.FeignRibbonClientAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tieland.nectar.config.NectarRibbonEagerProperties;
import org.springframework.core.env.Environment;

/**
 * Ribbon Eager AutoConfiguration
 *
 * @author zhouxiang
 * @date 2021/02/10
 */
@Configuration
@ConditionalOnClass(FeignRibbonClientAutoConfiguration.class)
@AutoConfigureBefore(RibbonAutoConfiguration.class)
@EnableConfigurationProperties({NectarRibbonEagerProperties.class})
public class NectarRibbonEagerAutoConfiguration {

    @Bean
    public NectarRibbonEagerProxy nectarRibbonEagerProxy(RibbonEagerLoadProperties ribbonEagerLoadProperties,
                                                         NectarRibbonEagerProperties nectarRibbonEagerProperties){
        NectarRibbonEagerProxy proxy = new NectarRibbonEagerProxy(ribbonEagerLoadProperties, nectarRibbonEagerProperties);
        return proxy;
    }

}
