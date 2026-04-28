package grupoC.mascotas.factory;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import grupoC.mascotas.dto.ReporteRequestDTO;
import grupoC.mascotas.model.Estado;
import grupoC.mascotas.model.Reporte;

@Component
public class ReporteFactory {

    private final Map<Estado, ReporteFactoryStrategy> estrategias = new EnumMap<>(Estado.class);

    public ReporteFactory(List<ReporteFactoryStrategy> factories) {
        for (ReporteFactoryStrategy factory : factories) {
            estrategias.put(factory.getEstadoSoportado(), factory);
        }
    }

    public Reporte instanciarReporte(ReporteRequestDTO dto, String usuarioUuid) {
        ReporteFactoryStrategy estrategia = estrategias.get(dto.estado());
        
        if (estrategia == null) {
            throw new IllegalArgumentException("No hay una fábrica configurada para el estado: " + dto.estado());
        }
        
        return estrategia.armarReporte(dto, usuarioUuid);
    }

}
