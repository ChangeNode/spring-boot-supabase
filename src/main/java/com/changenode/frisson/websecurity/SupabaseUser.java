package com.changenode.frisson.websecurity;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.*;

import static java.util.Collections.unmodifiableMap;

public class SupabaseUser implements UserDetails, Principal {
    private final List<GrantedAuthority> grantedAuthority = new ArrayList<>();
    private final Claims claims;
    private final String id;
    private final String role;
    private final String email;
    private final String phone;
    private final Map<String, String> appMetadata;
    private final Map<String, String> userMetadata;
    private final String fullName;
    private final String userName;
    private final String provider;
    private final String avatarUrl;
    private final String emailConfirmedAt;
    private final String confirmedAt;
    private final String lastSignInAt;
    private final String createdAt;
    private final String updatedAt;
    private final String password;

    @SuppressWarnings("unchecked")
    public SupabaseUser(Claims claims, String accessToken) {
        this.claims = claims;
        this.password = accessToken;

        id = claims.get("sub", String.class);
        role = claims.get("role", String.class);

        grantedAuthority.clear();
        grantedAuthority.add(new SimpleGrantedAuthority(id));
        grantedAuthority.add(new SimpleGrantedAuthority(role));

        email = claims.get("email", String.class);
        phone = claims.get("phone", String.class);

        appMetadata = (Map<String, String>) unmodifiableMap(claims.get("app_metadata", HashMap.class));
        userMetadata = (Map<String, String>) unmodifiableMap(claims.get("user_metadata", HashMap.class));

        if (userMetadata.get("avatar_url") != null)
            avatarUrl = userMetadata.get("avatar_url");
        else
            avatarUrl = "/assets/svg/person-outline.svg";
        fullName = userMetadata.get("full_name");
        if (userMetadata.get("user_name") != null)
            userName = userMetadata.get("user_name");
        else
            userName = email;

        emailConfirmedAt = userMetadata.get("email_confirmed_at");
        confirmedAt = userMetadata.get("confirmed_ad");
        lastSignInAt = userMetadata.get("last_sign_in_at");
        createdAt = userMetadata.get("created_at");
        updatedAt = userMetadata.get("updated_ad");

        provider = appMetadata.getOrDefault("provider", "");
    }

    public List<GrantedAuthority> getGrantedAuthority() {
        return grantedAuthority;
    }

    public Claims getClaims() {
        return claims;
    }

    public String getFullName() {
        return fullName;
    }

    public String getProvider() {
        return provider;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmailConfirmedAt() {
        return emailConfirmedAt;
    }

    public String getConfirmedAt() {
        return confirmedAt;
    }

    public String getLastSignInAt() {
        return lastSignInAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public Map<String, String> getAppMetadata() {
        return appMetadata;
    }

    public Map<String, String> getUserMetadata() {
        return userMetadata;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthority;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return getUsername();
    }
}
