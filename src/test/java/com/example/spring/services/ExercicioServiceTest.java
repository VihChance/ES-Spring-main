package com.example.spring.services;

import com.example.spring.domain.Exercicio;
import com.example.spring.domain.UnidadeCurricular;
import com.example.spring.repository.ExercicioRepository;
import com.example.spring.repository.UnidadeCurricularRepository;
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
class ExercicioServiceTest {

    @Mock
    private ExercicioRepository exercicioRepository;

    @Mock
    private UnidadeCurricularRepository unidadeCurricularRepository;

    @InjectMocks
    private ExercicioService exercicioService;

    @Nested
    class createExercise {

        @Test
        @DisplayName("should create an exercise successfully")
        void shouldCreateExerciseSuccessfully() {

            //Exercicio exercicio = new Exercicio();
            //exercicio.setTitulo("Exercise 1");

            Long unidadeCurricularId = 1L;
            String titulo = "Exercise 1";

            UnidadeCurricular unidadeCurricular = new UnidadeCurricular();
            unidadeCurricular.setId(unidadeCurricularId);

            when(unidadeCurricularRepository.findById(unidadeCurricularId)).thenReturn(
                    Optional.of(unidadeCurricular)
            );

            when(exercicioRepository.save(any(Exercicio.class))).thenAnswer(
                    invocation -> {
                        Exercicio exercise = invocation.getArgument(0);
                        exercise.setId(1L);
                        return exercise;
                    }
            );

            Exercicio exerciseResult = exercicioService.criarExercicio(unidadeCurricularId, titulo);

            assertNotNull(exerciseResult);
            assertEquals(unidadeCurricularId, exerciseResult.getUnidadeCurricular().getId());
            assertEquals(titulo, exerciseResult.getTitulo());
        }

    }

}