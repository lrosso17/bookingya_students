// ============================================================
// QUESTION: ResponseStatus
// ------------------------------------------------------------
// Propósito:
// Permite al actor responder a la pregunta:
// "¿Cuál fue el código de estado HTTP de la última respuesta?"
//
// Patrón Screenplay:
// - Las Questions encapsulan lo que el actor puede observar
// - Implementan la interfaz Question<T> de Serenity
// - Recuperan datos de la memoria del actor con recall()
// - Se usan en conjunto con seeThat() en los @Then de Cucumber
//
// Uso:
//   actor.should(seeThat(ResponseStatus.code(), equalTo(200)))
// ============================================================

package com.project.bookingya.screenplay.questions;

import io.restassured.response.Response;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

/* Clase: ResponseStatus
   Objetivo:
   Obtener el código de estado HTTP de la última respuesta almacenada
   en la memoria del actor.

   Esta clase permite validar si una operación fue exitosa (ej: 200 OK)
   dentro de los escenarios de prueba.
*/

public class ResponseStatus implements Question<Integer> {

    @Override
    public Integer answeredBy(Actor actor) {

        Response response = actor.recall("lastResponse");

        if (response == null) {
            throw new RuntimeException(
                    "No hay respuesta almacenada en la memoria del actor. " +
                            "Verifica que la Task ejecutada guarde 'lastResponse' con actor.remember()"
            );
        }

        return response.statusCode();
    }

    public static ResponseStatus code() {
        return new ResponseStatus();
    }
}
