import React from 'react';
// Asumo que esta ruta de importación ya está corregida
import LoginHandler from '../../components/login/LoginHandler'; 

// 🎯 CORRECCIÓN CLAVE: Se añade 'onToggleView' a la desestructuración de props.
function LoginPage({ onLoginSuccess, onToggleView }) {
    return (
        <div className="login-page" style={{ maxWidth: '400px', margin: '20px auto' }}>
            <h1 style={{textAlign: 'center'}}>Iniciar Sesión</h1>
            
            {/* Aquí se monta toda la lógica del login */}
            <LoginHandler onLoginSuccess={onLoginSuccess} />
            
            <p style={{textAlign: 'center', marginTop: '20px'}}>
                ¿No tienes cuenta? 
                <button 
                    // Ahora esta función está correctamente definida
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
                    Regístrate aquí
                </button>
            </p>
        </div>
    );
}

export default LoginPage;