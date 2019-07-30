package com.example.foreign_registration.tools.general;

import com.example.foreign_registration.model.app.AppObjectType;
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

    public String buildFilterQuery(HashMap<String, String> parameters, AppObjectType appObjectType) {

        Set<String> filters = parameters.keySet();

        String syntax = getQueryBase(appObjectType);
        stringBuilder.append(syntax);

        String queryObject = convertForShortQueryObject(appObjectType);
        String fullQueryObject = convertForFullQueryObject(appObjectType);

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
                    stringBuilder.append(" p.number");
                    break;
                case "byAssessment":
                    stringBuilder.append(" a.number");
                    break;
                case "byStatus":
                    stringBuilder.append(" s.id = ");
                    break;
                case "byCooperationModel":
                    stringBuilder.append(" p.model_cooperation = '");
                    break;
                case "byOrderDateStart":
                    stringBuilder.append(" ");
                    stringBuilder.append(queryObject);
                    stringBuilder.append(".creationDate > '");
                    break;
                case "byOrderDateEnd":
                    stringBuilder.append(" ");
                    stringBuilder.append(queryObject);
                    stringBuilder.append(".creationDate < '");
            }

            if ("byStatus".equals(filter)) {
                stringBuilder.append(parameters.get(filter));
            } else if ("byOrderDateStart".equals(filter) || "byOrderDateEnd".equals(filter) || "byCooperationModel".equals(filter)) {
                stringBuilder.append(parameters.get(filter));
                stringBuilder.append("'");
            } else {
                stringBuilder.append(" LIKE CONCAT('%', '");
                stringBuilder.append(parameters.get(filter));
                stringBuilder.append("','%')");
            }

            i++;
        }
        stringBuilder.append(" ORDER BY ");
        stringBuilder.append(queryObject);
        stringBuilder.append(".number");
       //todo pytanie co ze zmienna stringBuilder.append(fullQueryObject);
        //stringBuilder.append("No DESC");
        stringBuilder.append(" DESC");

        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }

    public String convertForFullQueryObject(AppObjectType appObjectType) {
        String queryObj = null;

        switch (appObjectType) {
            case ORDER:
                queryObj = "order";
                break;
            case ASSESSMENT:
                queryObj = "assessment";
                break;
            case CALCULATION:
                queryObj = "calculation";
                break;
            case MARKETING_AUTHORIZATION:
                queryObj = "marketingAuthorization";
                break;
            default:
                queryObj = "";
        }
        return queryObj;
    }

    public String getQueryBase(AppObjectType appObjectType) {

        String queryBase = null;

        switch (appObjectType) {
            case ORDER:
                queryBase = "SELECT DISTINCT p FROM Process p " +
                        "LEFT JOIN p.assessments a " +
                        "LEFT JOIN p.product pr " +
                        "LEFT JOIN pr.product_status ps " +
                        "LEFT JOIN a.destined_product_status dps " +
                        "LEFT JOIN a.registration_country rc " +
                        "LEFT JOIN a.required_prod_qualification rpq " +
                        "LEFT JOIN p.product_qualification pq " +
                        "LEFT JOIN p.client cl " +
                        "LEFT JOIN p.status s " +
                        "WHERE";
                break;
            case ASSESSMENT:
                queryBase = "SELECT DISTINCT a FROM Assessment a " +
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
                break;
            case CALCULATION:
                break;
            case MARKETING_AUTHORIZATION:
                break;
            default:
                queryBase = "";
        }

        if (null == queryBase || "".equals(queryBase)) {
            //   throw new DynamicSqlQueryyBuilderException;
        }
        return queryBase;
    }

    public String convertForShortQueryObject(AppObjectType appObjectType) {

        String queryObj = null;

        switch (appObjectType) {
            case ORDER:
                queryObj = "p";
                break;
            case ASSESSMENT:
                queryObj = "a";
                break;
            case CALCULATION:
                queryObj = "c";
                break;
            case MARKETING_AUTHORIZATION:
                queryObj = "ma";
                break;
            default:
                queryObj = "";
        }
        return queryObj;
    }

}
