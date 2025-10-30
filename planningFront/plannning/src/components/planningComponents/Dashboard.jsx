// src/components/planningComponents/Dashboard.jsx
import React, { useMemo, useState } from "react";
import { Calendar, momentLocalizer } from "react-big-calendar";
import moment from "moment";
import "react-big-calendar/lib/css/react-big-calendar.css";
import "../../styles/Dashboard.css";
import CrudModule from "./CrudModule";

const localizer = momentLocalizer(moment);

export default function Dashboard() {
  const [activeTab, setActiveTab] = useState("areas");

 
  // 📅 Estado para controlar el mes actual del calendario
  const [currentDate, setCurrentDate] = useState(new Date());

  // ejemplo de eventos vacíos por ahora
  const events = useMemo(() => [], []);

  const onSelectSlot = (slot) => {
    const day = moment(slot.start).format("YYYY-MM-DD");
    alert(`Abrir formulario de planeación para: ${day}`);
    // Aquí luego abres tu modal/form con los campos del plan diario
  };

  return (
    <div style={{ display: "grid", gap: "1.2rem", padding: "1rem" }}>
      {/* Calendario grande */}
      <section className="card" style={{ padding: ".8rem" }}>
        <h2 style={{ margin: ".4rem 0 1rem", fontSize: "1.3rem" }}>
          Calendario
        </h2>
        <Calendar
          localizer={localizer}
          date={currentDate}
          onNavigate={(date) => setCurrentDate(date)} // ✅ permite cambiar mes
          events={events}
          startAccessor="start"
          endAccessor="end"
          style={{ height: 600 }}
          selectable
          onSelectSlot={onSelectSlot}
          views={["month", "week", "day"]}
          popup
        />
      </section>

      {/* Módulos CRUD */}
     <section className="card" style={{ padding: "1rem" }}>
        <h2 style={{ fontSize: "1.3rem", marginBottom: ".8rem" }}>Catálogos</h2>

        {/* Botones de pestañas */}
        <div className="tabs">
          <button
            className={activeTab === "areas" ? "active" : ""}
            onClick={() => setActiveTab("areas")}
          >
            Áreas
          </button>
          <button
            className={activeTab === "dbas" ? "active" : ""}
            onClick={() => setActiveTab("dbas")}
          >
            DBA
          </button>
          <button
            className={activeTab === "competencias" ? "active" : ""}
            onClick={() => setActiveTab("competencias")}
          >
            Competencias
          </button>
          <button
            className={activeTab === "ejes" ? "active" : ""}
            onClick={() => setActiveTab("ejes")}
          >
            Ejes temáticos
          </button>
          <button
            className={activeTab === "criterios" ? "active" : ""}
            onClick={() => setActiveTab("criterios")}
          >
            Criterios de Evaluación
          </button>
          <button
            className={activeTab === "actividades" ? "active" : ""}
            onClick={() => setActiveTab("actividades")}
          >
            Act. de superación y profundización
          </button>
                    <button
            className={activeTab === "aprendizajes" ? "active" : ""}
            onClick={() => setActiveTab("aprendizajes")}
          >
            Aprendizajes
          </button>
        </div>

        {/* Contenido según pestaña */}
        <div className="tab-content">
          {activeTab === "areas" && (
            <CrudModule
              title="Áreas"
              endpoint="/api/areas"
              fields={[{ name: "name", label: "Nombre del Área" }]}
            />
          )}
          {activeTab === "dbas" && (
            <CrudModule
              title="DBA"
              endpoint="/api/dbas"
              fields={[{ name: "name", label: "Descripción DBA" }]}
            />
          )}
          {activeTab === "competencias" && (
            <CrudModule
              title="Competencias"
              endpoint="/api/competencies"
              fields={[{ name: "name", label: "Nombre/Descripción" }]}
            />
          )}
          {activeTab === "ejes" && (
            <CrudModule
              title="Ejes temáticos"
              endpoint="/api/thematic-axes"
              fields={[{ name: "name", label: "Nombre del eje" }]}
            />
          )}
          {activeTab === "criterios" && (
            <CrudModule
              title="Criterios de evaluación"
              endpoint="/api/evaluation-criteria"
              fields={[{ name: "name", label: "Criterio" }]}
            />
          )}
          {activeTab === "actividades" && (
            <CrudModule
              title="Act. de superación y profundización"
              endpoint="/api/self-improvement-activities"
              fields={[{ name: "name", label: "Actividad" }]}
            />
          )}
           {activeTab === "aprendizajes" && (
            <CrudModule
              title="Aprendizajes"
              endpoint="/api/learning"
              fields={[{ name: "name", label: "Actividad" }]}
            />
          )}
        </div>
      </section>
    </div>
  );
}