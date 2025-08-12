package com.emakers.api_biblioteca.DTOs;

import java.time.OffsetDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ErrorResponse", description = "Formato padrão de erros da API")
public class ErrorResponseDTO {

    @Schema(example = "400", description = "Código HTTP do erro")
    private int status;

    @Schema(example = "Bad Request", description = "Tipo resumido do erro")
    private String error;

    @Schema(example = "Erro de validação", description = "Mensagem amigável explicando o erro")
    private String message;

    @Schema(example = "/pessoas", description = "Caminho que gerou o erro")
    private String path;

    @Schema(example = "2025-08-12T18:24:33.123-03:00", description = "Data e hora do erro")
    private OffsetDateTime timestamp;

    @ArraySchema(schema = @Schema(implementation = FieldErrorItem.class,description = "Lista de erros de campo (em validações)"))
    private List<FieldErrorItem> fieldErrors;

    public ErrorResponseDTO() {}

    public ErrorResponseDTO(int status, String error, String message, String path, OffsetDateTime timestamp) {  
    this.status = status;
    this.error = error;
    this.message = message;
    this.path = path;
    this.timestamp = timestamp;
    }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public OffsetDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(OffsetDateTime timestamp) { this.timestamp = timestamp; }

    public List<FieldErrorItem> getFieldErrors() { return fieldErrors; }
    public void setFieldErrors(List<FieldErrorItem> fieldErrors) { this.fieldErrors = fieldErrors; }
}
