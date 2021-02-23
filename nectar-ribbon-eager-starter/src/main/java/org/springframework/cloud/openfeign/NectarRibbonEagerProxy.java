package org.springframework.cloud.openfeign;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cloud.netflix.ribbon.RibbonEagerLoadProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.tieland.nectar.config.NectarRibbonEagerProperties;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>Nectar ribbon eager Proxy</p>
 *
 * @author zhouxiang
 * @date 2021/02/10
 */
@Slf4j
public class NectarRibbonEagerProxy implements ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    private RibbonEagerLoadProperties ribbonEagerLoadProperties;

    private NectarRibbonEagerProperties nectarRibbonEagerProperties;

    public NectarRibbonEagerProxy(RibbonEagerLoadProperties ribbonEagerLoadProperties, NectarRibbonEagerProperties nectarRibbonEagerProperties){
        this.ribbonEagerLoadProperties = ribbonEagerLoadProperties;
        this.nectarRibbonEagerProperties = nectarRibbonEagerProperties;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Set<String> feignClients = new HashSet<>();

        applicationContext.getBeansOfType(FeignClientFactoryBean.class).values().forEach(
                feignClientFactoryBean -> {
                    String feignClient = feignClientFactoryBean.getName();
                    log.debug("feignClient:{}", feignClient);
                    feignClients.add(feignClient);
                }
        );

        if(nectarRibbonEagerProperties.isEnabled()){
            if(!ribbonEagerLoadProperties.isEnabled()){
                ribbonEagerLoadProperties.setEnabled(Boolean.TRUE);
            }

            if(CollectionUtils.isEmpty(nectarRibbonEagerProperties.getClients())){
                ribbonEagerLoadProperties.setClients(Lists.newArrayList(feignClients));
            }else{
                Sets.SetView<String> intersection = Sets.intersection(feignClients, nectarRibbonEagerProperties.getClients());
                log.debug("intersection:{}", intersection);
                if(CollectionUtils.isNotEmpty(intersection)){
                    ribbonEagerLoadProperties.setClients(Lists.newArrayList(intersection));
                }else{
                    log.warn(" no nectar ribbon eager client match. clients:{}, feignClients:{}",
                            nectarRibbonEagerProperties.getClients(), feignClients);
                }
            }
        }
    }
}
