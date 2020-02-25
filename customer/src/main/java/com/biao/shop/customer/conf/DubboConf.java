package com.biao.shop.customer.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/config/application-dev.yml")//注意properties里不要有和下面使用API方式的冲突。
public class DubboConf {
}
