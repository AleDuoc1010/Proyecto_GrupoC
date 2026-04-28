package grupoC.mascotas.factory;

import grupoC.mascotas.dto.ReporteRequestDTO;
import grupoC.mascotas.model.Estado;
import grupoC.mascotas.model.Reporte;

public interface ReporteFactoryStrategy {

    Reporte armarReporte(ReporteRequestDTO dto, String usuarioUuid);
    
    Estado getEstadoSoportado();

}
