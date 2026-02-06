package com.example.spring.services;

import com.example.spring.domain.Exercicio;
import com.example.spring.domain.Fase;
import com.example.spring.repository.ExercicioRepository;
import com.example.spring.repository.FaseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FaseServiceTest {

    @Mock
    private FaseRepository faseRepository;

    @Mock
    private ExercicioRepository exercicioRepository;

    @InjectMocks
    private FaseService faseService;

    @Nested
    class createFase{

        @Test
        @DisplayName("should create a fase successfully")
        void shouldCreateFase(){

            Long exerciseId = 1L;
            String titulo = "Fase 1";
            int ordem = 1;

            Exercicio exercicio = new Exercicio();
            exercicio.setId(exerciseId);

            when(exercicioRepository.findById(exerciseId)).thenReturn(
                    Optional.of(exercicio)
            );

            when(faseRepository.save(any(Fase.class))).thenAnswer(
                    invocation -> {
                        Fase fase = invocation.getArgument(0);
                        fase.setTitulo(titulo);
                        return fase;
                    }
            );

            var faseResult = faseService.criar(exerciseId, titulo, ordem);

            assertNotNull(faseResult);
            assertEquals(exerciseId, faseResult.getExercicio().getId());
            assertEquals(titulo, faseResult.getTitulo());
            assertEquals(ordem, faseResult.getOrdem());
            assertEquals(exercicio, faseResult.getExercicio()); //Testar o proprio objecto

        }


    }

}