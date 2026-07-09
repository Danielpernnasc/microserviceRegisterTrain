package com.trainday.train.api.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.trainday.train.api.DTO.request.PhysicalEducationProfessionalRequest;
import com.trainday.train.api.DTO.response.PhysicalEducationProfessionalResponse;
import com.trainday.train.application.service.PhysicalEducationProfessionalService;
import com.trainday.train.domain.models.PhysicalEducationProfessional;
import com.trainday.train.domain.models.enums.Role;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class PhysicalEducationProfessionalControllerTest {

    @Mock
    PhysicalEducationProfessionalService service;

    @InjectMocks
    PhysicalEducationProfessionalController controller;

    @Test
    void createProfessional(){
        PhysicalEducationProfessionalRequest request = new PhysicalEducationProfessionalRequest(
                "Danilo Nassau",
                LocalDate.of(1990, 1, 15),
                "999.999.999-99",
                "(11) 99999-9999",
                "Rua 1, 180 Bairro Qualquer, São Paulo SP",
                Role.PERSONAL_TRAINER
        );

        PhysicalEducationProfessional pep = new PhysicalEducationProfessional();
        pep.setId("user1");
        pep.setCref("CREF12345");
        pep.setCpf("999.999.999-99");
        pep.setName("Danilo Nassau");
        pep.setEmail("danilo@host.com");
        pep.setAddress("Rua 1, 180 Bairro Qualquer, São Paulo SP");
        pep.setPhone("(11) 99999-9999");
        pep.setBornDate(LocalDate.of(1990, 1, 15));
        pep.setRole(Role.PERSONAL_TRAINER);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("user1");


        when(service.create("user1", request))
                .thenReturn(pep);

        ResponseEntity<PhysicalEducationProfessional> created = controller.save(request, authentication);

        assertNotNull(created);

        assertNotNull(created.getBody());
        assertEquals(request.name(), created.getBody().getName());
        assertEquals(request.cpf(), created.getBody().getCpf());
        assertEquals(request.phone(), created.getBody().getPhone());
        assertEquals(request.address(), created.getBody().getAddress());
        assertEquals(request.bornDate(), created.getBody().getBornDate());
        assertEquals(request.role(), created.getBody().getRole());

        verify(service).create("user1", request);


    }

    @Test
    void findByCrefShouldReturnResponse() {
        String cref = "CREF12345";
        PhysicalEducationProfessionalResponse response = new PhysicalEducationProfessionalResponse(
                "Danilo Nassau",
                "danilo@host.com",
                cref,
                "(11) 99999-9999",
                "999.999.999-99",
                "Rua 1, 180 Bairro Qualquer, São Paulo SP",
                LocalDate.of(1990, 1, 15),
                Role.PERSONAL_TRAINER);

        when(service.getByCref(cref)).thenReturn(response);

        ResponseEntity<PhysicalEducationProfessionalResponse> result = controller.findByCREF(cref);

        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(cref, result.getBody().cref());
        assertEquals("Danilo Nassau", result.getBody().name());
        verify(service).getByCref(cref);
    }



    @Test
    void updateByCrefShouldReturnUpdatedProfessional() {
        String cref = "CREF12345";
        PhysicalEducationProfessionalRequest request = new PhysicalEducationProfessionalRequest(
                "Nome Atualizado",
                LocalDate.of(1991, 2, 20),
                "111.222.333-44",
                "(11) 98888-7777",
                "Rua Atualizada, 200",
                Role.PERSONAL_TRAINER);

        PhysicalEducationProfessional updated = new PhysicalEducationProfessional();
        updated.setId("user1");
        updated.setCref(cref);
        updated.setName("Nome Atualizado");
        updated.setEmail("danilo@host.com");
        updated.setCpf("111.222.333-44");
        updated.setPhone("(11) 98888-7777");
        updated.setAddress("Rua Atualizada, 200");
        updated.setBornDate(LocalDate.of(1991, 2, 20));
        updated.setRole(Role.PERSONAL_TRAINER);

        when(service.UpdatePEP(cref, request)).thenReturn(updated);

        PhysicalEducationProfessional result = controller.updateByCREF(cref, request);

        assertNotNull(result);
        assertEquals("Nome Atualizado", result.getName());
        assertEquals(cref, result.getCref());
        verify(service).UpdatePEP(cref, request);
    }

    @Test
    void patchByCrefShouldReturnPatchedProfessional() {
        String cref = "CREF12345";
        PhysicalEducationProfessionalRequest request = new PhysicalEducationProfessionalRequest(
                "Somente Nome",
                null,
                null,
                null,
                null,
                null);

        PhysicalEducationProfessional patched = new PhysicalEducationProfessional();
        patched.setId("user1");
        patched.setCref(cref);
        patched.setName("Somente Nome");
        patched.setEmail("danilo@host.com");
        patched.setCpf("999.999.999-99");
        patched.setPhone("(11) 99999-9999");
        patched.setAddress("Rua 1, 180 Bairro Qualquer, São Paulo SP");
        patched.setBornDate(LocalDate.of(1990, 1, 15));
        patched.setRole(Role.PERSONAL_TRAINER);

        when(service.UpdatePEP(cref, request)).thenReturn(patched);

        PhysicalEducationProfessional result = controller.patchByCREF(cref, request);

        assertNotNull(result);
        assertEquals("Somente Nome", result.getName());
        verify(service).UpdatePEP(cref, request);
    }

    @Test
    void deleteByCrefShouldCallService() {
        String cref = "CREF12345";

        controller.deleteByCREF(cref);

        verify(service).deletePEP(cref);
    }
}
