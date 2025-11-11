// src/components/planningComponents/DailyPlanForm.jsx
import React from "react";

export default function DailyPlanForm({ area, fecha, datos }) {
  const { dbas = [], competencias = [], learning = [], ejes = [], criterios = [], actividades = [], recursos = [] } = datos;

  return (
    <form className="card" style={{ padding: "1.4rem" }}>
      <h3>{area.name}</h3>
      <p style={{ fontSize: ".9rem", color: "#555" }}>Fecha: {fecha}</p>

      <div style={{ display: "grid", gap: ".9rem", marginTop: "1rem" }}>
        <div>
          <label>DBA</label>
          <select>
            <option value="">Selecciona un DBA</option>
            {dbas.map((d) => (
              <option key={d.idDba}>{d.description}</option>
            ))}
          </select>
        </div>

        <div>
          <label>Competencias</label>
          <select>
            <option value="">Selecciona una competencia</option>
            {competencias.map((c) => (
              <option key={c.idCompetencies}>{c.name}</option>
            ))}
          </select>
        </div>

        <div>
          <label>Aprendizajes</label>
          <select>
            <option value="">Selecciona un aprendizaje</option>
            {learning.map((l) => (
              <option key={l.idLearning}>{l.name}</option>
            ))}
          </select>
        </div>

        <div>
          <label>Ejes Temáticos</label>
          <select>
            <option value="">Selecciona un eje</option>
            {ejes.map((e) => (
              <option key={e.idThematicAxes}>{e.name}</option>
            ))}
          </select>
        </div>

        <div>
          <label>Actividades</label>
          <select>
            <option value="">Selecciona una actividad</option>
            {actividades.map((a) => (
              <option key={a.idActivity}>{a.name}</option>
            ))}
          </select>
        </div>

        <div>
          <label>Recursos</label>
          <select>
            <option value="">Selecciona un recurso</option>
            {recursos.map((r) => (
              <option key={r.idResource}>{r.name}</option>
            ))}
          </select>
        </div>

        <div>
          <label>Criterios de Evaluación</label>
          <select>
            <option value="">Selecciona un criterio</option>
            {criterios.map((cr) => (
              <option key={cr.idEvaluationCriteria}>{cr.name}</option>
            ))}
          </select>
        </div>

        <button type="submit" className="button-primary" style={{ marginTop: "1rem" }}>
          Guardar planeación
        </button>
      </div>
    </form>
  );
}
