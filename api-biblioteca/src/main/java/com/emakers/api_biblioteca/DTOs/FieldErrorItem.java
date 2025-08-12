package com.emakers.api_biblioteca.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "FieldErrorItem", description = "Erro de validação em um campo específico")
public class FieldErrorItem {

  @Schema(example = "email")
  private String field;

  @Schema(example = "must be a well-formed email address")
  private String message;

  public FieldErrorItem() {}
  public FieldErrorItem(String field, String message) {
    this.field = field;
    this.message = message;
  }

  public String getField() { return field; }
  public void setField(String field) { this.field = field; }
  public String getMessage() { return message; }
  public void setMessage(String message) { this.message = message; }
}
