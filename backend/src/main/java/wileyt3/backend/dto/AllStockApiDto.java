package wileyt3.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
//@JsonIgnoreProperties(ignoreUnknown = true)
public class AllStockApiDto {
//    private String id;

    @JsonProperty("class") // This annotation maps the JSON property "class" to this field
    private String classType;

    private String exchange;
    private String symbol;
    private String name;
    private List<String> attributes;

}
