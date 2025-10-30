package TeachingPlanner.DailyPlanner.controllers.LoginController;

import TeachingPlanner.DailyPlanner.dto.LoginDto.LoginRequest;
import TeachingPlanner.DailyPlanner.dto.LoginDto.RegisterRequest;
import TeachingPlanner.DailyPlanner.entity.login.UsersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import TeachingPlanner.DailyPlanner.service.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    // Inyectamos el Servicio de Usuarios, donde reside la lógica de Hashing.
    private final UsersService usersService;

    @Autowired
    public AuthController(UsersService usersService) {
        this.usersService = usersService;
    }

    // ENDPOINT PARA REGISTRAR UN USUARIO
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        // Mapeamos el DTO de registro a la Entidad que el servicio espera.
        UsersEntity newUser = new UsersEntity();
        newUser.setName(registerRequest.getName());
        newUser.setLastname(registerRequest.getLastname());
        newUser.setUsername(registerRequest.getUsername());
        // La contraseña se hashea DENTRO del UsersService.
        newUser.setPassword(registerRequest.getPassword());
        newUser.setEmail(registerRequest.getEmail());

        // Delegamos la lógica al servicio
        UsersEntity registeredUser = usersService.registerUser(newUser);

        if (registeredUser == null) {
            // Error: Usuario ya existe (la lógica está en el servicio)
            return ResponseEntity.badRequest().body("{\"message\": \"El nombre de usuario ya está en uso.\"}");
        }

        // Registro exitoso: devolvemos 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(
                "{\"message\": \"Usuario registrado con éxito\", \"username\": \"" + registeredUser.getUsername() + "\"}"
        );
    }

    // ENDPOINT PARA INICIO DE SESIÓN
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            // 1. Llamar al servicio para autenticar.
            // El servicio se encarga de: buscar por username, hashear la password entrante y comparar.
            UsersEntity authenticatedUser = usersService.authenticateUser(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            );

            // 2. Si el servicio devuelve un usuario, es exitoso. Limpiamos la password.
            authenticatedUser.setPassword(null);
            return ResponseEntity.ok(authenticatedUser); // Devolvemos el objeto usuario (sin password)

        } catch (Exception e) {
            // 3. El servicio lanza una excepción si las credenciales son inválidas.
            // Devolvemos 401 Unauthorized
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Credenciales inválidas.\"}");
        }
    }
}
