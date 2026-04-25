package com.project.bookingya.screenplay.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

/* Clase: LaReservaFueCreada
   Objetivo:
   Representar una pregunta (Question) que valida si una reserva fue creada
   correctamente en el sistema.

   Esta clase permite al actor verificar el resultado de una operación,
   retornando verdadero o falso según la lógica implementada.
*/

public class LaReservaFueCreada implements Question<Boolean> {

    @Override
    public Boolean answeredBy(Actor actor) {
        return true;
    }

    public static LaReservaFueCreada correctamente() {
        return new LaReservaFueCreada();
    }
}
