package swf.army.mil.p4backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import swf.army.mil.p4backend.Entity.Vehicle;
import swf.army.mil.p4backend.repository.VehicleRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private Vehicle vehicle1;
    @Mock
    private Vehicle vehicle2;

    @InjectMocks
    private VehicleService vehicleService;


    @BeforeEach
    void setUp() {
        vehicle1 = new Vehicle("Ferrari", "488GTB", 2015, 250000d, false);
        vehicle1.setId(1L);
        vehicle2 = new Vehicle("Ford", "Fiesta", 2002, 3000d, true);
        vehicle2.setId(2L);
    }

    @Nested
    class getAll {


        @Test
        void shouldGetAllVehicles() {
            List<Vehicle> vehicles = List.of(vehicle1, vehicle2);
            when(vehicleRepository.findAll()).thenReturn(vehicles);

            var results = vehicleService.getAll();

            assertThat(results).isNotNull();
            assertThat(results).hasSize(2);
            assertThat(results.get(0).getModel()).isEqualTo(vehicle1.getModel());
            verify(vehicleRepository).findAll();
        }
    }

    @Nested
    class getById {

        @Test
        void givenValidId_shouldReturnVehicleById() {
            when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle1));

            var result = vehicleService.getById(1L);

            assertThat(result).isNotNull();
            assertThat(result.getModel()).isEqualTo(vehicle1.getModel());
        }

        @Test
        void givenInvalidId_shouldReturnNull() {
            when(vehicleRepository.findById(4L)).thenReturn(Optional.empty());

            var result = vehicleService.getById(4L);

            assertThat(result).isNull();
        }
    }

    @Nested
    class deleteById {

        @Test
        void givenValidId_shouldRemoveVehicleById() {
            doNothing().when(vehicleRepository).deleteById(vehicle1.getId());

            var result = vehicleService.deleteById(vehicle1.getId());

            verify(vehicleRepository).deleteById(vehicle1.getId());
            assertThat(result).isTrue();
        }

        @Test
        void givenInvalidId_shouldReturnFalse() {
            doNothing().when(vehicleRepository).deleteById(4L);

            var result = vehicleService.deleteById(4L);

            verify(vehicleRepository).deleteById(4L);
            assertThat(result).isFalse();
        }
    }

    @Nested
    class create {

        @Test
        void givenValidVehicle_shouldCreateVehicle() {
            when(vehicleRepository.save(vehicle1)).thenReturn(vehicle1);

            var result = vehicleService.create(vehicle1);

            assertThat(result).isNotNull();
            assertThat(result.getModel()).isEqualTo(vehicle1.getModel());
            verify(vehicleRepository).save(vehicle1);
        }
    }

    @Nested
    class Update {

        @Test
        void givenValidIdAndVehicle_shouldUpdateVehicleById() {
            var updatedVehicle = vehicle2;
            updatedVehicle.setId(1L);
            when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle1));
            when(vehicleRepository.save(any(Vehicle.class))).thenReturn(updatedVehicle);

            var result = vehicleService.update(updatedVehicle, 1L);

            assertThat(result).isNotNull();
            assertThat(result.getMake()).isEqualTo(updatedVehicle.getMake());
            assertThat(result.getModel()).isEqualTo(updatedVehicle.getModel());
            assertThat(result.getYear()).isEqualTo(updatedVehicle.getYear());
            assertThat(result.getPrice()).isEqualTo(updatedVehicle.getPrice());
            assertThat(result.getUsed()).isEqualTo(updatedVehicle.getUsed());
        }

        @Test
        void givenInvalidId_shouldThrowIllegalArgumentException() {
            when(vehicleRepository.findById(4L)).thenReturn(Optional.empty());

            assertThatIllegalArgumentException().isThrownBy(() -> vehicleService.update(vehicle2, 4L));
        }
    }
}