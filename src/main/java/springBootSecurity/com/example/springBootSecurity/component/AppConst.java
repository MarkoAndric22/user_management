package springBootSecurity.com.example.springBootSecurity.component;

public class AppConst {

  public static final int MIN_ID = 100;
  public static final String SUPER_ADMIN = "super_admin";
  public static final String TOKEN_PREFIX = "Bearer ";

  public static final String HEADER_STRING = "Authorization";

  public static final String TOKEN_SUBJECT = "app";

  public static final String HEADER_STRING_2 = "typ";

  public static final String HEADER_VALUE_2 = "JWT";

  public static final int EXPIRATION_TIME = 1; // 1 hour

  public static final int REFRESH_EXPIRATION_TIME = 168; // 168 hours
}
