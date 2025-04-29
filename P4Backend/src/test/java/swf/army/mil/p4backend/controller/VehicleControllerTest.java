package swf.army.mil.p4backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.ResponseStatus;
import swf.army.mil.p4backend.Entity.Vehicle;
import swf.army.mil.p4backend.service.VehicleService;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VehicleController.class)
@AutoConfigureMockMvc
class VehicleControllerTest {

    public static final Vehicle FRONTIER = new Vehicle("Nissan", "Frontier", 2023, 36000d, false);
    public static final Vehicle CIVIC = new Vehicle("Honda", "Civic", 2019, 14000d, true);

    ArrayList<Vehicle> vehicles;

    @Autowired
    ObjectMapper mapper;

    @MockitoBean
    VehicleService vehicleService;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        vehicles = new ArrayList<>();
        vehicles.add(FRONTIER);
        vehicles.add(CIVIC);
        vehicles.get(0).setId(1L);
        vehicles.get(1).setId(2L);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetAllVehicles() throws Exception {
        when(vehicleService.getAll()).thenReturn(vehicles);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/vehicles/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(vehicles.size()))
                .andExpect(jsonPath("$[0].make").value(FRONTIER.getMake()))
                .andExpect(jsonPath("$[0].model").value(FRONTIER.getModel()))
                .andExpect(jsonPath("$[1].used").value(CIVIC.getUsed()));
        verify(vehicleService, times(1)).getAll();
    }

    @Test
    void givenValidId_shouldGetVehicleById() throws Exception {
        when(vehicleService.getById(1L)).thenReturn(FRONTIER);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/vehicle/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.make").value(FRONTIER.getMake()))
                .andExpect(jsonPath("$.model").value(FRONTIER.getModel()))
                .andExpect(jsonPath("$.used").value(FRONTIER.getUsed()));
        verify(vehicleService, times(1)).getById(1L);
    }

    @Test
    void givenInvalidId_shouldReturnNull() throws Exception {
        when(vehicleService.getById(1L)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/vehicle/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
        verify(vehicleService, times(1)).getById(1L);
    }

    @Test
    void givenValidId_shouldDeleteVehicleById() throws Exception {
        // Returning true if deleted, false if not deleted.
        // See internal server error for more details
        when(vehicleService.deleteById(1L)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/vehicle/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isBoolean())
                .andExpect(jsonPath("$").value(true));
        verify(vehicleService, times(1)).deleteById(1L);
    }

    @Test
    void givenInvalidId_deleteShouldReturnFalse() throws Exception {
        when(vehicleService.deleteById(1L)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/vehicle/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isBoolean())
                .andExpect(jsonPath("$").value(false));
        verify(vehicleService, times(1)).deleteById(1L);
    }

    @Test
    void givenValidInput_shouldCreateNewVehicle() throws Exception {
        var modifiedCivic = CIVIC;
        modifiedCivic.setId(3L);
        when(vehicleService.create(any(Vehicle.class))).thenReturn(modifiedCivic);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/vehicle" )
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(CIVIC)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value(modifiedCivic.getId()))
                .andExpect(jsonPath("$.model").value(CIVIC.getModel()))
                .andExpect(jsonPath("$.price").value(CIVIC.getPrice()))
                .andExpect(jsonPath("$.used").value(CIVIC.getUsed()));
        verify(vehicleService, times(1)).create(any(Vehicle.class));
    }

    @Test
    void givenValidInput_shouldUpdateVehicleById() throws Exception {
        var modifiedCivic = vehicles.get(1);
        modifiedCivic.setPrice(1000d);
        when(vehicleService.update(any(Vehicle.class), eq(modifiedCivic.getId()))).thenReturn(Optional.of(modifiedCivic));

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/vehicle/" + modifiedCivic.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(modifiedCivic)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value(modifiedCivic.getId()))
                .andExpect(jsonPath("$.model").value(modifiedCivic.getModel()))
                .andExpect(jsonPath("$.price").value(modifiedCivic.getPrice()))
                .andExpect(jsonPath("$.used").value(modifiedCivic.getUsed()));
        verify(vehicleService, times(1)).update(any(Vehicle.class), eq(modifiedCivic.getId()));
    }

    @Test
    void givenInvalidId_shouldReturnError() throws Exception {
        var modifiedCivic = vehicles.get(1);
        modifiedCivic.setPrice(1000d);
        when(vehicleService.update(any(Vehicle.class), eq(4L))).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/vehicle/4")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(modifiedCivic)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
        verify(vehicleService, times(1)).update(any(Vehicle.class), eq(4L));

    }
}