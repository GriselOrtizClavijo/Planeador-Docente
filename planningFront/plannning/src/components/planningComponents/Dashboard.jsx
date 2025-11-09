// src/components/planningComponents/Dashboard.jsx
import React, { useMemo, useState } from "react";
import { Calendar, momentLocalizer } from "react-big-calendar";
import moment from "moment";
import "react-big-calendar/lib/css/react-big-calendar.css";
import "../../styles/Dashboard.css";
import TabsPanel from "./TabsPanel";
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
    <section style={{ marginTop: "1.5rem" }}>

  <TabsPanel />
</section>
    </div>
  );
}