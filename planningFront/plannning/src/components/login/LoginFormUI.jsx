import React from 'react';
import '../../styles/Login.css';

// Este componente solo recibe props y renderiza el HTML.
// No tiene estado interno, solo muestra lo que le dicen.
function LoginFormUI({ username, password, setUsername, setPassword, handleSubmit, message, messageClass }) {
    
    return (
        <div className="login-container"> 
            <form onSubmit={handleSubmit} className="login-form">
                <h2>Iniciar Sesión</h2>
                
                <div className="form-group">
                    <label htmlFor="username">Usuario:</label>
                    <input
                        id="username"
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)} // El handler viene de arriba
                        required
                    />
                </div>
                
                <div className="form-group">
                    <label htmlFor="password">Contraseña:</label>
                    <input
                        id="password"
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)} // El handler viene de arriba
                        required
                    />
                </div>
                
                <button type="submit" className="login-button">
                    Entrar
                </button>
            </form>
            
            {/* Usamos la clase dinámica que también viene como prop */}
            {message && <p className={messageClass}>{message}</p>}
        </div>
    );
}

export default LoginFormUI;