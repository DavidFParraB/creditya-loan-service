package co.credit.app.api;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.*;

import co.credit.app.api.dto.LoanFilterDTO;
import co.credit.app.api.dto.LoanUpdateDTO;
import co.credit.app.model.loanfilter.LoanFilter;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.credit.app.api.dto.ErrorResponse;
import co.credit.app.api.dto.LoanDTO;
import co.credit.app.api.dto.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Configuration
public class RouterRest {

  @RouterOperations({@RouterOperation(path = "/api/loan", produces = {
      MediaType.APPLICATION_JSON_VALUE}, method = org.springframework.web.bind.annotation.RequestMethod.GET, beanClass = Handler.class, beanMethod = "listenGETUseCase", operation = @Operation(operationId = "getLoans", tags = {
      "Loans"}, summary = "Get all Loans", description = "Retrieve a list of all Loans", responses = {
      @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = LoanDTO.class)))),
      @ApiResponse(responseCode = "500", description = "Internal server error")})),
      @RouterOperation(path = "/api/loan/filter", produces = {
          MediaType.APPLICATION_JSON_VALUE}, method = org.springframework.web.bind.annotation.RequestMethod.POST, beanClass = Handler.class, beanMethod = "listenPOSTByFilterUseCase", operation = @Operation(operationId = "getLoansByFilter", tags = {
          "Loans"}, summary = "Get Loans by filter", description = "Retrieve a list of loans by filter", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Loan details", required = true, content = @Content(schema = @Schema(implementation = LoanFilterDTO.class))), responses = {
          @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = LoanDTO.class)))),
          @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Internal server error")})),
      @RouterOperation(path = "/api/loan", produces = {
          MediaType.APPLICATION_JSON_VALUE}, method = org.springframework.web.bind.annotation.RequestMethod.POST, beanClass = Handler.class, beanMethod = "listenPOSTUseCase", operation = @Operation(operationId = "createLoan", tags = {
          "Loans"}, summary = "Create a new Loan", description = "Create a new loan with the provided details", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Loan details", required = true, content = @Content(schema = @Schema(implementation = LoanDTO.class))), responses = {
          @ApiResponse(responseCode = "200", description = "Loan created", content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
          @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Internal server error")})),
      @RouterOperation(path = "/api/loan/{id}", produces = {
          MediaType.APPLICATION_JSON_VALUE}, method = org.springframework.web.bind.annotation.RequestMethod.PUT, beanClass = Handler.class, beanMethod = "listenPUTUseCase", operation = @Operation(operationId = "putLoan", tags = {
          "Loans"}, summary = "Update loan status by Id", description = "Retrieve Ok Message", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Loan details", required = true, content = @Content(schema = @Schema(implementation = LoanUpdateDTO.class))),
          parameters = {
          @Parameter(in = ParameterIn.PATH, name = "id", description = "User document", required = true, schema = @Schema(type = "string"))}, responses = {
          @ApiResponse(responseCode = "200", description = "User found", content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
          @ApiResponse(responseCode = "404", description = "User not found"),
          @ApiResponse(responseCode = "500", description = "Internal server error")}))})
  @Bean
  public RouterFunction<ServerResponse> routerFunction(Handler handler) {
    return route(GET("/api/loan"), handler::listenGETUseCase)
        .andRoute(POST("/api/loan/filter"),handler::listenPOSTByFilterUseCase)
        .andRoute(POST("/api/loan"), handler::listenPOSTUseCase)
        .andRoute(PUT("/api/loan/{id}"), handler::listenPUTUseCase);
  }
}
