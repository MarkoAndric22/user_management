package springBootSecurity.com.example.springBootSecurity.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
public class EndpointResponse implements Serializable {

  @Id
  @Schema(example = "1234fgh678")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("id")
  @Column(name = "id", nullable = false, unique = true)
  private String id;

  @Schema(example = "GET")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("method")
  @Column(name = "method")
  private String method;

  @Schema(example = "admin")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("service")
  @Column(name = "service")
  private String service;

  @Schema(example = "RoleController")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("controller")
  @Column(name = "controller")
  private String controller;

  @Schema(example = "Role")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("controller_alias")
  @Column(name = "controller_alias")
  private String controllerAlias;

  @Schema(example = "view")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("action")
  @Column(name = "action")
  private String action;

  @Schema(example = "/api/v1/read")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @JsonProperty("endpoint")
  @Column(name = "endpoint")
  private String endpoint;
}
