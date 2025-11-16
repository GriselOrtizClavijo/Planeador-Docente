// src/components/planningComponents/DailyPlanForm.jsx
import React, { useState, useEffect } from "react";

export default function DailyPlanForm({ area, fecha, plan, onSaved, onCancel }) {
  const [form, setForm] = useState({
    idPlan: plan.idPlan ?? null,
    areaId: area.idArea,
    date: fecha,
    state: plan.state ?? "INCOMPLETE",
    idDba: plan.idDba ?? "",
    idCompetencies: plan.idCompetencies ?? "",
    idLearning: plan.idLearning ?? "",
    idThematicAxes: plan.idThematicAxes ?? "",
    idEvaluationCriteria: plan.idEvaluationCriteria ?? "",
    idSiA: plan.idSiA ?? "",
    idResources: plan.idResources ?? "",
    observations: plan.observations ?? ""
  });

  const [datos, setDatos] = useState({
    dbas: [],
    competencias: [],
    learning: [],
    ejes: [],
    criterios: [],
    actividades: [],
    recursos: []
  });

  const translateState = {
  INCOMPLETE: "INCOMPLETE",
  COMPLETE: "COMPLETE",
  INCOMPLETO: "INCOMPLETE",
  COMPLETO: "COMPLETE"
  };


    // Estado para imágenes seleccionadas
  const [imagenes, setImagenes] = React.useState([]);

  // 🔹 Cargar catálogos de la materia
  useEffect(() => {
    const load = async () => {
      const id = area.idArea;

      const [
        dbas,
        competencias,
        learning,
        ejes,
        criterios,
        actividades,
        recursos
      ] = await Promise.all([
        fetch(`http://localhost:8080/api/dbas?areaId=${id}`).then((r) => r.json()),
        fetch(`http://localhost:8080/api/competencies?areaId=${id}`).then((r) => r.json()),
        fetch(`http://localhost:8080/api/learning?areaId=${id}`).then((r) => r.json()),
        fetch(`http://localhost:8080/api/thematic-axes?areaId=${id}`).then((r) => r.json()),
        fetch(`http://localhost:8080/api/evaluation-criteria?areaId=${id}`).then((r) => r.json()),
        fetch(`http://localhost:8080/api/self-improvement-activities?areaId=${id}`).then((r) => r.json()),
        fetch(`http://localhost:8080/api/resources?areaId=${id}`).then((r) => r.json())
      ]);

      setDatos({
        dbas,
        competencias,
        learning,
        ejes,
        criterios,
        actividades,
        recursos
      });
    };

    load();
  }, [area]);

  const onChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  // 🔹 GUARDAR FORMULARIO
  const onSubmit = async (e) => {
    e.preventDefault();

    const method = form.idPlan ? "PUT" : "POST";
    const url = form.idPlan
      ? `http://localhost:8080/api/daily-plan/${form.idPlan}`
      : `http://localhost:8080/api/daily-plan`;

      const payload = {
        ...form,
        state: translateState[form.state] // 🔥 traducción obligatoria
      };

      
      const res = await fetch(url, {
      method,
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload)
    });

    if (!res.ok) {
      alert("Error al guardar planeación");
      return;
    }

    onSaved();
  };

    // ⛔ Protección: evita que se ejecute si no hay área
  if (!area || !area.idArea) {
    return <p>Cargando datos del área...</p>;
  }

  const actualizarEstado = async (nuevoEstado) => {
  const url = `http://localhost:8080/api/daily-plan/${form.idPlan}`;

  const res = await fetch(url, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ ...form, state: nuevoEstado })
  });

  if (!res.ok) {
    alert("No se pudo actualizar el estado");
    return;
  }

  onSaved();
};


  return (
    <form className="card" style={{ padding: "1.4rem", marginTop: "2rem" }} onSubmit={onSubmit}>
      <button
        type="button"
        onClick={onCancel}
        className="button-ghost"
        style={{ marginBottom: "1rem" }}
      >
        ⬅ Volver
      </button>

      <h3>{area.name}</h3>
      <p style={{ fontSize: ".9rem", color: "#555" }}>Fecha: {fecha}</p>

      {/* DBA */}
      <div>
        <label>DBA</label>
        <select name="idDba" value={form.idDba} onChange={onChange}>
          <option value="">Seleccione un DBA</option>
          {datos.dbas.map((d) => (
            <option key={d.idDba} value={d.idDba}>{d.description}</option>
          ))}
        </select>
      </div>

      {/* COMPETENCIAS */}
      <div>
        <label>Competencias</label>
        <select name="idCompetencies" value={form.idCompetencies} onChange={onChange}>
          <option value="">Seleccione una competencia</option>
          {datos.competencias.map((c) => (
            <option key={c.idCompetencies} value={c.idCompetencies}>{c.name}</option>
          ))}
        </select>
      </div>

      {/* APRENDIZAJES */}
      <div>
        <label>Aprendizajes</label>
        <select name="idLearning" value={form.idLearning} onChange={onChange}>
          <option value="">Seleccione un aprendizaje</option>
          {datos.learning.map((l) => (
            <option key={l.idLearning} value={l.idLearning}>{l.name}</option>
          ))}
        </select>
      </div>

      {/* EJES TEMÁTICOS */}
      <div>
        <label>Ejes Temáticos</label>
        <select name="idThematicAxes" value={form.idThematicAxes} onChange={onChange}>
          <option value="">Seleccione un eje temático</option>
          {datos.ejes.map((e) => (
            <option key={e.idThematicAxes} value={e.idThematicAxes}>{e.name}</option>
          ))}
        </select>
      </div>

      {/* ACTIVIDADES DE PROFUNDIZACIÓN */}
      <div>
        <label>Actividades</label>
        <select name="idSiA" value={form.idSiA} onChange={onChange}>
          <option value="">Seleccione una actividad</option>
          {datos.actividades.map((a) => (
            <option key={a.idSiA} value={a.idSiA}>{a.name}</option>
          ))}
        </select>
      </div>

      {/* RECURSOS */}
      <div>
        <label>Recursos</label>
        <select name="idResources" value={form.idResources} onChange={onChange}>
          <option value="">Seleccione un recurso</option>
          {datos.recursos.map((r) => (
            <option key={r.idResources} value={r.idResources}>{r.name}</option>
          ))}
        </select>
      </div>

      {/* CRITERIOS DE EVALUACIÓN */}
      <div>
        <label>Criterios de Evaluación</label>
        <select name="idEvaluationCriteria" value={form.idEvaluationCriteria} onChange={onChange}>
          <option value="">Seleccione un criterio</option>
          {datos.criterios.map((cr) => (
            <option key={cr.idEvaluationCriteria} value={cr.idEvaluationCriteria}>{cr.description}</option>
          ))}
        </select>
      </div>

      {/* OBSERVACIONES */}
      <div>
        <label>Observaciones</label>
        <textarea
          name="observations"
          value={form.observations}
          onChange={onChange}
          rows="3"
          style={{ width: "100%" }}
        ></textarea>
      </div>

      <div>
  <label>Imágenes (máximo 3)</label>
  <input
    type="file"
    accept="image/png, image/jpeg, image/jpg"
    multiple
    onChange={(e) => {
      const files = Array.from(e.target.files);

      // validar límite
      if (files.length + imagenes.length > 3) {
        alert("Máximo puedes subir 3 imágenes.");
        return;
      }

      // validar tipo
      const permitidos = ["image/png", "image/jpeg", "image/jpg"];
      const validos = files.filter((f) => permitidos.includes(f.type));

      if (validos.length !== files.length) {
        alert("Solo se permiten imágenes PNG o JPG.");
        return;
      }

      setImagenes((prev) => [...prev, ...validos]);
    }}
  />
</div>

        {/* Vista previa */}
        {imagenes.length > 0 && (
          <div style={{ display: "flex", gap: "1rem", marginTop: "1rem", flexWrap: "wrap" }}>
            {imagenes.map((img, index) => (
              <div key={index} style={{ textAlign: "center" }}>
                <img
                  src={URL.createObjectURL(img)}
                  alt="preview"
                  style={{ width: 120, height: 120, objectFit: "cover", borderRadius: 8, border: "1px solid #ccc" }}
                />
                <button
                  type="button"
                  onClick={() =>
                    setImagenes((prev) => prev.filter((_, i) => i !== index))
                  }
                  style={{
                    marginTop: ".5rem",
                    background: "#ff4d4d",
                    color: "white",
                    border: "none",
                    padding: ".3rem .6rem",
                    borderRadius: 6,
                    cursor: "pointer",
                  }}
                >
                  Eliminar
                </button>
              </div>
            ))}
          </div>,
          
          console.log("📸 Imágenes seleccionadas:", imagenes)
        )}


      {/* BOTONES */}
      <div style={{ display: "flex", gap: ".8rem", marginTop: "1rem" }}>
        <button type="button" className="button-ghost" onClick={onCancel}>
          Cancelar
        </button>
        <button type="submit" className="button-primary">
          Guardar planeación
        </button>
      </div>
      <button
      type="button"
      className="button-primary"
      onClick={() => actualizarEstado("COMPLETE")}
    >
      ✔ Marcar como completado
    </button>

    <button
      type="button"
      className="button-ghost"
      onClick={() => actualizarEstado("INCOMPLETE")}
    >
      ⏳ Marcar como pendiente
    </button>

    </form>
  );
}
