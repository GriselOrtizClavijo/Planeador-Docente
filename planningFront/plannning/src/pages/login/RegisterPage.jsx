import React, { useState } from 'react';

// EL COMPONENTE AHORA RECIBE DOS PROPS DEL PADRE (App.jsx)
function RegisterPage({ onRegistrationSuccess, onToggleView }) {
    const [name, setName] = useState('');
    const [lastname, setLastname] = useState('');
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [email, setEmail] = useState('');
    const [message, setMessage] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        setMessage('Registrando usuario...');

        const payload = { name, lastname, username, password, email };
        const API_URL = 'http://localhost:8080/api/auth/register'; 

        try {
            const response = await fetch(API_URL, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload), 
            });

            const text = await response.text();
            
            if (response.ok) {
                const data = JSON.parse(text);
                setMessage(`✅ Registro Exitoso: ${data.message}. Redirigiendo a Iniciar Sesión...`);
                
                // 1. LLAMADA CLAVE: Avisa a App.jsx que el registro fue exitoso
                //    y cambia la vista automáticamente a Login después de 1 segundo.
                setTimeout(() => {
                    onRegistrationSuccess(); 
                }, 1000);

            } else {
                 // El cuerpo del error viene como JSON
                const errorData = JSON.parse(text); 
                setMessage(`❌ Error al registrar: ${errorData.message}`); 
            }
        } catch (error) {
            setMessage(`🔴 Error de red o CORS. Intenta de nuevo.`);
            console.error(error);
        }
    };

    return (
        <div style={{ padding: '30px', maxWidth: '400px', margin: '30px auto', border: '1px solid grey', borderRadius: '5px' }}>
            <h3>Registrar Nuevo Usuario</h3>
            <form onSubmit={handleSubmit} style={{ display: 'grid', gap: '30px', padding: '30px', borderRadius: '5px' }}>
                <input type="text" value={name} onChange={(e) => setName(e.target.value)} placeholder="Nombre" required />
                <input type="text" value={lastname} onChange={(e) => setLastname(e.target.value)} placeholder="Apellido" required />
                <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} placeholder="Email" required />
                <input type="number" value={username} onChange={(e) => setUsername(e.target.value)} placeholder="Username (Documento de identidad)" required />
                <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="Password" required />
                
                <button type="submit" 
                    style={{ 
                        padding: '10px', 
                        cursor: 'pointer', 
                        backgroundColor: '#007bff' , 
                        color: 'white', 
                        border: 'none',
                        borderRadius: '4px' 
                    }}>Crear Cuenta</button>
            </form>
            
            <p style={{ marginTop: '25px', fontWeight: 'bold' }}>{message}</p>
            
            {/* 2. BOTÓN CLAVE: Botón para volver a la página de Login */}
            <p style={{textAlign: 'center', marginTop: '20px', fontSize: '14px'}}>
                ¿Ya tienes cuenta? 
                <button 
                    onClick={onToggleView} 
                    style={{ 
                        background: 'none', 
                        border: 'none', 
                        color: '#007bff', 
                        cursor: 'pointer', 
                        marginLeft: '5px',
                        textDecoration: 'underline'
                    }}
                >
                    Iniciar Sesión
                </button>
            </p>
        </div>
    );
}

export default RegisterPage;