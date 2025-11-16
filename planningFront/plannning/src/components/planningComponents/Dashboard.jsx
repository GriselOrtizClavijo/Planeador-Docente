import React, { useState, useEffect } from "react";
import { Calendar, momentLocalizer } from "react-big-calendar";
import moment from "moment";
import { useNavigate } from "react-router-dom";  // 👈 nuevo import
import "react-big-calendar/lib/css/react-big-calendar.css";
import "../../styles/Dashboard.css";
import TabsPanel from "./TabsPanel";

const localizer = momentLocalizer(moment);

export default function Dashboard() {
  const navigate = useNavigate();
  const [currentDate, setCurrentDate] = useState(new Date());
  const [events, setEvents] = useState([]);


  const onSelectSlot = (slot) => {
    const day = moment(slot.start).format("YYYY-MM-DD");
    navigate(`/plan-diario/${day}`); // 👈 te llevará a la nueva página
  };

  const loadEvents = async () => {
  try {
    const res = await fetch("http://localhost:8080/api/daily-plan/events");
    const data = await res.json();

    const formatted = data.map(ev => ({
      title: ev.areaName,
      start: new Date(ev.date),
      end: new Date(ev.date),
      state: ev.state
    }));

    setEvents(formatted);
  } catch (e) {
    console.error("Error cargando eventos:", e);
  }
};

useEffect(() => {
  loadEvents();
}, []);

  return (
    <div style={{ display: "grid", gap: "1.2rem", padding: "1rem" }}>
      <section className="card" style={{ padding: ".8rem" }}>
        <h2 style={{ margin: ".4rem 0 1rem", fontSize: "1.3rem" }}>
          Calendario
        </h2>
        <Calendar
          localizer={localizer}
          date={currentDate}
          onNavigate={(date) => setCurrentDate(date)}
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
          <section style={{ marginTop: "1.5rem" }}>
          <TabsPanel />
      </section>
    </div>
  );
}
