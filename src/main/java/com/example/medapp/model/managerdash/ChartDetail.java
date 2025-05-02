package com.example.medapp.model.managerdash;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Entity
@NamedNativeQuery(name = "ChartDetail.getDailySales", query = "SELECT SalesTable.SalesDate billDate,COALESCE(ROUND(SUM(mbm.net_price), 2), 0) AS totalAmount, "
        +
        "TO_CHAR(SalesTable.SalesDate, 'FMDay') AS dynamicParam, " +
        "'day' AS dynamicKeyName " +
        "FROM ( " +
        "SELECT DATE(CURRENT_DATE - INTERVAL '6 day') AS SalesDate UNION ALL " +
        "SELECT DATE(CURRENT_DATE - INTERVAL '5 day') AS SalesDate UNION ALL " +
        "SELECT DATE(CURRENT_DATE - INTERVAL '4 day') AS SalesDate UNION ALL " +
        "SELECT DATE(CURRENT_DATE - INTERVAL '3 day') AS SalesDate UNION ALL " +
        "SELECT DATE(CURRENT_DATE - INTERVAL '2 day') AS SalesDate UNION ALL " +
        "SELECT DATE(CURRENT_DATE - INTERVAL '1 day') AS SalesDate UNION ALL " +
        "SELECT DATE(CURRENT_DATE) " +
        ") SalesTable " +
        "LEFT JOIN medapp_bill_master mbm ON mbm.bill_date = SalesTable.SalesDate " +
        "GROUP BY SalesTable.SalesDate " +
        "ORDER BY SalesTable.SalesDate", resultSetMapping = "DailychartMapping")
@SqlResultSetMapping(name = "DailychartMapping", classes = @ConstructorResult(targetClass = com.example.medapp.model.managerdash.ChartDetail.class, columns = {
        @ColumnResult(name = "totalAmount", type = java.math.BigDecimal.class),
        @ColumnResult(name = "dynamicParam", type = java.lang.String.class),
        @ColumnResult(name = "dynamicKeyName", type = java.lang.String.class),
        @ColumnResult(name = "billDate", type = java.time.LocalDate.class)
}))
@NamedNativeQuery(name = "ChartDetail.getMonthlySales", query = """
        WITH RECURSIVE MonthRange AS (
            SELECT
                CURRENT_DATE AS Days,
                TO_CHAR(CURRENT_DATE, 'MON') AS Months
            UNION ALL
            SELECT
                (Days - INTERVAL '1 month')::date,
                TO_CHAR((Days - INTERVAL '1 month')::date, 'MON')
            FROM MonthRange
            WHERE (Days - INTERVAL '1 month')::date > (CURRENT_DATE - INTERVAL '12 months')::date
        ), MonthlySales AS (
            SELECT
                to_char(current_date, 'YYYY-MM') AS eachMonthYear,
                round(sum(net_price), 2) AS Sales
            FROM medapp_bill_master
            GROUP BY to_char(current_date, 'YYYY-MM')
        )
        SELECT mr.Days as billDate,
            mr.Months as dynamicParam,
            COALESCE(ms.Sales, 0) AS totalAmount,
            'Month' as dynamicKeyName
        FROM MonthRange mr
        LEFT JOIN MonthlySales ms ON TO_CHAR(mr.Days, 'YYYY-MM') = ms.eachMonthYear
        """, resultSetMapping = "MonthlychartMapping")
