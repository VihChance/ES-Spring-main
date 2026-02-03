package com.example.spring.services;

import com.example.spring.domain.Exercicio;
import com.example.spring.domain.Participacao;
import com.example.spring.repository.ExercicioRepository;
import com.example.spring.repository.ParticipacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DashboardService {

    @Autowired
    private ExercicioRepository exercicioRepository;

    @Autowired
    private ParticipacaoRepository participacaoRepository;

    public Map<String, Object> progressoDoExercicio(Long exercicioId) {
        Optional<Exercicio> optionalExercicio = exercicioRepository.findById(exercicioId);
        if (optionalExercicio.isEmpty()) return null;

        Exercicio exercicio = optionalExercicio.get();
        List<Participacao> participacoes = participacaoRepository.findByExercicioId(exercicioId);

        int totalFases = (exercicio.getFases() == null) ? 0 : exercicio.getFases().size();

        List<Map<String, Object>> progressoAlunos = new ArrayList<>();

        for (Participacao p : participacoes) {

            int fasesConcluidas = (p.getFasesConcluidas() == null)
                    ? 0
                    : p.getFasesConcluidas().size();

            int percentagem = (totalFases == 0)
                    ? 0
                    : (int) Math.round(fasesConcluidas * 100.0 / totalFases);

            int faseAtual = (totalFases == 0)
                    ? 0
                    : Math.min(fasesConcluidas + 1, totalFases);

            Map<String, Object> alunoMap = new HashMap<>();
            alunoMap.put("aluno", p.getAluno().getNome());
            alunoMap.put("terminado", p.isTerminado());
            alunoMap.put("nota", p.getNota());
            alunoMap.put("chamado", p.isChamado());
            alunoMap.put("totalFases", totalFases);
            alunoMap.put("fasesConcluidas", fasesConcluidas);
            alunoMap.put("percentagem", percentagem);
            alunoMap.put("faseAtual", faseAtual);

            progressoAlunos.add(alunoMap);
        }

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("exercicio", exercicio.getTitulo());
        resultado.put("progresso", progressoAlunos);

        return resultado;
    }

}
