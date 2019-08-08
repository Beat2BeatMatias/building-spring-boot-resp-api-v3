package me.salisuwy.StressTest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GrantRefundCreationCommand {

    @NotNull
    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("external_references")
    private List<GrantRefundExternalReference> externalReferences;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public List<GrantRefundExternalReference> getExternalReferences() {
        return externalReferences;
    }

    public void setExternalReferences(List<GrantRefundExternalReference> externalReferences) {
        this.externalReferences = externalReferences;
    }
}
