package com.example.foreign_registration.tools.general;

import com.example.foreign_registration.model.app.Filters;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class FilterManager {


    public boolean isOtherThenKeyWord(HashMap<String, String> filtersToCheck) {

        for (Filters filters : Filters.values()) {
            System.out.println(filters.name());
            if (filtersToCheck.containsKey(filters.name())) {
                return true;
            }
        }
        return false;
    }

}
