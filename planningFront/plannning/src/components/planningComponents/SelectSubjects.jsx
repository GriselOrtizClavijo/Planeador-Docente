// src/components/planningComponents/SelectSubjects.jsx
import React, { useEffect, useState } from "react";

export default function SelectSubjects({ onSelect }) {
  const [areas, setAreas] = useState([]);
  const [selected, setSelected] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/api/areas")
      .then((res) => res.json())
      .then(setAreas)
      .catch((e) => console.error(e));
  }, []);

  const toggleSelection = (area) => {
    const exists = selected.find((a) => a.idArea === area.idArea);
    const updated = exists
      ? selected.filter((a) => a.idArea !== area.idArea)
      : [...selected, area];
    setSelected(updated);
    onSelect(updated);
  };

  return (
    <div>
      <h4>Selecciona las áreas para este día:</h4>
      <div style={{ display: "flex", flexWrap: "wrap", gap: ".6rem", marginTop: ".5rem" }}>
        {areas.map((a) => (
          <button
            key={a.idArea}
            onClick={() => toggleSelection(a)}
            className={`button-${selected.find((s) => s.idArea === a.idArea) ? "primary" : "ghost"}`}
          >
            {a.name}
          </button>
        ))}
      </div>
    </div>
  );
}
