package springBootSecurity.com.example.springBootSecurity.model.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
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
public class UserResponse implements Serializable {


  @Id
  @Schema(example = "101")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("id")
  @Column(name = "id")
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
  @Column(name = "password")
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

  @Schema(example = "A6B1C6")
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

  @Schema(example = "John Doe")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "John Doe")
  @JsonProperty("created_by")
  @Column(name = "created_by")
  private String createdBy;

  @Schema(example = "John Doe")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "John Doe")
  @JsonProperty("last_modified_by")
  @Column(name = "last_modified_by")
  private String lastModifiedBy;

  @Schema(example = "2020-10-15 20:18:45")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonProperty("created_date")
  @Column(name = "created_date")
  private Date createdDate;

  @Schema(example = "2020-10-15 20:18:45")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonProperty("last_modified_date")
  @Column(name = "last_modified_date")
  private Date lastModifiedDate;

  @OneToOne(fetch = FetchType.EAGER)
  @Schema()
  @JsonFormat(shape = JsonFormat.Shape.OBJECT)
  @JsonProperty("role")
  @JoinColumn(
    name = "role_id",
    referencedColumnName = "id",
    insertable = false,
    updatable = false)
  @ToString.Exclude
  private RoleResponse role;

}
