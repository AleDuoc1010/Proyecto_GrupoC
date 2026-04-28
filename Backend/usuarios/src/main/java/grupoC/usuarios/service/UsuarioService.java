package grupoC.usuarios.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import grupoC.usuarios.dto.LoginDto;
import grupoC.usuarios.dto.RegistroDto;
import grupoC.usuarios.exception.EmailYaExisteException;
import grupoC.usuarios.exception.UsuarioNotFoundException;
import grupoC.usuarios.model.Rol;
import grupoC.usuarios.model.Usuario;
import grupoC.usuarios.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Page<Usuario> findAll(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Usuario findByUuid(String uuid) {
        return usuarioRepository.findByUuid(uuid)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario con UUID " + uuid + " no encontrado"));
    }

    public boolean existePorEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Usuario registrarUsuario(RegistroDto registroDto) {
        if (usuarioRepository.existsByEmail(registroDto.email())) {
            throw new EmailYaExisteException("El email ya está registrado" + registroDto.email());
        }

        String passwordHash = passwordEncoder.encode(registroDto.password());

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(registroDto.nombre());
        nuevoUsuario.setEmail(registroDto.email());
        nuevoUsuario.setPhone(registroDto.phone());
        nuevoUsuario.setPasswordHash(passwordHash);
        nuevoUsuario.setRol(Rol.CIUDADANO);

        return usuarioRepository.save(nuevoUsuario);
    }

    @Transactional(readOnly = true)
    public Usuario loginUsuario(LoginDto loginDto) {
        Usuario usuario = usuarioRepository.findByEmail(loginDto.email())
            .orElseThrow(() -> new BadCredentialsException("Email o contraseña incorrectos"));
        
        if(passwordEncoder.matches(loginDto.password(), usuario.getPasswordHash())){
            return usuario;
        } else {
            throw new BadCredentialsException("Email o contraseña incorrectos");
        }
    }

    public Usuario cambiarRol(String uuid, String nuevoRolStr) {
        Usuario usuario = usuarioRepository.findByUuid(uuid)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario con UUID " + uuid + " no encontrado"));

        try {
            Rol rolEnum = Rol.valueOf(nuevoRolStr.toUpperCase());
            usuario.setRol(rolEnum);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Rol no válido");
        }
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void eliminarUsuario(String uuid) {
        usuarioRepository.deleteByUuid(uuid);
    }
}
