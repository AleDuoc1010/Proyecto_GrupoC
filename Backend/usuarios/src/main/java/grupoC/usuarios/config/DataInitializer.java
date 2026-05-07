package grupoC.usuarios.config;

import grupoC.usuarios.model.Rol;
import grupoC.usuarios.model.Usuario;
import grupoC.usuarios.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DataInitializer implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.default-admin.email:admin@local}")
    private String adminEmail;

    @Value("${app.default-admin.password:admin}")
    private String adminPassword;

    public DataInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            if (!usuarioRepository.existsByEmail(adminEmail)) {
                Usuario admin = new Usuario();
                admin.setUuid(UUID.randomUUID().toString());
                admin.setNombre("Administrador");
                admin.setEmail(adminEmail);
                admin.setPhone(null);
                admin.setRol(Rol.ADMINISTRADOR);
                admin.setPasswordHash(passwordEncoder.encode(adminPassword));
                usuarioRepository.save(admin);
                logger.info("Usuario administrador creado: {}", adminEmail);
            } else {
                logger.info("Usuario administrador ya existe: {}", adminEmail);
            }
        } catch (Exception e) {
            logger.error("Error al crear usuario administrador: {}", e.getMessage(), e);
        }
    }
}
