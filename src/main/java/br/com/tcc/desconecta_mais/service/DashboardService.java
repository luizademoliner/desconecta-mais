package br.com.tcc.desconecta_mais.service;

import br.com.tcc.desconecta_mais.database.entity.RegistroTempoTelaEntity;
import br.com.tcc.desconecta_mais.database.entity.UsuarioEntity;
import br.com.tcc.desconecta_mais.database.repository.IRegistroTempoTelaRepository;
import br.com.tcc.desconecta_mais.dto.DashboardResponseDto;
import br.com.tcc.desconecta_mais.dto.FocoDiaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

        private final IRegistroTempoTelaRepository registroTempoTelaRepository;

        public DashboardResponseDto montar(UsuarioEntity usuario) {

                LocalDate hoje = LocalDate.now();
                LocalDate inicioSemana = hoje.minusDays(6);

                List<RegistroTempoTelaEntity> registrosSemana = registroTempoTelaRepository
                                .findByUsuarioAndDataBetweenOrderByDataAsc(
                                                usuario,
                                                inicioSemana,
                                                hoje);

                Map<LocalDate, Long> tempoPorData = registrosSemana.stream()
                                .collect(Collectors.toMap(
                                                RegistroTempoTelaEntity::getData,
                                                RegistroTempoTelaEntity::getTempoTotalSegundos));

                long tempoHoje = tempoPorData.getOrDefault(hoje, 0L);

                List<FocoDiaDto> tempoPorDiaSemana = new ArrayList<>();

                long somaSemana = 0;
                long maiorTempoSegundos = 0;

                for (int i = 0; i < 7; i++) {

                        LocalDate dia = inicioSemana.plusDays(i);

                        long segundos = tempoPorData.getOrDefault(dia, 0L);

                        somaSemana += segundos;

                        if (segundos > maiorTempoSegundos) {
                                maiorTempoSegundos = segundos;
                        }

                        String abreviado = dia.getDayOfWeek()
                                        .getDisplayName(
                                                        TextStyle.SHORT,
                                                        new Locale("pt", "BR"));

                        tempoPorDiaSemana.add(
                                        new FocoDiaDto(
                                                        capitalizar(abreviado),
                                                        segundos));
                }

                long maiorTempoHoras = (maiorTempoSegundos + 3599) / 3600;

                long maximoEixoY;

                if (maiorTempoHoras == 0) {
                        maximoEixoY = 4;
                } else {
                        maximoEixoY = ((maiorTempoHoras + 3) / 4) * 4;
                }

                long intervaloEixoY = maximoEixoY / 4;

                List<Long> escalaEixoY = new ArrayList<>();

                for (int i = 4; i >= 0; i--) {
                        escalaEixoY.add(intervaloEixoY * i);
                }

                long diasDeSequencia = ChronoUnit.DAYS.between(
                                usuario.getDataInicioSequencia().toLocalDate(),
                                hoje) + 1;

                DashboardResponseDto dto = new DashboardResponseDto();

                dto.setTempoHojeSegundos(tempoHoje);
                dto.setPontosGerais(usuario.getPontosGerais());
                dto.setDiasDeSequencia(diasDeSequencia);
                dto.setMediaDiariaSegundosSemana(somaSemana / 7);
                dto.setTempoPorDiaSemana(tempoPorDiaSemana);

                dto.setMaximoEixoY(maximoEixoY);
                dto.setEscalaEixoY(escalaEixoY);

                return dto;
        }

        private String capitalizar(String texto) {

                if (texto == null || texto.isEmpty()) {
                        return texto;
                }

                return texto.substring(0, 1).toUpperCase()
                                + texto.substring(1);
        }
}