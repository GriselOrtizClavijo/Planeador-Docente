package TeachingPlanner.DailyPlanner.controllers.LoginController;

import TeachingPlanner.DailyPlanner.dto.LoginDto.LoginRequest;
import TeachingPlanner.DailyPlanner.dto.LoginDto.RegisterRequest;
import TeachingPlanner.DailyPlanner.entity.login.Users;
import TeachingPlanner.DailyPlanner.repository.login.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5174")
public class AuthController {

    @Autowired
    private UsersRepository usersRepository; // Usamos el repositorio correcto

    // ENDPOINT PARA REGISTRAR UN USUARIO
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {

        // 1. Verificar si el usuario ya existe
        if (usersRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("{\"message\": \"El nombre de usuario ya está en uso.\"}");
        }

        // 2. Crear y guardar la Entidad
        Users newUser = new Users();
        // Usamos los setters de Lombok (o manuales)
        newUser.setName(registerRequest.getName());
        newUser.setLastname(registerRequest.getLastname());
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPassword(registerRequest.getPassword()); // Sin encriptación por ahora
        newUser.setEmail(registerRequest.getEmail());

        usersRepository.save(newUser);

        return ResponseEntity.ok("{\"message\": \"Usuario registrado con éxito\", \"username\": \"" + newUser.getUsername() + "\"}");
    }

    // ENDPOINT PARA EL LOGIN SIMULADO
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        // Buscar el usuario en la DB
        Optional<Users> userOptional = usersRepository.findByUsername(loginRequest.getUsername());

        if (userOptional.isEmpty()) {
            // Usuario no existe
            return ResponseEntity.status(401).body("{\"message\": \"Usuario no encontrado (simulación).\"}");
        }

        // Simulación: Si el usuario existe, el login es exitoso
        return ResponseEntity.ok(
                "{\"message\": \"Login simulado exitoso\", \"username\": \"" + loginRequest.getUsername() + "\"}"
        );
    }
}


