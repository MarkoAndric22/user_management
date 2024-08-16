package springBootSecurity.com.example.springBootSecurity.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class AuthorizationToken implements Serializable {


  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("refresh_token")
  private String refreshToken;

  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("token")
  private String token;
}
