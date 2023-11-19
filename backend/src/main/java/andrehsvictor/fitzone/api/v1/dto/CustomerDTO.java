package andrehsvictor.fitzone.api.v1.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private String fullName;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date expiration;
    private Boolean accountNonExpired;
    private String role;
}
