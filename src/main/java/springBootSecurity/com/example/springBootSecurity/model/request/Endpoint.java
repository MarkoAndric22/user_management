package springBootSecurity.com.example.springBootSecurity.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
@Table(name = "endpoint")
public class Endpoint implements Serializable {

  @Id
  @NotBlank(message = "Please provide id")
  @NotEmpty(message = "Please provide id")
  @Size(min = 1, max = 255, message = "id must be between 1 and 255")
  @Schema(example = "1234fgh678")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("id")
  @Column(name = "id", nullable = false, unique = true)
  private String id;

  @NotBlank(message = "Please provide method")
  @NotEmpty(message = "Please provide method")
  @Size(min = 1, max = 10, message = "method must be between 1 and 10")
  @Schema(example = "GET")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("method")
  @Column(name = "method")
  private String method;

  @NotBlank(message = "Please provide service")
  @NotEmpty(message = "Please provide service")
  @Size(min = 1, max = 255, message = "service must be between 1 and 255")
  @Schema(example = "admin")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("service")
  @Column(name = "service")
  private String service;

  @NotBlank(message = "Please provide controller")
  @NotEmpty(message = "Please provide controller")
  @Size(min = 1, max = 255, message = "controller must be between 1 and 255")
  @Schema(example = "RoleController")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("controller")
  @Column(name = "controller")
  private String controller;

  @NotBlank(message = "Please provide controller alias")
  @NotEmpty(message = "Please provide controller alias")
  @Size(min = 1, max = 255, message = "controller alias must be between 1 and 255")
  @Schema(example = "Role")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("controller_alias")
  @Column(name = "controller_alias")
  private String controllerAlias;

  @NotBlank(message = "Please provide action")
  @NotEmpty(message = "Please provide action")
  @Size(min = 1, max = 10, message = "action must be between 1 and 10")
  @Schema(example = "view")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("action")
  @Column(name = "action")
  private String action;

  @NotBlank(message = "Please provide endpoint")
  @NotEmpty(message = "Please provide endpoint")
  @Size(min = 1, max = 500, message = "controller must be between 1 and 500")
  @Schema(example = "/api/v1/read")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("endpoint")
  @Column(name = "endpoint")
  private String endpoint;
}
