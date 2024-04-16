package wileyt3.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AllStockApiDto {

    @JsonProperty("class") // This annotation maps the JSON property "class" to this field
    private String classType;

    private String exchange;
    private String symbol;
    private String name;

}
