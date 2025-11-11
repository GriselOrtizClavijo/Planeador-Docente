// src/pages/DailyPlanPage.jsx
import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import SelectSubjects from "../../components/planningComponents/SelectSubjects";
import DailyPlanForm from "../../components/planningComponents/DailyPlanForm";

export default function DailyPlanPage() {
  const { fecha } = useParams();
  const [materias, setMaterias] = useState([]);
  const [datosPorArea, setDatosPorArea] = useState({});

  useEffect(() => {
    const cargarDatos = async () => {
      const nuevosDatos = {};
      for (const area of materias) {
        const [dbas, competencias, learning, ejes, criterios, actividades, recursos] =
          await Promise.all([
            fetch(`http://localhost:8080/api/dbas?areaId=${area.idArea}`).then((r) => r.json()),
            fetch(`http://localhost:8080/api/competencies?areaId=${area.idArea}`).then((r) => r.json()),
            fetch(`http://localhost:8080/api/learning?areaId=${area.idArea}`).then((r) => r.json()),
            fetch(`http://localhost:8080/api/thematic-axes?areaId=${area.idArea}`).then((r) => r.json()),
            fetch(`http://localhost:8080/api/evaluation-criteria?areaId=${area.idArea}`).then((r) => r.json()),
            fetch(`http://localhost:8080/api/self-improvement-activities?areaId=${area.idArea}`).then((r) => r.json()),
            fetch(`http://localhost:8080/api/resources?areaId=${area.idArea}`).then((r) => r.json()),
          ]);

        nuevosDatos[area.idArea] = { dbas, competencias, learning, ejes, criterios, actividades, recursos };
      }
      setDatosPorArea(nuevosDatos);
    };

    if (materias.length > 0) cargarDatos();
  }, [materias]);

  return (
    <div style={{ padding: "2rem" }}>
      <h2>
        Planeación del día: <span style={{ color: "#007bff" }}>{fecha}</span>
      </h2>

      <SelectSubjects onSelect={setMaterias} />

      <div style={{ marginTop: "2rem", display: "grid", gap: "1.5rem" }}>
        {materias.map((materia) => (
          <DailyPlanForm
            key={materia.idArea}
            area={materia}
            fecha={fecha}
            datos={datosPorArea[materia.idArea] || {}}
          />
        ))}
      </div>
    </div>
  );
}
