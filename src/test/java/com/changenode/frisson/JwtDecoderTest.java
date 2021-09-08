package com.changenode.frisson;

import com.changenode.frisson.websecurity.SupabaseUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtDecoderTest {

    String jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJhdXRoZW50aWNhdGVkIiwiZXhwIjoxNjMwMDI2NDQ1LCJzdWIiOiI1MTdkOTlmZS1kYjdkLTQ2N2UtYjNmMi1iZTVhYzhjNDE3NTIiLCJlbWFpbCI6IndpdmVyc29uQGdtYWlsLmNvbSIsInBob25lIjoiIiwiYXBwX21ldGFkYXRhIjp7InByb3ZpZGVyIjoiZ2l0aHViIn0sInVzZXJfbWV0YWRhdGEiOnsiYXZhdGFyX3VybCI6Imh0dHBzOi8vYXZhdGFycy5naXRodWJ1c2VyY29udGVudC5jb20vdS84NzM4NTA_dj00IiwiZnVsbF9uYW1lIjoiV2lsbCBJdmVyc29uIiwidXNlcl9uYW1lIjoid2l2ZXJzb24ifSwicm9sZSI6ImF1dGhlbnRpY2F0ZWQifQ.5-gknHOAKifeEcCjqWNI8zF7ypQi3MkMDLE6W2qyM_A";

    String secret = "cb36e3af-0bfb-4994-a735-65d95c0e40aa";

    @Test
    @Disabled("Requires the JWT to be manually updated. :P")
    public void jtwDecodeAndVerify() {
        String encoded = Base64.getEncoder().encodeToString(secret.getBytes());

        Jwt<JwsHeader, Claims> parse = Jwts.parserBuilder().setSigningKey(encoded).
                requireAudience("authenticated").
                build().parseClaimsJws(jwt);

        JwsHeader header = parse.getHeader();
        for (Object key : header.keySet()) {
            System.out.println(key + ":" + header.get(key));
        }

        Claims body = parse.getBody();

        if (body.getExpiration().before(Date.from(Instant.now())))
            throw new IllegalArgumentException("JWT expired");

        SupabaseUser user = new SupabaseUser(body, jwt);

        assertThat(user.getId()).isEqualTo(body.get("sub", String.class));

        System.out.println(body);
    }

}
