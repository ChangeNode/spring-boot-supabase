package com.changenode.frisson.websecurity;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.security.Principal;

@Configuration
public class PrincipalSupabaseConverter implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new PSC());
    }

    public class PSC implements Converter<Principal, SupabaseUser> {
        @Override
        public SupabaseUser convert(Principal from) {
            return (SupabaseUser) from;
        }
    }
}
