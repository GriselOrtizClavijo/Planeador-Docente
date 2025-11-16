// src/components/planningComponents/SelectSubjects.jsx
import React, { useEffect, useState } from "react";

export default function SelectSubjects({ onSelect, onCancel }) {
  const [areas, setAreas] = useState([]);
  const [selected, setSelected] = useState([]); // 👈 ahora es una lista de seleccionadas

  // Cargar áreas
  useEffect(() => {
    fetch("http://localhost:8080/api/areas")
      .then((res) => res.json())
      .then(setAreas)
      .catch((e) => console.error("Error cargando áreas:", e));
  }, []);

  // Alternar selección
  const toggleSelect = (area) => {
    const exists = selected.find((a) => a.idArea === area.idArea);

    if (exists) {
      // Si ya estaba, la quitamos
      setSelected(selected.filter((a) => a.idArea !== area.idArea));
    } else {
      // Si no estaba, la agregamos
      setSelected([...selected, area]);
    }
  };

  // Enviar selección
  const confirmar = () => {
    if (selected.length === 0) {
      alert("Selecciona al menos una materia.");
      return;
    }

    onSelect(selected); // 👈 ahora enviamos un ARRAY completo
  };

  return (
    <div style={{ marginTop: "1rem" }}>
      <h3>Selecciona las materias para este día:</h3>

      <p style={{ color: "#555", fontSize: ".9rem" }}>
        Puedes seleccionar varias materias al mismo tiempo.
      </p>

      <div
        style={{
          display: "flex",
          flexWrap: "wrap",
          gap: ".6rem",
          marginTop: "1rem"
        }}
      >
        {areas.map((area) => {
          const isSelected = selected.some((a) => a.idArea === area.idArea);

          return (
            <button
              key={area.idArea}
              onClick={() => toggleSelect(area)}
              className={isSelected ? "button-primary" : "button-ghost"}
              style={{
                minWidth: "120px",
                borderRadius: "8px"
              }}
            >
              {area.name}
            </button>
          );
        })}
      </div>

      {/* BOTONES */}
      <div style={{ marginTop: "2rem", display: "flex", gap: "1rem" }}>
        <button className="button-primary" onClick={confirmar}>
          Confirmar materias
        </button>

        <button className="button-ghost" onClick={onCancel}>
          Cancelar
        </button>
      </div>
    </div>
  );
}
