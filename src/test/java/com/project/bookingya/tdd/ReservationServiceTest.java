// ============================================================
// TDD - PRUEBAS UNITARIAS DEL SERVICIO DE RESERVAS
// ------------------------------------------------------------
// Propósito:
// Validar la lógica de negocio del servicio ReservationService
// de forma aislada utilizando mocks.
//
// Enfoque:
// - Se utiliza JUnit 5 para pruebas unitarias
// - Se utiliza Mockito para simular dependencias (repositories, mapper)
// - Se validan reglas de negocio internas del servicio
//
// Diferencia con otros enfoques:
// - BDD → comportamiento del negocio (Gherkin)
// - ATDD → validación end-to-end (API real)
// - TDD → validación de lógica interna (unitaria)
//
// Flujo:
// Test → Service → Mocks → Validación
// ============================================================

package com.project.bookingya.tdd;

import com.project.bookingya.dtos.ReservationDto;
import com.project.bookingya.entities.ReservationEntity;
import com.project.bookingya.entities.RoomEntity;
import com.project.bookingya.models.Reservation;
import com.project.bookingya.repositories.IReservationRepository;
import com.project.bookingya.repositories.IRoomRepository;
import com.project.bookingya.repositories.IGuestRepository;
import com.project.bookingya.services.ReservationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReservationServiceTest {

    // DEPENDENCIAS MOCKEADAS
    // Simulan el comportamiento de base de datos y utilidades

    private IReservationRepository reservationRepository;
    private IRoomRepository roomRepository;
    private IGuestRepository guestRepository;
    private ModelMapper modelMapper;

    private ReservationService service;

    // CONFIGURACIÓN INICIAL
    // Se ejecuta antes de cada prueba

    @BeforeEach
    void setup() {
        reservationRepository = mock(IReservationRepository.class);
        roomRepository = mock(IRoomRepository.class);
        guestRepository = mock(IGuestRepository.class);
        modelMapper = mock(ModelMapper.class);

        service = new ReservationService(
                reservationRepository,
                roomRepository,
                guestRepository,
                modelMapper
        );
    }

    // ESCENARIO 1: CREACIÓN DE UNA RESERVA
    // Valida que el servicio permita crear una reserva válida
    // cumpliendo reglas de negocio:
    // - fechas válidas
    // - huéspedes válidos
    // - habitación disponible

    @Test
    void shouldCreateReservationSuccessfully() {

        ReservationDto dto = new ReservationDto();
        dto.setCheckIn(LocalDateTime.now().plusDays(1));
        dto.setCheckOut(LocalDateTime.now().plusDays(3));
        dto.setGuestsCount(2);

        ReservationEntity entity = new ReservationEntity();

        RoomEntity room = new RoomEntity();
        room.setAvailable(true);
        room.setMaxGuests(2);

        Reservation reservation = new Reservation();

        when(roomRepository.findById(any()))
                .thenReturn(Optional.of(room));

        when(guestRepository.findById(any()))
                .thenReturn(Optional.of(mock()));

        when(modelMapper.map(dto, ReservationEntity.class))
                .thenReturn(entity);

        when(reservationRepository.saveAndFlush(any(ReservationEntity.class)))
                .thenReturn(entity);

        when(reservationRepository.existsOverlappingReservationForRoom(any(), any(), any(), any()))
                .thenReturn(false);

        when(reservationRepository.existsOverlappingReservationForGuest(any(), any(), any(), any()))
                .thenReturn(false);

        when(modelMapper.map(entity, Reservation.class))
                .thenReturn(reservation);

        Reservation result = service.create(dto);

        assertNotNull(result);
        verify(reservationRepository).saveAndFlush(any(ReservationEntity.class));
    }

    // ESCENARIO 2: OBTENCIÓN DE UNA RESERVA POR ID
    // Valida que el servicio retorne una reserva existente

    @Test
    void shouldReturnReservationById() {

        UUID id = UUID.randomUUID();

        ReservationEntity entity = new ReservationEntity();
        entity.setId(id);

        Reservation reservation = new Reservation();
        reservation.setId(id);

        when(reservationRepository.findById(id))
                .thenReturn(Optional.of(entity));

        when(modelMapper.map(entity, Reservation.class))
                .thenReturn(reservation);

        Reservation result = service.getById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    // ESCENARIO 3: CONSULTA DE UNA RESERVA POR HUÉSPED
    // Valida que el servicio permita actualizar una reserva existente
    // cumpliendo validaciones de negocio

    @Test
    void shouldUpdateReservationEntity() {

        UUID id = UUID.randomUUID();

        ReservationDto dto = new ReservationDto();
        dto.setCheckIn(LocalDateTime.now().plusDays(1));
        dto.setCheckOut(LocalDateTime.now().plusDays(3));
        dto.setGuestsCount(2);

        ReservationEntity entity = new ReservationEntity();
        entity.setId(id);

        RoomEntity room = new RoomEntity();
        room.setAvailable(true);
        room.setMaxGuests(2);

        Reservation reservation = new Reservation();
        reservation.setId(id);

        when(reservationRepository.findById(id))
                .thenReturn(Optional.of(entity));

        when(roomRepository.findById(any()))
                .thenReturn(Optional.of(room));

        when(guestRepository.findById(any()))
                .thenReturn(Optional.of(mock()));

        when(reservationRepository.saveAndFlush(any(ReservationEntity.class)))
                .thenReturn(entity);

        when(reservationRepository.existsOverlappingReservationForRoom(any(), any(), any(), any()))
                .thenReturn(false);

        when(reservationRepository.existsOverlappingReservationForGuest(any(), any(), any(), any()))
                .thenReturn(false);

        when(modelMapper.map(entity, Reservation.class))
                .thenReturn(reservation);

        Reservation result = service.update(dto, id);

        assertNotNull(result);
        verify(reservationRepository).saveAndFlush(any(ReservationEntity.class));
    }

    // ESCENARIO 4: ACTUALIZACIÓN DE UNA RESERVA EXISTENTE
    // Valida que el servicio elimine correctamente una reserva existente

    @Test
    void shouldDeleteReservationEntity() {

        UUID id = UUID.randomUUID();

        ReservationEntity entity = new ReservationEntity();
        entity.setId(id);

        when(reservationRepository.findById(id))
                .thenReturn(Optional.of(entity));

        doNothing().when(reservationRepository).delete(entity);
        doNothing().when(reservationRepository).flush();

        service.delete(id);

        verify(reservationRepository).delete(entity);
        verify(reservationRepository).flush();
    }
}