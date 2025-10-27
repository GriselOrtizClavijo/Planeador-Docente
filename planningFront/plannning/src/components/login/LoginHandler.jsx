import React, { useState } from 'react';
import LoginFormUI from './LoginFormUI'; // Importamos el componente de presentación

function LoginHandler({ onLoginSuccess }) { 
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        setMessage('Iniciando sesión...');

        const loginPayload = { username, password };
        const API_URL = 'http://localhost:8080/api/auth/login'; 

        try {
            const response = await fetch(API_URL, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(loginPayload), 
            });

            const data = await response.json(); 
            
            if (response.ok) {
                setMessage(`¡Conexión Exitosa! Mensaje del servidor: ${data.message}`);
                onLoginSuccess(data.username); 
            } else {
                setMessage(`Error del Servidor: ${data}`); 
            }
        } catch (error) {
            setMessage(`Error de conexión: Asegúrate de que Spring Boot esté corriendo en el puerto 8080.`);
            console.error('Error de red:', error);
        }
    };
    
    // Lógica para determinar la clase CSS (también es lógica, no presentación)
    let messageClass = 'login-message';
    if (message.includes('Conexión Exitosa')) {
        messageClass += ' message-success';
    } else if (message.includes('Error')) {
        messageClass += ' message-error';
    }

    return (
        <LoginFormUI
            username={username}
            password={password}
            setUsername={setUsername}
            setPassword={setPassword}
            handleSubmit={handleSubmit}
            message={message}
            messageClass={messageClass} // Pasamos la clase como prop
            
        />
        
    );
}

export default LoginHandler;