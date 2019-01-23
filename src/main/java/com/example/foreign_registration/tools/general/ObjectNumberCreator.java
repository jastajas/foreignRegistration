package com.example.foreign_registration.tools.general;

import com.example.foreign_registration.model.app.AppObject;

public class ObjectNumberCreator{

    public static String createObjectNo(String maxCurrentNo, String currentYear, AppObject objectType) {

        int maxCurrentId = 0;
        try {
            maxCurrentId = Integer.valueOf(maxCurrentNo.substring(0,5));
        }catch (NumberFormatException e){
		//Todo jeżeli niepoprawny format to wyrzucenie błędu, stworzyć błąd: 1 na nieporprawny format, a drugi na przekroczenie maksymalnej liczby
            maxCurrentId = 99998;
        }

        maxCurrentId++;
        int objectNoLength = String.valueOf(maxCurrentId).length();

        StringBuilder objectNoBuilder = new StringBuilder("0");
        while (objectNoBuilder.length() < 5 - objectNoLength) {
            objectNoBuilder.append("0");
        }

        objectNoBuilder.append(maxCurrentId);
        objectNoBuilder.append("/");
        objectNoBuilder.append(objectType.getObjectShortcut());
        objectNoBuilder.append("/");
        objectNoBuilder.append(currentYear);

        return objectNoBuilder.toString();
    }	
	
}

