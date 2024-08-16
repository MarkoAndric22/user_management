package springBootSecurity.com.example.springBootSecurity.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import springBootSecurity.com.example.springBootSecurity.model.modelTools.Auditable;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
@Table(name = "user")
public class UserUpdate extends Auditable implements Serializable {

  @Id
  @Schema(example = "101")
  @NotNull(message = "Please provide a user ID")
  @Min(value = 1, message = "user ID must be greater or minimum 1")
  @Column(name = "id")
  @JsonProperty("id")
  private Integer id;

  @Schema(example = "John")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("first_name")
  @Column(name = "first_name")
  private String firstName;

  @Schema(example = "Doe")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("last_name")
  @Column(name = "last_name")
  private String lastName;

  @Schema(example = "email@email.com")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("email")
  @Column(name = "email")
  private String email;

  @Schema(example = "john_doe")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("user_name")
  @Column(name = "user_name")
  private String userName;

  @JsonIgnore
  @Column(name = "password", nullable = false)
  private String password;

  @Schema(example = "+3816155566677")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("phone")
  @Column(name = "phone")
  private String phone;

  @Schema(example = "A6B1C6")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("otp")
  @Column(name = "otp")
  private String otp;

  @Schema(example = "101")
  @JsonFormat(shape = JsonFormat.Shape.NUMBER)
  @JsonProperty("role_id")
  @Column(name = "role_id")
  private Integer roleId;

  @Schema(example = "true")
  @JsonFormat(shape = JsonFormat.Shape.BOOLEAN)
  @JsonProperty("status")
  @Column(name = "status")
  private Boolean status;

  @Schema(example = "true")
  @JsonFormat(shape = JsonFormat.Shape.BOOLEAN)
  @JsonProperty("phone_verify")
  @Column(name = "phone_verify")
  private Boolean phoneVerify;

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

  @Schema(example = "https:// path/to/image.png")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("image")
  @Column(name = "image")
  private String image;
}
