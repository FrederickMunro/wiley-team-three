package wileyt3.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class StockApiDto {
    private String id;

    @JsonProperty("class") // This annotation maps the JSON property "class" to this field
    private String classType;

    private String exchange;
    private String symbol;
    private String name;
    private String status;
    private boolean tradable;
    private boolean marginable;
    private int maintenanceMarginRequirement;
    private boolean shortable;
    private boolean easyToBorrow;
    private boolean fractionable;
    private List<String> attributes;
}
