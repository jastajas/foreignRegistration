package com.example.foreign_registration.model.app;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.LinkedHashMap;

@Component
@Scope(scopeName = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class FilterParameter {

    private LinkedHashMap<String, String> filters;

    public FilterParameter() {
        filters = new LinkedHashMap<>();
    }

	/*Filtrowanie za pomocą parametrów jest podzielone na dwa mechanizmy:
	Filtering by parameters is divided into two ways:
	 1. Szukanie za pomocą słowa kluczowego
	 1. Filtering by keyword
	 2. Szukanie za pomocą nazwy klienta, nazwy produktu HL, nazwy produktu klienta,
	 2. Filtering by others parameters such as client name, HL product name, client product name,
	 statusu projektu, nr zlecenia, nr oceny
	 project status, order no, assessment no
	 Dwa poniższe mechanizmy nie mogą funkcjonować jednocześnie. Albo szukanie po słowie
	 Two ways of filtering can not be used at the same time. It is set to use one of these two ways:
	 kluczowym albo po zestawie parametrów drugiej grupy
	 by keyword or by set of parameters from second filtering way*/

    public void addFilterParameter(String filterKey, String filterValue) {
        //motoda obsługuje mechanizm filtrowania nr2, czyli weryfikacja i usunięcie
        //słowa kluczowego i dodanie parametru filtorwania
        if (filters.containsKey("byKeyword")) {
            filters.remove("byKeyword");
        }
        filters.put(filterKey, filterValue);
    }

    public void addKeyWord(String filterValue) {
        if (!filters.isEmpty()) {
            filters.clear();
        }
        filters.put("byKeyword", filterValue);
    }

    public void removeFilterParameters(boolean allParam, String filterKey) {
        if (allParam) {
            filters.clear();
        } else {
            filters.remove(filterKey);
        }
    }

    public LinkedHashMap<String, String> getFiltersList() {
        return filters;
    }

}

