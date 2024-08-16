package springBootSecurity.com.example.springBootSecurity.model.modelTools;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class Mapping {

  @Schema(example = "categories_administration")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "categories_administration")
  @JsonProperty("service")
  private String service;

  @Schema(example = "CategoriesController")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "CategoriesController")
  @JsonProperty("controller")
  private String controller;

  @Schema(example = "e5dadf6524624f79c3127e247f04b548")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "e5dadf6524624f79c3127e247f04b548")
  @JsonProperty("controller_hash")
  private String controllerHash;

  @Schema(example = "POST")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "POST")
  @JsonProperty("method")
  private String method;

  @Schema(example = "/api/v1/categories/create")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "/api/v1/categories/create")
  @JsonProperty("path")
  private String path;

  @Schema(example = "e5dadf6524624f79c3127e247f04b548")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "e5dadf6524624f79c3127e247f04b548")
  @JsonProperty("path_hash")
  private String pathHash;
}
