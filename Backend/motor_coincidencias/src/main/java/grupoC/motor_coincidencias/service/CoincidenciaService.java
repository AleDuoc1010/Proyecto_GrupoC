package grupoC.motor_coincidencias.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import grupoC.motor_coincidencias.model.Coincidencia;
import grupoC.motor_coincidencias.model.EstadoCoincidencia;
import grupoC.motor_coincidencias.repository.CoincidenciaRepository;

@Service
public class CoincidenciaService {

    private final CoincidenciaRepository repository;

    public CoincidenciaService(CoincidenciaRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Coincidencia> obtenerCoincidenciasPorReporte(Long reporteId) {
        // Busca si el ID coincide ya sea como mascota perdida o como encontrada
        List<Coincidencia> coincidencias = repository.findByReportePerdidoId(reporteId);
        if (coincidencias.isEmpty()) {
            coincidencias = repository.findByReporteEncontradoId(reporteId);
        }
        return coincidencias;
    }

    @Transactional
    public void actualizarEstadoCoincidencia(Long id, EstadoCoincidencia nuevoEstado) {
        Coincidencia coincidencia = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coincidencia no encontrada"));
        
        coincidencia.setEstado(nuevoEstado);
        repository.save(coincidencia);
    }

}
