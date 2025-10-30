package TeachingPlanner.DailyPlanner.service;


import TeachingPlanner.DailyPlanner.entity.login.UsersEntity;
import TeachingPlanner.DailyPlanner.repository.login.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

/**
 * Servicio encargado de la lógica de negocio para la gestión de usuarios,
 * con un mecanismo de hashing temporal y simple para la contraseña.
 * NOTA: ESTE MÉTODO DE HASHING ES SOLO PARA DESBLOQUEAR EL DESARROLLO SIN
 * SPRING SECURITY. NO USAR EN PRODUCCIÓN.
 */
@Service
public class UsersService {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /**
     * Función utilitaria para aplicar un hashing simple (SHA-256).
     * @param input La cadena (contraseña en texto plano) a hashear.
     * @return El hash de la cadena.
     */
    private String hashPassword(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            // Convertir bytes a formato hexadecimal para almacenaje
            StringBuilder hexString = new StringBuilder(2 * encodedhash.length);
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Esto no debería suceder en entornos Java estándar
            throw new RuntimeException("Error en el algoritmo de hashing", e);
        }
    }

    /**
     * Registra un nuevo usuario aplicando hashing a la contraseña.
     * @param user El objeto Users con username y la contraseña en texto plano.
     * @return El usuario guardado o null si el username ya existe.
     */
    public UsersEntity registerUser(UsersEntity user) {
        // 1. Verificar si el usuario ya existe
        if (usersRepository.findByUsername(user.getUsername()).isPresent()) {
            return null; // Usuario duplicado
        }

        // 2. APLICAR HASHING: Cifrar la contraseña ANTES de guardarla
        String hashedPassword = hashPassword(user.getPassword());
        user.setPassword(hashedPassword);

        // 3. Guardar el nuevo usuario con la contraseña hasheada
        return usersRepository.save(user);
    }

    /**
     * Intenta iniciar sesión comparando la contraseña ingresada (hasheada)
     * con la almacenada (hasheada).
     * @param username El nombre de usuario.
     * @param rawPassword La contraseña ingresada por el usuario (en texto plano).
     * @return El objeto Users si el login es exitoso.
     * @throws Exception Si el usuario no existe o la contraseña es incorrecta.
     */
    public UsersEntity authenticateUser(String username, String rawPassword) throws Exception {
        // 1. Buscar al usuario
        Optional<UsersEntity> userOptional = usersRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            UsersEntity user = userOptional.get();

            // 2. HASHEAR la contraseña ingresada para COMPARAR con la guardada
            String hashedPasswordAttempt = hashPassword(rawPassword);

            // 3. Comparación
            if (hashedPasswordAttempt.equals(user.getPassword())) {
                return user; // Contraseña correcta, autenticación exitosa
            }
        }

        // Lanzar excepción para indicar credenciales inválidas.
        throw new Exception("Credenciales inválidas.");
    }
}
