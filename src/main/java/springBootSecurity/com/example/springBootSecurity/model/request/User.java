package springBootSecurity.com.example.springBootSecurity.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
public class User extends Auditable implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  @JsonIgnore
  private Integer id;

  @NotBlank(message = "Please provide first name")
  @NotEmpty(message = "Please provide first name")
  @Size(min = 1, max = 255, message = "first name must be between 1 and 255")
  @Schema(example = "John")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("first_name")
  @Column(name = "first_name", nullable = false)
  private String firstName;

  @NotBlank(message = "Please provide last name")
  @NotEmpty(message = "Please provide last name")
  @Size(min = 1, max = 255, message = "last name must be between 1 and 255")
  @Schema(example = "Doe")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("last_name")
  @Column(name = "last_name", nullable = false)
  private String lastName;

  @NotBlank(message = "Please provide email")
  @NotEmpty(message = "Please provide email")
  @Size(min = 1, max = 255, message = "email must be between 1 and 255")
  @Email(message = "Please provide valid email address")
  @Schema(example = "email@email.com")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("email")
  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @NotBlank(message = "Please provide user name")
  @NotEmpty(message = "Please provide user name")
  @Size(min = 1, max = 255, message = "user name must be between 1 and 255")
  @Schema(example = "john_doe")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("user_name")
  @Column(name = "user_name", nullable = false, unique = true)
  private String userName;

  @NotBlank(message = "Please provide password")
  @NotEmpty(message = "Please provide password")
  @Schema(example = "john_doe123!")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("password")
  @Column(name = "password", nullable = false)
  private String password;

  @NotBlank(message = "Please provide phone")
  @NotEmpty(message = "Please provide phone")
  @Size(min = 1, max = 255, message = "phone must be between 1 and 255")
  @Schema(example = "+3816155566677")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("phone")
  @Column(name = "phone", nullable = false)
  private String phone;

  @JsonIgnore
  @Column(name = "otp")
  private String otp = null;

  @NotNull(message = "Please provide role ID")
  @Schema(example = "101")
  @JsonFormat(shape = JsonFormat.Shape.NUMBER)
  @JsonProperty("role_id")
  @Column(name = "role_id", nullable = false)
  private Integer roleId;

  @JsonIgnore
  @Column(name = "status")
  private Boolean status = false;

  @JsonIgnore
  @Column(name = "phone_verify")
  private Boolean phoneVerify = false;

  @JsonIgnore
  @Column(name = "refresh_token")
  private String refreshToken = null;

  @JsonIgnore
  @Column(name = "refresh_token_expire")
  private Date refreshTokenExpire = null;

  @JsonIgnore
  @Column(name = "image")
  private String image = null;
}
