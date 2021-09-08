package com.changenode.frisson.websecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentSupabaseUser {

    @Bean(name = "supabaseUser")
    public SupabaseUserLookup user() {
        return new SupabaseUserLookupImpl();
    }

    interface SupabaseUserLookup {
        SupabaseUser user();

        String getAvatarUrl();

        String getProvider();

        String getUsername();
    }

    public class SupabaseUserLookupImpl implements SupabaseUserLookup {

        @Override
        public SupabaseUser user() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) return null;
            if (authentication.getPrincipal() == null) return null;
            if (authentication.getPrincipal() instanceof SupabaseUser)
                return (SupabaseUser) authentication.getPrincipal();
            return null;
        }

        @Override
        public String getAvatarUrl() {
            return user().getAvatarUrl();
        }

        @Override
        public String getProvider() {
            return user().getProvider();
        }

        @Override
        public String getUsername() {
            return user().getUsername();
        }
    }

}
