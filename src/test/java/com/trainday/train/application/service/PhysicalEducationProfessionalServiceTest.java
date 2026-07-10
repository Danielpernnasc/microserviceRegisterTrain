package com.trainday.train.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.trainday.train.api.DTO.request.PhysicalEducationProfessionalRequest;
import com.trainday.train.api.DTO.response.PhysicalEducationProfessionalResponse;
import com.trainday.train.domain.models.LoginPhyEdProf;
import com.trainday.train.domain.models.PhysicalEducationProfessional;
import com.trainday.train.domain.models.enums.Role;
import com.trainday.train.domain.repository.LoginRepository;
import com.trainday.train.domain.repository.RepositoryPhyEdProf;

@ExtendWith(MockitoExtension.class)
class PhysicalEducationProfessionalServiceTest {

    @Mock
    private RepositoryPhyEdProf repositoryPhyEdProf;

    @Mock
    private LoginRepository loginRepository;

    @InjectMocks
    private PhysicalEducationProfessionalService service;

    private PhysicalEducationProfessional professional;
    private LoginPhyEdProf loginUser;
    private PhysicalEducationProfessionalRequest request;

    @BeforeEach
    void setUp() {
        loginUser = new LoginPhyEdProf("user-id-1", "CREF-001", "prof@email.com", "encoded-pass", "Prof Name",
                Role.PERSONAL_TRAINER);

        professional = new PhysicalEducationProfessional();
        professional.setId("user-id-1");
        professional.setName("Prof Name");
        professional.setEmail("prof@email.com");
        professional.setCref("CREF-001");
        professional.setBornDate(LocalDate.of(1990, 1, 15));
        professional.setCpf("123.456.789-00");
        professional.setPhone("11999999999");
        professional.setAddress("Rua A, 123");
        professional.setRole(Role.PERSONAL_TRAINER);

        request = new PhysicalEducationProfessionalRequest(
                "Prof Name",
                LocalDate.of(1990, 1, 15),
                "123.456.789-00",
                "professional@host.com",
                "11999999999",
                "Rua A, 123",
                Role.PERSONAL_TRAINER);
    }

    // ─── isCrefRegistered ───────────────────────────────────────────────────────

    @Test
    void isCrefRegistered_shouldReturnTrue_whenCrefExists() {
        when(repositoryPhyEdProf.findByCref("CREF-001")).thenReturn(Optional.of(professional));

        boolean result = service.isCrefRegistered("CREF-001");

        assertThat(result).isTrue();
    }

    @Test
    void isCrefRegistered_shouldReturnFalse_whenCrefNotFound() {
        when(repositoryPhyEdProf.findByCref("CREF-999")).thenReturn(Optional.empty());

        boolean result = service.isCrefRegistered("CREF-999");

        assertThat(result).isFalse();
    }

    // ─── create ─────────────────────────────────────────────────────────────────

    @Test
    void create_shouldSaveProfessional_whenEmailAndCrefAreValid() {
        when(loginRepository.findByEmail("prof@email.com")).thenReturn(Optional.of(loginUser));
        when(repositoryPhyEdProf.findByCref("prof@email.com")).thenReturn(Optional.empty());
        when(repositoryPhyEdProf.save(any(PhysicalEducationProfessional.class))).thenReturn(professional);

        PhysicalEducationProfessional result = service.create("prof@email.com", request);

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("prof@email.com");
        assertThat(result.getCref()).isEqualTo("CREF-001");
        verify(repositoryPhyEdProf).save(any(PhysicalEducationProfessional.class));
    }

