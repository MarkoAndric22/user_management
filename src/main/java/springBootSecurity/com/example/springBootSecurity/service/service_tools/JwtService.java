package springBootSecurity.com.example.springBootSecurity.service.service_tools;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import springBootSecurity.com.example.springBootSecurity.configuration.AppProperties;
import springBootSecurity.com.example.springBootSecurity.exception.BadToken;
import springBootSecurity.com.example.springBootSecurity.exception.TokenExpired;
import springBootSecurity.com.example.springBootSecurity.model.response.UserResponse;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import static springBootSecurity.com.example.springBootSecurity.component.AppConst.*;

@Service
@RequiredArgsConstructor
public class JwtService {

  private final AppProperties appProperties;

  /**
   * Generate token..
   *
   * @param model RemiLoginCheck
   * @return String
   */
  public String generateToken(UserResponse model) {
    Date now = new Date();
    Date expire = DateUtils.addHours(now, EXPIRATION_TIME);

    SecretKey key = Keys.hmacShaKeyFor(appProperties.getJwtSecret().getBytes(StandardCharsets.UTF_8));
    return Jwts.builder()
      .setHeaderParam(HEADER_STRING_2, HEADER_VALUE_2)
      .setClaims(createClaim(model))
      .setSubject(TOKEN_SUBJECT)
      .setIssuedAt(now)
      .setExpiration(expire)
      .signWith(key, SignatureAlgorithm.HS512).compact();
  }

  /**
   * Generate refresh token.
   *
   * @return String
   */
  public String generateRefreshToken() {
    Instant instant = Instant.now();
    long unixTimeInMillis = instant.toEpochMilli();
    return DigestUtils.md5Hex(UUID.randomUUID() + "-" + unixTimeInMillis);
  }

  /**
   * Claim token.
   *
   * @param request HttpServletRequest
   * @return Claims
   */
  public Claims jwsToken(HttpServletRequest request) {
    SecretKey secretKey = Keys.hmacShaKeyFor(appProperties.getJwtSecret().getBytes());
    Claims jws;
    try {
      jws =
        Jwts.parserBuilder()
          .setSigningKey(secretKey)
          .build()
          .parseClaimsJws(getBearer(request))
          .getBody();
    } catch (ExpiredJwtException e) {
      throw new TokenExpired(e.getMessage());
    } catch (JwtException | IllegalArgumentException ex) {
      throw new BadToken(ex.getMessage());
    }
    return jws;
  }

  /**
   * Get super admin.
   *
   * @param request HttpServletRequest
   * @return boolean
   */
  public boolean getSuperAdmin(HttpServletRequest request) {
    return getValue(request, "role").toString().equals(SUPER_ADMIN);
  }

  /**
   * Get User Full Name.
   *
   * @param request - HttpServletRequest
   * @return String
   */
  public String getFullName(HttpServletRequest request) {
    return getValue(request, "first_name").toString() + " " + getValue(request, "last_name");
  }

  /**
   * Get role ID.
   *
   * @param request HttpServletRequest
   * @return Integer
   */
  public Integer getRoleId(HttpServletRequest request) {
    return Integer.valueOf(getValue(request, "role_id").toString());
  }

  /**
   * Get jwt value.
   *
   * @param request HttpServletRequest
   * @param key     String
   * @return Object
   */
  private Object getValue(HttpServletRequest request, String key) {
    return jwsToken(request).get(key);
  }

  /**
   * Get bearer token.
   *
   * @param request HttpServletRequest
   * @return String
   */
  private String getBearer(HttpServletRequest request) {
    String authTokenHeader = request.getHeader(HEADER_STRING);
    if (authTokenHeader == null || authTokenHeader.trim().isEmpty()) {
      throw new BadToken("Bearer token missing");
    }
    return authTokenHeader.replace(TOKEN_PREFIX, "");
  }

  /**
   * Create claim
   *
   * @param model UserResponse
   * @return Map
   */
  private Map<String, Object> createClaim(UserResponse model) {
    Map<String, Object> claims = new LinkedHashMap<>();
    claims.put("id", model.getId());
    claims.put("first_name", model.getFirstName());
    claims.put("last_name", model.getLastName());
    claims.put("email", model.getEmail());
    claims.put("role_id", model.getRole().getId());
    claims.put("role", model.getRole().getName());
    return claims;
  }
}
