package com.example.foreign_registration.tools.general;

import com.example.foreign_registration.model.app.AppObjectType;
import com.example.foreign_registration.model.exceptions.OversizeNumberException;

public class ObjectNumberCreator{

    public static String createObjectNo(String maxCurrentNo, String currentYear, AppObjectType objectType) throws OversizeNumberException {


          int  maxCurrentId = Integer.valueOf(maxCurrentNo);
//        }catch (NumberFormatException e){
        if(maxCurrentId >= 99999){
            throw new OversizeNumberException();
        }
//		//Todo jeżeli niepoprawny format to wyrzucenie błędu, stworzyć błąd: 1 na nieporprawny format, a drugi na przekroczenie maksymalnej liczby


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

