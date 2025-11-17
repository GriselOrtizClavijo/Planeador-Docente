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
    resources: plan.resources?.map(r => r.idResources) ?? [],
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


          // ⛔ Protección: evita que se ejecute si no hay área
        if (!area || !area.idArea) {
          return <p>Cargando datos del área...</p>;
        }

        const normalize = (value) => {
          return value === "" ? null : value;
        };


      const actualizarEstado = async (nuevoEstado) => {
      const normalize = (value) => value === "" ? null : value;

      const payload = {
        ...form,
        state: nuevoEstado,
        resources: form.resources ?? [],
        idDba: normalize(form.idDba),
        idCompetencies: normalize(form.idCompetencies),
        idLearning: normalize(form.idLearning),
        idThematicAxes: normalize(form.idThematicAxes),
        idEvaluationCriteria: normalize(form.idEvaluationCriteria),
        idSiA: normalize(form.idSiA)
      };




        const url = form.idPlan
          ? `http://localhost:8080/api/daily-plan/${form.idPlan}`
          : `http://localhost:8080/api/daily-plan`;

        const method = form.idPlan ? "PUT" : "POST";

        const res = await fetch(url, {
          method,
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(payload)
        });

        if (!res.ok) {
          alert("No se pudo guardar");
          return;
        }

        onSaved();
        window.dispatchEvent(new Event("updateCalendar"));
      };

          // 🔹 Subir imágenes al backend
    const uploadImages = async () => {
      if (!form.idPlan) return;

      for (const img of imagenes) {
        const formData = new FormData();
        formData.append("file", img);

        try {
          await fetch(`http://localhost:8080/api/daily-plan/images/${form.idPlan}`, {
            method: "POST",
            body: formData
          });
        } catch (e) {
          console.error("Error subiendo imagen:", e);
        }
      }
    };



  return (
    <form className="card" style={{ padding: "1.4rem", marginTop: "2rem" }}>

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
        <label>Actividades de profundización</label>
        <select name="idSiA" value={form.idSiA} onChange={onChange}>
          <option value="">Seleccione una actividad</option>
          {datos.actividades.map((a) => (
            <option key={a.idSiA} value={a.idSiA}>{a.name}</option>
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
        <label>Descripción de la actividad</label>
        <textarea
          name="observations"
          value={form.observations}
          onChange={onChange}
          rows="3"
          style={{ width: "100%" }}
        ></textarea>
      </div>

      
      {/* RECURSOS */}
     <div>
  <label style={{ fontWeight: "600" }}>Recursos (puede seleccionar varios)</label>

  <div
    style={{
      border: "1px solid #ccc",
      borderRadius: "6px",
      padding: "10px",
      maxHeight: "150px",
      overflowY: "auto",
      background: "white",
      marginTop: "4px"
    }}
  >
    {datos.recursos.map((r) => {
      const seleccionado = form.resources.includes(r.idResources);

      return (
        <label
          key={r.idResources}
          style={{
            display: "flex",
            alignItems: "center",
            gap: "8px",
            padding: "4px 0",
            cursor: "pointer"
          }}
        >
          <input
            type="checkbox"
            checked={seleccionado}
            onChange={() => {
              let nuevos;

              if (seleccionado) {
                // quitar
                nuevos = form.resources.filter(id => id !== r.idResources);
              } else {
                // agregar
                nuevos = [...form.resources, r.idResources];
              }

              setForm({ ...form, resources: nuevos });
            }}
          />

          {r.name}
        </label>
      );
    })}
  </div>
</div>


    {/* IMÁGENES */}
<div style={{ marginTop: "1rem" }}>
  <label style={{ fontWeight: 600 }}>
    Imágenes (máximo 3)
  </label>

  <input
    type="file"
    accept="image/png, image/jpeg, image/jpg"
    multiple
    onChange={(e) => {
      const files = Array.from(e.target.files);

      if (imagenes.length + files.length > 3) {
        alert("Solo puedes subir máximo 3 imágenes.");
        return;
      }

      const permitidos = ["image/png", "image/jpeg", "image/jpg"];
      const validos = files.filter((f) => permitidos.includes(f.type));

      if (validos.length !== files.length) {
        alert("Solo se permiten imágenes PNG o JPG.");
        return;
      }

      setImagenes((prev) => [...prev, ...validos]);
    }}
    style={{ marginTop: ".4rem" }}
  />

  {/* Contador */}
  <p style={{ fontSize: ".8rem", color: "#555", marginTop: ".3rem" }}>
    Total seleccionadas: {imagenes.length} / 3
  </p>

  {/* Lista de nombres */}
  {imagenes.length > 0 && (
    <ul style={{ marginTop: ".5rem", paddingLeft: "1.2rem" }}>
      {imagenes.map((img, index) => (
        <li key={index} style={{ marginBottom: ".3rem" }}>
          {img.name}
        </li>
      ))}
    </ul>
  )}

  {/* Vista previa */}
  {imagenes.length > 0 && (
    <div style={{
      display: "flex",
      gap: "1rem",
      marginTop: "1rem",
      flexWrap: "wrap"
    }}>
      {imagenes.map((img, index) => (
        <div key={index} style={{ textAlign: "center" }}>
          <img
            src={URL.createObjectURL(img)}
            alt="preview"
            style={{
              width: 120,
              height: 120,
              objectFit: "cover",
              borderRadius: 8,
              border: "1px solid #ccc"
            }}
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
    </div>
  )}
</div>
         

          <div style={{ display: "flex", gap: "1rem", marginTop: "1.5rem" }}>
          <button
            type="button"
            className="button-ghost"
            onClick={() => actualizarEstado("INCOMPLETE")}
          >
            ⏳ Guardar como pendiente
          </button>

          <button
            type="button"
            className="button-primary"
            onClick={() => actualizarEstado("COMPLETE")}
          >
            ✔ Guardar como completado
          </button>

          <button
            type="button"
            className="button-ghost"
            onClick={onCancel}
          >
            Cancelar
          </button>
        </div>
      
    </form>
  );
}
