package co.credit.app.consumer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserResponseDTO {
    private Long id;
    private String name;
    @JsonProperty("last_name")
    private String lastName;
    private String email;
    private String document;
    private String phone;
    private Double salary;
    private Long roleId;
}