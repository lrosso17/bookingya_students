package com.project.bookingya.screenplay.abilities;

import net.serenitybdd.screenplay.Ability;

/* Clase: CallApi
   Objetivo:
   Definir una habilidad (Ability) del actor para interactuar con una API
   mediante una URL base.

   Esta clase permite almacenar la URL base que será utilizada por las
   Tasks y Questions para realizar peticiones HTTP.
*/

public class CallApi implements Ability {
    private final String baseUrl;

    public CallApi(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String baseUrl() {
        return baseUrl;
    }

    public static CallApi at(String url) {
        return new CallApi(url);
    }
}
