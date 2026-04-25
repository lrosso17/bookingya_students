// ============================================================
// ABILITY: CallBookingApi
// ------------------------------------------------------------
// Propósito:
// Representa la HABILIDAD del actor para interactuar con la API
// de reservas de BookingYa.
//
// Patrón Screenplay:
// - Las Abilities definen QUÉ puede hacer un actor
// - Se asignan al actor al momento de su creación
// - Las Tasks y Questions las consumen vía actor.abilityTo(...)
//
// Uso:
//   actor.whoCan(CallBookingApi.at("http://localhost:8080/api"))
// ============================================================

package com.project.bookingya.screenplay.abilities;

import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;

/* Clase: CallBookingApi
   Objetivo:
   Definir una habilidad personalizada del actor para interactuar con la API
   de BookingYa.

   Esta clase permite que el actor tenga acceso a la URL base del sistema,
   facilitando su uso en las Tasks y Questions mediante el patrón Screenplay.
*/

public class CallBookingApi implements Ability {

    private final String baseUrl;

    private CallBookingApi(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    // Crea la habilidad apuntando a la URL base de la API
    public static CallBookingApi at(String url) {
        return new CallBookingApi(url);
    }

    // Metodo que permite que las Tasks y Questions recuperen la habilidad
    public static CallBookingApi as(Actor actor) {
        return actor.abilityTo(CallBookingApi.class);
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
