package com.example.foreign_registration.tools.general;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Set;

@Component
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DynamicSqlSyntaxBuilder {

    private StringBuilder stringBuilder;

    public DynamicSqlSyntaxBuilder() {
        this.stringBuilder = new StringBuilder();
    }

    public String buildFilterQuery(HashMap<String, String> parameters) {

        Set<String> filters = parameters.keySet();

        String syntax = "SELECT DISTINCT a FROM Assessment a " +
                "LEFT JOIN a.processes p " +
                "LEFT JOIN p.product pr " +
                "LEFT JOIN pr.product_status ps " +
                "LEFT JOIN a.destined_product_status dps " +
                "LEFT JOIN a.registration_country rc " +
                "LEFT JOIN a.required_prod_qualification rpq " +
                "LEFT JOIN p.product_qualification pq " +
                "LEFT JOIN p.client cl " +
                "LEFT JOIN a.status s " +
                "WHERE";
        stringBuilder.append(syntax);

        int i = 0;

        for (String filter : filters) {
            if (i != 0) {
                stringBuilder.append(" AND");
            }

            switch (filter) {
                case "byClient":
                    stringBuilder.append(" cl.name");
                    break;
                case "byProductHL":
                    stringBuilder.append(" pr.name");
                    break;
                case "byProductClient":
                    stringBuilder.append(" p.destined_product_name");
                    break;
                case "byOrder":
                    stringBuilder.append(" p.orderNo");
                    break;
                case "byAssessment":
                    stringBuilder.append(" a.assessmentNo");
                    break;
                case "byStatus":
                    stringBuilder.append(" s.id = ");
                    break;
                case "byCooperationModel":
                    stringBuilder.append(" p.model_cooperation = '");
                    break;
                case "byOrderDateStart":
                    stringBuilder.append(" a.order_date > '");
                    break;
                case "byOrderDateEnd":
                    stringBuilder.append(" a.order_date < '");
            }

            if ("byStatus".equals(filter)) {
                stringBuilder.append(parameters.get(filter));
            } else if ("byOrderDateStart".equals(filter) || "byOrderDateEnd".equals(filter) || "byCooperationModel".equals(filter)){
                stringBuilder.append(parameters.get(filter));
                stringBuilder.append("'");
            } else {
                stringBuilder.append(" LIKE CONCAT('%', '");
                stringBuilder.append(parameters.get(filter));
                stringBuilder.append("','%')");
            }

            i++;
        }
        stringBuilder.append(" ORDER BY a.assessmentNo");

        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }

}
