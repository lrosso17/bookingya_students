// ============================================================
// TASK: ConsultarTodasReservas
// ------------------------------------------------------------
// Propósito:
// Representa la acción de consultar todas las reservas del sistema.
//
// Patrón Screenplay:
// - No requiere parámetros del actor
// - Ejecuta GET /reservation
// - Guarda la respuesta para validación posterior
// ============================================================

package com.project.bookingya.screenplay.tasks;

import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

/* Clase: ConsultarTodasReservas
   Objetivo:
   Representar la acción de consultar todas las reservas del sistema.

   Esta tarea permite al actor obtener la lista completa de reservas
   mediante una petición GET al endpoint correspondiente.
*/

public class ConsultarTodasReservas implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        // Llamar al endpoint GET /reservation (lista completa)
        Response response = SerenityRest.given()
                .get("/reservation");

        // Guardar la respuesta en memoria del actor
        actor.remember("lastResponse", response);
    }

    // ----------------------------------------------------------
    // FACTORY METHOD
    // ----------------------------------------------------------
    public static ConsultarTodasReservas enElSistema() {
        return Tasks.instrumented(ConsultarTodasReservas.class);
    }
}

