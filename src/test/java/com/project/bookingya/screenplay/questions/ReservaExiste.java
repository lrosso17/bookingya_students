// ============================================================
// QUESTION: ReservaExiste
// ------------------------------------------------------------
// Propósito:
// Permite al actor responder a la pregunta:
// "¿La última respuesta contiene una reserva con ID?"
//
// Patrón Screenplay:
// - Valida que el cuerpo de respuesta incluya un ID de reserva
// - Se usa para verificar creación exitosa de una reserva
//
// Uso:
//   actor.should(seeThat(ReservaExiste.enSistema(), is(true)))
// ============================================================

package com.project.bookingya.screenplay.questions;

import io.restassured.response.Response;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

/* Clase: ReservaExiste
   Objetivo:
   Validar si la última respuesta contiene una reserva creada en el sistema.

   Esta clase permite al actor verificar que el response incluya un ID válido,
   confirmando que la reserva fue creada correctamente.
*/

public class ReservaExiste implements Question<Boolean> {

    @Override
    public Boolean answeredBy(Actor actor) {

        Response response = actor.recall("lastResponse");

        if (response == null) {
            return false;
        }

        String id = response.jsonPath().getString("id");
        return id != null && !id.isBlank();
    }

    public static ReservaExiste enSistema() {
        return new ReservaExiste();
    }
}
