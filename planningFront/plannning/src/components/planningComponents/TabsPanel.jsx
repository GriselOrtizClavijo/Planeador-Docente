// src/components/planningComponents/TabsPanel.jsx
import React, { useState } from "react";
import CrudModule from "./CrudModule";
import "../../styles/TabsPanel.css";

export default function TabsPanel() {
  const [activeTab, setActiveTab] = useState("areas");
  const [tabStates, setTabStates] = useState({}); // 🧠 guarda el estado individual por tab

  const tabs = [
    { id: "areas", label: "Áreas", endpoint: "/api/areas", fields: [{ name: "name", label: "Nombre del Área" }] },
    { id: "dbas", label: "DBA", endpoint: "/api/dbas", fields: [{ name: "description", label: "Descripción del DBA" }] },
    { id: "competencias", label: "Competencias", endpoint: "/api/competencies", fields: [{ name: "name", label: "Descripción de competencias" }] },
    { id: "ejes", label: "Ejes Temáticos", endpoint: "/api/thematic-axes", fields: [{ name: "name", label: "Descripción de Eje Temático" }] },
    { id: "criterios", label: "Criterios de Evaluación", endpoint: "/api/evaluation-criteria", fields: [{ name: "description", label: "Descripción de Criterios de evaluación" }] },
    { id: "aprendizajes", label: "Aprendizajes", endpoint: "/api/learning", fields: [{ name: "name", label: "Descripción de aprendizajes" }] },
    { id: "actividades", label: "Actividades de profundización", endpoint: "/api/self-improvement-activities", fields: [{ name: "name", label: "Descripción de Act. de profundización" }] },
    { id: "recursos", label: "Recursos", endpoint: "/api/resources", fields: [{ name: "name", label: "Nombre del Recurso" }] },
];

  const current = tabs.find((t) => t.id === activeTab);

    const saveTabState = (tabId, newState) => {
      setTabStates((prev) => ({
        ...prev,
        [tabId]: newState,
      }));
    };

    const currentState = tabStates[activeTab] || {};

    return (
      <div className="tabs-container">
        <h2 style={{ fontSize: "2rem", marginBottom: "2rem" }}>Categoría</h2>

        {/* 🗂️ Pestañas tipo folder */}
        <div className="folder-tabs">
          {tabs.map((tab) => (
            <button
              key={tab.id}
              className={`folder-tab ${activeTab === tab.id ? "active" : ""}`}
              onClick={() => setActiveTab(tab.id)}
            >
              {tab.label}
            </button>
          ))}
        </div>

        {/* 📦 Contenido de la pestaña activa */}
        <div className="folder-content">
          <CrudModule
            key={current.id}
            title={current.label}
            endpoint={current.endpoint}
            fields={current.fields}
            savedState={currentState}
            onStateChange={(state) => saveTabState(current.id, state)}
          />
        </div>
      </div>
    );
  }