package org.spring.module.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Company {
    String symbol;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        return symbol != null ? symbol.equals(company.symbol) : company.symbol == null;
    }

    @Override
    public int hashCode() {
        return symbol != null ? symbol.hashCode() : 0;
    }
}
