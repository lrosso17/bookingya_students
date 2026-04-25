// ============================================================
// TASK: EliminarReserva
// ------------------------------------------------------------
// Propósito:
// Representa la acción de eliminar (cancelar) una reserva.
//
// Patrón Screenplay:
// - Recupera el reservationId desde la memoria del actor
// - Ejecuta DELETE /reservation/{id}
// - Guarda la respuesta para que ResponseStatus pueda validarla
//
// Corrección sobre código original:
// - El original no guardaba la respuesta del DELETE en memoria,
//   lo que causaba NullPointerException al validar el status
//   en el @Then del paso de Cucumber.
// ============================================================

package com.project.bookingya.screenplay.tasks;

import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

/* Clase: EliminarReserva
   Objetivo:
   Representar la acción de eliminar una reserva existente en el sistema.

   Esta tarea utiliza el reservationId almacenado en la memoria del actor
   para ejecutar una petición DELETE y eliminar la reserva.
*/

public class EliminarReserva implements Task {

    // ----------------------------------------------------------
    // PERFORMAS: lógica de la tarea
    // ----------------------------------------------------------
    @Override
    public <T extends Actor> void performAs(T actor) {

        // 1. Recuperar el ID de la reserva a eliminar
        String reservationId = actor.recall("reservationId");

        if (reservationId == null) {
            throw new RuntimeException("No hay reservationId en memoria del actor para eliminar");
        }

        // 2. Llamar al endpoint DELETE /reservation/{id}
        Response response = SerenityRest.given()
                .delete("/reservation/" + reservationId);

        // 3. Guardar la respuesta en memoria del actor
        //    (CRÍTICO: sin esto, ResponseStatus lanza NullPointerException)
        actor.remember("lastResponse", response);
    }

    // ----------------------------------------------------------
    // FACTORY METHOD
    // ----------------------------------------------------------
    public static EliminarReserva existente() {
        return Tasks.instrumented(EliminarReserva.class);
    }
}
