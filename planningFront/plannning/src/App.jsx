import React, { useState } from 'react';

// ✅ MANTENEMOS MINÚSCULA para que Vite lo lea
import NavBar from './components/navBar';
import LoginPage from './pages/login/LoginPages'; 
import RegisterPage from './pages/login/RegisterPage';


// Componente simple que simula el Dashboard principal
// NOTA: EL NAV BAR YA NO ESTÁ AQUÍ DENTRO
const MainDashboard = ({ username }) => (
    <div style={{ 
        textAlign: 'center', 
        marginTop: '50px',
        padding: '20px',
        backgroundColor: '#FFFFFF',
        borderRadius: '8px',
        boxShadow: '0 4px 12px rgba(0, 0, 0, 0.05)'
    }}>
        <h2>¡Bienvenido al Panel Principal, {username}!</h2>
        <p>Conexión Back-end y Front-end exitosa. ¡Es hora de planificar!</p>
        {/* Cambiamos window.location.reload() por una función más limpia para salir */}
        <button 
            onClick={() => window.location.reload()} 
            style={{ 
                padding: '10px 20px', 
                backgroundColor: '#dc3545', 
                color: 'white', 
                border: 'none', 
                borderRadius: '4px', 
                cursor: 'pointer', 
                marginTop: '15px' 
            }}
        >
            Cerrar Sesión (Simulado)
        </button>
    </div>
);


function App() {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [currentUser, setCurrentUser] = useState(null);
    
    // Nuevo estado para controlar si mostramos LOGIN o REGISTER
    const [currentView, setCurrentView] = useState('login'); 

    const handleLoginSuccess = (username) => {
        setIsLoggedIn(true);
        setCurrentUser(username);
    };
    
    // Función para cambiar la vista
    const toggleView = () => {
        setCurrentView(currentView === 'login' ? 'register' : 'login');
    };

    // Función que decide qué componente mostrar
    const renderContent = () => {
        if (isLoggedIn) {
            return <MainDashboard username={currentUser} />;
        }
        
        // Si no está logueado, muestra la vista actual (Login o Registro)
        if (currentView === 'login') {
            return (
                <LoginPage 
                    onLoginSuccess={handleLoginSuccess}
                    onToggleView={toggleView} // Le pasamos la función para que pueda cambiar a Registro
                />
            );
        } else {
            return (
                <RegisterPage 
                    onRegistrationSuccess={() => setCurrentView('login')} // Al registrar, volvemos a Login
                    onToggleView={toggleView} // Le pasamos la función para que pueda cambiar a Login
                />
            );
        }
    }

    return (
        // El 'container' es la clase global para centrar el contenido.
        <div className="container"> 
            
            {/* 🎯 NAVBAR SIEMPRE VISIBLE */}
            <NavBar /> 
            
            {/* Aquí se renderiza la página actual (Login/Register/Dashboard) */}
            {renderContent()}
        </div>
    );
}

export default App;
