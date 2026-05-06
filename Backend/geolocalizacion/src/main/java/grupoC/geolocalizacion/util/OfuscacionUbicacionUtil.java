package grupoC.geolocalizacion.util;

import java.util.Random;

public class OfuscacionUbicacionUtil {

    private static final int RADIO_METROS = 500;
    private static final double GRADOS_POR_METRO = 1.0 / 111320.0; // Aproximadamente 1 grado de latitud equivale a 111.32 km

    public static double[] ofuscar(double latReal, double lonReal) {
        Random random = new Random();

        //Generar distancia aleatoria dentro del radio (max 500 metros)
        double radioGrados = RADIO_METROS * GRADOS_POR_METRO;
        double w = radioGrados * Math.sqrt(random.nextDouble());
        double t = 2 * Math.PI * random.nextDouble();

        //Calcular el desplazamiento en x e y
        double desplazamientoLat = w * Math.sin(t);
        double desplazamientoLon = w * Math.cos(t) / Math.cos(Math.toRadians(latReal));

        //Calcular la nueva ubicación ofuscada
        return new double[]{
            latReal + desplazamientoLat,
            lonReal + desplazamientoLon
        };
    }

}
