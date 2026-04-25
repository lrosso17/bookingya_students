// ============================================================
// TASK: ConsultarReserva
// ------------------------------------------------------------
// Propósito:
// Representa la acción de consultar una reserva en el sistema.
// Soporta dos variantes:
//   - porId()   → GET /reservation/{id}
//   - porGuest() → GET /reservation/guest/{guestId}
//
// Patrón Screenplay:
// - Una sola clase Task parametrizada por tipo de consulta
// - Usa un enum interno para distinguir el tipo de consulta
// - Recupera el ID correspondiente desde la memoria del actor
//
// Corrección sobre código original:
// - El código original intentaba usar "actor" como variable estática
//   en un método estático, lo cual es un error de compilación.
// - La solución correcta es parametrizar la Task y ejecutar
//   la lógica dentro de performAs(actor).
// ============================================================

package com.project.bookingya.screenplay.tasks;

import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

/* Clase: ConsultarReserva
   Objetivo:
   Representar la acción de consultar reservas en el sistema.

   Esta clase permite realizar consultas de dos tipos:
   - Por ID de reserva
   - Por huésped

   Utiliza la memoria del actor para obtener los datos necesarios
   y ejecutar las peticiones correspondientes.
*/

public class ConsultarReserva implements Task {

    // ----------------------------------------------------------
    // TIPO DE CONSULTA
    // Permite distinguir el endpoint a llamar sin duplicar clases
    // ----------------------------------------------------------
    public enum TipoConsulta {
        POR_ID, POR_GUEST
    }

    private final TipoConsulta tipo;

    public ConsultarReserva(TipoConsulta tipo) {
        this.tipo = tipo;
    }

    // ----------------------------------------------------------
    // PERFORMAS: lógica de la tarea
    // ----------------------------------------------------------
    @Override
    public <T extends Actor> void performAs(T actor) {

        Response response;

        switch (tipo) {

            case POR_ID -> {
                // GET /reservation/{id}
                String reservationId = actor.recall("reservationId");
                response = SerenityRest.given()
                        .get("/reservation/" + reservationId);
            }

            case POR_GUEST -> {
                // GET /reservation/guest/{guestId}
                String guestId = actor.recall("guestId");
                response = SerenityRest.given()
                        .get("/reservation/guest/" + guestId);
            }

            default -> throw new IllegalArgumentException("Tipo de consulta no soportado: " + tipo);
        }

        // Guardar la respuesta en la memoria del actor
        actor.remember("lastResponse", response);
    }

    // ----------------------------------------------------------
    // FACTORY METHODS
    // ----------------------------------------------------------

    /** Consulta una reserva por su UUID */
    public static ConsultarReserva porId() {
        return new ConsultarReserva(TipoConsulta.POR_ID);
    }

    /** Consulta todas las reservas de un huésped */
    public static ConsultarReserva porGuest() {
        return new ConsultarReserva(TipoConsulta.POR_GUEST);
    }
}
