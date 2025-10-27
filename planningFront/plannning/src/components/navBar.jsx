import React from 'react';

// AJUSTA ESTA RUTA si el logo.png no está en src/assets
import logo from '../assets/logo_planeador.png'; 

const NavBar = () => {
    return (
        <nav style={styles.navBar}>
            <div style={styles.logoContainer}>
                {/* 1. Logo */}
                <img src={logo} alt="EduPlanner Logo" style={styles.logoImage} />
                <span style={styles.logoText}>EduPlanner</span>
            </div>
            {/* 2. Eslogan */}
            <span style={styles.slogan}>El planificador digital para docentes.</span>
        </nav>
    );
};

const styles = {
    navBar: {
        // Fondo blanco limpio para la barra
        backgroundColor: '#FFFFFF', 
        padding: '15px 20px',
        // Sombra suave, ideal para muchas horas de uso
        boxShadow: '0 2px 8px rgba(44, 62, 80, 0.1)', 
        display: 'flex',
        justifyContent: 'space-between', 
        alignItems: 'center',
        marginBottom: '40px', // Aumentamos el margen inferior para separar del contenido
        // Línea de acento con el Amarillo Mostaza vibrante
        borderBottom: `4px solid #F39C12`, 
        borderRadius: '0 0 8px 8px', // Bordes redondeados inferiores
    },
    logoContainer: {
        display: 'flex',
        alignItems: 'center',
    },
    logoImage: {
        height: '80px', // Ajusta el tamaño del logo si es necesario
        marginRight: '12px',
    },
    logoText: {
        // Color Azul Oscuro/Negro
        color: '#2C3E50', 
        fontSize: '26px',
        fontWeight: '900', // Súper negrita
        letterSpacing: '0.5px',
    },
    slogan: {
        // Color Azul Medio
        color: '#3498DB', 
        fontSize: '15px',
        fontWeight: '500',
    },
};

export default NavBar;
