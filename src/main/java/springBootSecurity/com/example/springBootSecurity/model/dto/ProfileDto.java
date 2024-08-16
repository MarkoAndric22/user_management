package springBootSecurity.com.example.springBootSecurity.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class ProfileDto implements Serializable {

  @Schema(example = "101")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("id")
  private Integer id;

  @Schema(example = "John")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("first_name")
  private String firstName;

  @Schema(example = "Doe")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("last_name")
  private String lastName;

  @Schema(example = "email@email.com")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("email")
  private String email;

  @Schema(example = "+3816155566677")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("phone")
  private String phone;

  @Schema(example = "https:// path/to/image.png")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("image")
  private String image;
}
