package org.spring.module.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;


import javax.persistence.*;

@Entity
@Table(name = "quote")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quote {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "iex_realtime_price")
    private Double iexRealtimePrice;

    @Column(name = "latest_price")
    private Double latestPrice;

    @Column(name = "date")
    private Date date;

}
