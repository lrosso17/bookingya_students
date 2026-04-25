# ****************************************************************************
# FEATURE: Gestión de reservas en BookingYa
# ------------------------------------------------------------
# Objetivo:
# Validar el comportamiento funcional del Sistema backend para la
# gestión de reservas de habitaciones al estilo Booking / Airbnb,
# cubriendo el ciclo completo (CRUD) desde la perspectiva del negocio.
#
# Alcance:
# - Creación de una reserva
# - Obtención de una reserva por ID
# - Consulta de una reserva por huésped
# - Actualización de una reserva existente
# - Eliminación de una reserva

#
# Enfoque:
# Se utiliza BDD (Behavior Driven Development) para definir
# escenarios en lenguaje natural Gherkin alineados con las reglas de negocio.
# ***********************************************************+****************

Feature: Gestión de reservas en BookingYa

  # ------------------------------------------------------------
  # ESCENARIO 1: CREACIÓN DE UNA RESERVA
  # Objetivo: Validar que el sistema permita crear una reserva
  # con datos válidos cuando existe un huésped y habitación disponible.
  # Resultado esperado:
  # - HTTP 200
  # - Reserva creada correctamente
  # ------------------------------------------------------------
  Scenario: Crear una reserva correctamente
    Given existe un huésped y una habitación disponible
    When creo una reserva válida
    Then la reserva se crea exitosamente

  # ------------------------------------------------------------
  # ESCENARIO 2: OBTENCIÓN DE UNA RESERVA POR ID
  # Objetivo: Validar que el sistema retorne una reserva específica
  # usando su identificador único.
  # Resultado esperado:
  # - Se obtiene la reserva correcta
  # - Los datos coinciden con la reserva creada
  # ------------------------------------------------------------
  Scenario: Obtener reserva por ID
    Given existe una reserva creada
    When consulto la reserva por su ID
    Then debo obtener la reserva por ID correctamente

# ------------------------------------------------------------
  # ESCENARIO 3: CONSULTA DE UNA RESERVA POR HUÉSPED
  # Objetivo: Validar que el sistema retorne todas las reservas
  # asociadas a un huésped específico.
  # Resultado esperado:
  # - Se obtiene una lista de reservas
  # - La lista corresponde al huésped consultado
  # ------------------------------------------------------------
  Scenario: Consultar reservas por huésped
    Given existe una reserva creada
    When consulto las reservas del huésped
    Then debo obtener las reservas del huésped

# ------------------------------------------------------------
  # ESCENARIO 4: ACTUALIZACIÓN DE UNA RESERVA EXISTENTE
  # Objetivo: Validar que el sistema permita modificar una reserva existente.
  # Resultado esperado:
  # - Cambios aplicados correctamente
  # - Información actualizada en el sistema
  # ------------------------------------------------------------
  Scenario: Actualizar una reserva existente
    Given existe una reserva creada
    When actualizo la reserva
    Then la reserva debe actualizarse correctamente

  # ------------------------------------------------------------
  # ESCENARIO 5: ELIMINACIÓN DE UNA RESERVA
  # Objetivo: Validar que el sistema permita eliminar una reserva.
  # Resultado esperado:
  # - La reserva deja de existir
  # - No se encuentra en consultas posteriores
  # ------------------------------------------------------------
  Scenario: Eliminar una reserva
    Given existe una reserva creada
    When elimino la reserva
    Then la reserva debe eliminarse correctamente

  #@ignore
  #Feature: Escenarios negativos de reservas (no ejecutables)

  # Regla: checkIn debe ser menor que checkOut
  # Scenario: No permitir reserva con fechas inválidas
  #  Given existe un huésped y una habitación disponible
  #  When intento crear una reserva con checkIn mayor que checkOut
  #  Then el sistema debe responder con error de validación

  # Regla: no solapamiento de reservas
  # Scenario: No permitir reservas solapadas
  #  Given existe una reserva creada
  #  When intento crear otra reserva en la misma habitación en las mismas fechas
  #  Then el sistema debe rechazar la reserva

  # Regla: capacidad máxima de habitación
  # Scenario: No permitir más huéspedes que la capacidad
  #  Given existe un huésped y una habitación disponible
  #  When intento crear una reserva con más huéspedes que la capacidad
  #  Then el sistema debe responder con error

  # Regla: habitación disponible
  # Scenario: No permitir reservar habitación no disponible
  #  Given existe una habitación no disponible
  #  When intento crear una reserva
  #  Then el sistema debe rechazar la operación