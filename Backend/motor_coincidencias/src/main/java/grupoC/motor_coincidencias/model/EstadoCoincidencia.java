package grupoC.motor_coincidencias.model;

public enum EstadoCoincidencia {

    PENDIENTE, //El motor encontró la coincidencia pero el usuario aún no la ha revisado
    REVISADA, //El usuario ha revisado la coincidencia pero aún no ha tomado una decisión
    DESCARTADA, //El usuario ha descartado la coincidencia
    EXITOSA //La coincidencia ha sido exitosa

}
