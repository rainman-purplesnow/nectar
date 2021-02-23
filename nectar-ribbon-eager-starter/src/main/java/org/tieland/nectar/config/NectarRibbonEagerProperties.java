package org.tieland.nectar.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.Set;

/**
 *
 * @author zhouxiang
 * @date 2021/02/10
 */
@Data
@ConfigurationProperties(prefix = "nectar.ribbon.eager")
public class NectarRibbonEagerProperties {

    /**
     * 是否自动eager加载
     */
    private boolean enabled = false;

    /**
     * 特定client
     */
    private Set<String> clients;

}
