package com.example.spring.services;

import com.example.spring.domain.Aluno;
import com.example.spring.repository.AlunoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlunoServiceTest {

    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks
    private AlunoService alunoService;

    //subclass
    @Nested
    class createAluno {

        @Test
        @DisplayName("Should create a new student")
        void shouldCreateNewStudent() {

            //Arrange
            Aluno aluno = new Aluno();
            aluno.setNome("Maria João");

            when(alunoRepository.save(any(Aluno.class))).thenAnswer(
                    invocation -> {
                        Aluno aluno1 = invocation.getArgument(0);
                        aluno1.setId(1L);
                        return aluno1;
                    });

            //Act
            Aluno result = alunoService.novoAluno(aluno);

            //Assert
            assertNotNull(result);
            assertEquals(aluno.getId(), result.getId());
            assertEquals(aluno.getNome(), result.getNome());

        }

    }

    @Nested
    class listarAluno {

        @Test
        @DisplayName("should list all students successfully")
        void shouldListAllStudents() {

            Aluno aluno1 = new Aluno();
            aluno1.setId(1L);
            aluno1.setNome("Maria Joao");

            Aluno aluno2 = new Aluno();
            aluno2.setId(2L);
            aluno2.setNome("Jose Antonio");

            Aluno aluno3 = new Aluno();
            aluno3.setId(3L);
            aluno3.setNome("Antonio Jose");


            //Arrange
            when(alunoRepository.findAll()).thenReturn(
                    List.of(aluno1, aluno2, aluno3)
            );


            //Act
            var allStudents = alunoService.listarTodos();

            //Assert
            assertNotNull(allStudents);
            assertEquals(3, allStudents.size());
            assertTrue(allStudents.contains(aluno1));
            assertTrue(allStudents.contains(aluno2));
            assertTrue(allStudents.contains(aluno3));

        }
    }

    @Nested
    class procurarAlunoById{

        @Test
        @DisplayName("should search for student successfully")
        void shouldSearchForStudentSuccessfully() {

            Aluno aluno = new Aluno();
            aluno.setId(1L);
            long id = 1L;

            //Arrange
            when(alunoRepository.findById(id)).thenReturn(Optional.of(aluno));

            //Act
            var student = alunoService.procurarPorId(id);

            //Assert
            assertNotNull(student);
            assertEquals(id, student.getId());

        }
    }

}