@SqlResultSetMapping(name = "MonthlychartMapping", classes = @ConstructorResult(targetClass = com.example.medapp.model.managerdash.ChartDetail.class, columns = {
        @ColumnResult(name = "billDate", type = java.time.LocalDate.class),
        @ColumnResult(name = "dynamicParam", type = java.lang.String.class),
        @ColumnResult(name = "totalAmount", type = java.math.BigDecimal.class),
        @ColumnResult(name = "dynamicKeyName", type = java.lang.String.class)
}))
@NamedNativeQuery(name = "ChartDetail.GetSalesmanCurrentDayPef", query = """
         select
         max(mbm.bill_date) billDate,
         coalesce(ml.user_id,'') dynamicParam,
         coalesce (sum(round(mbm.net_price,2))) totalAmount,
         'userId' dynamicKeyName
          from  medapp_login ml left join medapp_bill_master mbm on mbm.login_id=ml.login_id 
         where ml."role" ='Biller' 
         and bill_master_id is not null
         and to_char(bill_date,'YYYY-MM-DD') = to_char(current_date,'YYYY-MM-DD')
         group by ml.user_id 
        """, resultSetMapping = "SalesmanCurrentdayPefMapping")
@SqlResultSetMapping(name = "SalesmanCurrentdayPefMapping", classes = @ConstructorResult(targetClass = com.example.medapp.model.managerdash.ChartDetail.class, columns = {
        @ColumnResult(name = "billDate", type = java.time.LocalDate.class),
        @ColumnResult(name = "dynamicParam", type = java.lang.String.class),
        @ColumnResult(name = "totalAmount", type = java.math.BigDecimal.class),
        @ColumnResult(name = "dynamicKeyName", type = java.lang.String.class)
}))
@NamedNativeQuery(name = "ChartDetail.getSalesmanCurrentMonthlyPef", query = """
         select
         max(mbm.bill_date) billDate,
         coalesce(ml.user_id,'') dynamicParam,
         coalesce (sum(round(mbm.net_price,2))) totalAmount,
         'userId' dynamicKeyName
          from  medapp_login ml left join medapp_bill_master mbm on mbm.login_id=ml.login_id 
         where ml."role" ='Biller' 
         and bill_master_id is not null
         and to_char(bill_date,'YYYY-MM') = to_char(current_date,'YYYY-MM')
         group by ml.user_id 
        """, resultSetMapping = "SalesmanCurrentMonthPefMapping")
@SqlResultSetMapping(name = "SalesmanCurrentMonthPefMapping", classes = @ConstructorResult(targetClass = com.example.medapp.model.managerdash.ChartDetail.class, columns = {
        @ColumnResult(name = "billDate", type = java.time.LocalDate.class),
        @ColumnResult(name = "dynamicParam", type = java.lang.String.class),
        @ColumnResult(name = "totalAmount", type = java.math.BigDecimal.class),
        @ColumnResult(name = "dynamicKeyName", type = java.lang.String.class)
}))
public class ChartDetail {

    @Id
    private LocalDate billDate;

    private BigDecimal totalAmount;

    @JsonIgnore
    private String dynamicParam;

    @JsonIgnore
    private String dynamicKeyName;

    public ChartDetail() {
    }

    public ChartDetail(BigDecimal totalAmount, String dynamicParam, String dynamicKeyName) {
        this.totalAmount = totalAmount;
        this.dynamicParam = dynamicParam;
        this.dynamicKeyName = dynamicKeyName;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDynamicParam() {
        return dynamicParam;
    }

    public void setDynamicParam(String dynamicParam) {
        this.dynamicParam = dynamicParam;
    }

    public String getDynamicKeyName() {
        return dynamicKeyName;
    }

    public void setDynamicKeyName(String dynamicKeyName) {
        this.dynamicKeyName = dynamicKeyName;
    }

    @JsonAnyGetter
    public Map<String, Object> getDynamicFieldAsMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("totalAmount", totalAmount);
        if (dynamicKeyName != null) {
            map.put(dynamicKeyName, dynamicParam);
        }
        return map;
    }

    @Override
    public String toString() {
        return "ChartDetail [totalAmount=" + totalAmount + ", dynamicParam=" + dynamicParam + ", dynamicKeyName="
                + dynamicKeyName + "]";
    }

    // public static void main(String[] args) throws Exception {
    // ChartDetail detail = new ChartDetail(0.02, "April", "Month");

    // ObjectMapper mapper = new ObjectMapper();
    // String json = mapper.writeValueAsString(detail);
    // System.out.println(json);
    // }

}
