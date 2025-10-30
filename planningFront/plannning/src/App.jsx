import React, { useState } from 'react';

// ✅ AJUSTE DE RUTA: Aplicando PascalCase para resolver Case Sensitivity
import NavBar from './components/navBar'; 
import LoginPage from './pages/login/LoginPages'; 
import RegisterPage from './pages/login/RegisterPage';
import Dashboard from './components/planningComponents/Dashboard'



function App() {
    // Estado para controlar si el usuario está logueado
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    // Estado para guardar el nombre o ID del usuario
    const [currentUser, setCurrentUser] = useState(null);
    
    // Estado para controlar si mostramos LOGIN o REGISTER
    const [currentView, setCurrentView] = useState('login'); 

    // Se llama cuando el Login es exitoso
    const handleLoginSuccess = (username) => {
        setIsLoggedIn(true); // <--- CAMBIO CLAVE: Activa la vista del Dashboard
        setCurrentUser(username);
    };
    
    // Función para cambiar la vista entre Login y Register
    const toggleView = () => {
        setCurrentView(currentView === 'login' ? 'register' : 'login');
    };

    // Función que decide qué componente de contenido mostrar (Dashboard o Auth)
    const renderContent = () => {
        if (isLoggedIn) {
            // LÓGICA CLAVE: Si isLoggedIn es TRUE, renderiza MainDashboard
            return (
                 <Dashboard/>
            );
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
        // Estructura principal de la aplicación
        <div className="container"> 
            
            {/* 🎯 NAVBAR SIEMPRE VISIBLE */}
            <NavBar /> 
            {renderContent()}
        </div>
    );
}

export default App;
