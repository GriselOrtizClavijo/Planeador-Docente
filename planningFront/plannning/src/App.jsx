import React, { useState } from "react";
import { Routes, Route, Navigate } from "react-router-dom";

import NavBar from "./components/navBar";
import LoginPage from "./pages/login/LoginPages";
import RegisterPage from "./pages/login/RegisterPage";
import Dashboard from "./components/planningComponents/Dashboard";
import DailyPlanPage from "./pages/planningPages/DailyPlanPage";

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [currentUser, setCurrentUser] = useState(null);
  const [currentView, setCurrentView] = useState("login");

  const handleLoginSuccess = (username) => {
    setIsLoggedIn(true);
    setCurrentUser(username);
  };

  const toggleView = () => {
    setCurrentView(currentView === "login" ? "register" : "login");
  };

  return (
    <div className="container">
      <NavBar />

      <Routes>
        {/* RUTA LOGIN */}
        <Route
          path="/"
          element={
            isLoggedIn ? (
              <Navigate to="/dashboard" replace />
            ) : currentView === "login" ? (
              <LoginPage
                onLoginSuccess={handleLoginSuccess}
                onToggleView={toggleView}
              />
            ) : (
              <RegisterPage
                onRegistrationSuccess={() => setCurrentView("login")}
                onToggleView={toggleView}
              />
            )
          }
        />

        {/* RUTA DASHBOARD */}
        <Route
          path="/dashboard"
          element={isLoggedIn ? <Dashboard /> : <Navigate to="/" replace />}
        />

        {/* RUTA NUEVA: PLAN DIARIO */}
        <Route
          path="/plan-diario/:fecha"
          element={
            isLoggedIn ? <DailyPlanPage /> : <Navigate to="/" replace />
          }
        />
      </Routes>
    </div>
  );
}

export default App;
