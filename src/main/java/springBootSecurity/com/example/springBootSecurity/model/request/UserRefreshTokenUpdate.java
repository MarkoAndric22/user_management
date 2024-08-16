package springBootSecurity.com.example.springBootSecurity.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
@Table(name = "user")
public class UserRefreshTokenUpdate implements Serializable {

  @Id
  @Schema(example = "101")
  @NotNull(message = "Please provide a user ID")
  @Min(value = 1, message = "user ID must be greater or minimum 1")
  @Column(name = "id")
  @JsonProperty("id")
  private Integer id;

  @Schema(example = "101abcdegh")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("refresh_token")
  @Column(name = "refresh_token")
  private String refreshToken;

  @Schema(example = "2020-10-15 20:18:45")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("refresh_token_expire")
  @Column(name = "refresh_token_expire")
  private Date refreshTokenExpire;
}