    @Test
    void create_shouldThrowException_whenUserNotFound() {
        when(loginRepository.findByEmail("unknown@email.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.create("unknown@email.com", request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User not found");
    }

    @Test
    void create_shouldThrowException_whenCrefAlreadyRegistered() {
        when(loginRepository.findByEmail("prof@email.com")).thenReturn(Optional.of(loginUser));
        when(repositoryPhyEdProf.findByCref("prof@email.com")).thenReturn(Optional.of(professional));

        assertThatThrownBy(() -> service.create("prof@email.com", request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("CREF already registered");
    }

    // ─── getByCref ──────────────────────────────────────────────────────────────

    @Test
    void getByCref_shouldReturnResponse_whenCrefExists() {
        when(repositoryPhyEdProf.findByCref("CREF-001")).thenReturn(Optional.of(professional));

        PhysicalEducationProfessionalResponse response = service.getByCref("CREF-001");

        assertThat(response).isNotNull();
        assertThat(response.cref()).isEqualTo("CREF-001");
        assertThat(response.email()).isEqualTo("prof@email.com");
    }

    @Test
    void getByCref_shouldThrowException_whenCrefNotFound() {
        when(repositoryPhyEdProf.findByCref("CREF-999")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getByCref("CREF-999"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Physical Education Professional not found");
    }

    // ─── UpdatePEP ──────────────────────────────────────────────────────────────

    @Test
    void updatePEP_shouldUpdateFields_whenCrefExists() {
        when(repositoryPhyEdProf.findByCref("CREF-001")).thenReturn(Optional.of(professional));
        when(repositoryPhyEdProf.findAll()).thenReturn(List.of(professional));
        when(repositoryPhyEdProf.save(any(PhysicalEducationProfessional.class))).thenReturn(professional);

        PhysicalEducationProfessionalRequest updateRequest = new PhysicalEducationProfessionalRequest(
                "Updated Name", LocalDate.of(1992, 5, 20), "987.654.321-00", "professional@host.com", "(11) 88888-8888", "Rua B, 456",
                Role.PERSONAL_TRAINER);

        PhysicalEducationProfessional result = service.UpdatePEP("CREF-001", updateRequest);

        assertThat(result).isNotNull();
        verify(repositoryPhyEdProf).save(any(PhysicalEducationProfessional.class));
    }

    @Test
    void updatePEP_shouldThrowException_whenCrefNotFound() {
        when(repositoryPhyEdProf.findAll()).thenReturn(List.of());
        when(repositoryPhyEdProf.findByCref("CREF-999")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.UpdatePEP("CREF-999", request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Physical Education Professional not found");
    }

    // ─── patchPEP ───────────────────────────────────────────────────────────────

    @Test
    void patchPEP_shouldUpdateOnlyProvidedFields_whenCrefExists() {
        when(repositoryPhyEdProf.findByCref("CREF-001")).thenReturn(Optional.of(professional));
        when(repositoryPhyEdProf.save(any(PhysicalEducationProfessional.class))).thenReturn(professional);

        PhysicalEducationProfessionalRequest patchRequest = new PhysicalEducationProfessionalRequest(
                "New Name", null, null, null, null,null, null);

        PhysicalEducationProfessional result = service.patchPEP("CREF-001", patchRequest);

        assertThat(result).isNotNull();
        verify(repositoryPhyEdProf).save(any(PhysicalEducationProfessional.class));
    }

    @Test
    void patchPEP_shouldThrowException_whenCrefNotFound() {
        when(repositoryPhyEdProf.findByCref("CREF-999")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.patchPEP("CREF-999", request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Physical Education Professional not found");
    }

    // ─── deletePEP ──────────────────────────────────────────────────────────────

    @Test
    void deletePEP_shouldDeleteProfessionalAndLogin_whenCrefExists() {
        when(repositoryPhyEdProf.findByCref("CREF-001")).thenReturn(Optional.of(professional));

        service.deletePEP("CREF-001");

        verify(repositoryPhyEdProf).deleteById("CREF-001");
        verify(loginRepository).deleteById("user-id-1");
    }

    @Test
    void deletePEP_shouldThrowException_whenCrefNotFound() {
        when(repositoryPhyEdProf.findByCref("CREF-999")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deletePEP("CREF-999"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Physical Education Professional not found");
    }
}
