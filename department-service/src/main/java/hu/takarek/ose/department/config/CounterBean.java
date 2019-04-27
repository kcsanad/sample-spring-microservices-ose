package hu.takarek.ose.department.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Meter.Type;

@Configuration
@Component
public class CounterBean {
    private static final String SMID = "metric.conf.smid";
    private static final String METRICTYPE = "metric.conf.type";

    @Value("${metric.conf.smid}")
    private String smid;

    @Value("${metric.conf.type}")
    private String type;

    @Bean
    MeterRegistryCustomizer<MeterRegistry> addOrganizationRegistryAdd() {
        return registry -> registry.config()
            .commonTags(SMID, smid)
            .namingConvention().name("department.add", Type.COUNTER)
            ;
    }


    @Bean
    MeterRegistryCustomizer<MeterRegistry> addOrganizationRegistryFind() {
        return registry -> registry.config()
            .commonTags(SMID, smid)
            .commonTags(METRICTYPE, type)
            .namingConvention().name("department.find", Type.COUNTER)
            ;
    }



}