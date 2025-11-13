// src/components/planningComponents/DailyPlanForm.jsx
import React, { useState } from "react";

export default function DailyPlanForm({ area, fecha, datos }) {
  const {
    dbas = [],
    competencias = [],
    learning = [],
    ejes = [],
    criterios = [],
    actividades = [],
    recursos = []
  } = datos;

  const [form, setForm] = useState({
    idDba: "",
    idCompetencies: "",
    idLearning: "",
    idThematicAxes: "",
    idEvaluationCriteria: "",
    idSiA: "",
    idResources: "",
    observations: "",
  });

  // Estado para imágenes seleccionadas
const [imagenes, setImagenes] = React.useState([]);


  /*const onChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };*/

  const onChange = (e) => {
  const value = e.target.value === "" ? null : e.target.value;
  setForm({ ...form, [e.target.name]: value });
};

  // 🔹 GUARDAR
  const onSubmit = async (e) => {
    e.preventDefault(); // ⛔ evita que refresque la página

    console.log("📤 Enviando datos al backend:", form);

    const payload = {
      date: fecha,
      areaId: area.idArea,
      state: "INCOMPLETE", // por ahora
      ...form,
    };

    try {
      const res = await fetch("http://localhost:8080/api/daily-plan", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      });

      if (!res.ok) throw new Error("Error al guardar planeación");

      alert("✔️ Planeación guardada correctamente");
    } catch (err) {
      console.error("❌ Error:", err);
      alert("Error al guardar: " + err.message);
    }
  };

  return (
    <form className="card" style={{ padding: "1.4rem" }} onSubmit={onSubmit}>
      <h3>{area.name}</h3>
      <p style={{ fontSize: ".9rem", color: "#555" }}>Fecha: {fecha}</p>

      <div style={{ display: "grid", gap: ".9rem", marginTop: "1rem" }}>
        
        {/* DBA */}
        <div>
          <label>DBA</label>
          <select name="idDba" value={form.idDba} onChange={onChange}>
            <option value="">Selecciona un DBA</option>
            {dbas.map((d) => (
              <option key={d.idDba} value={d.idDba}>{d.description}</option>
            ))}
          </select>
        </div>

        {/* Competencias */}
        <div>
          <label>Competencias</label>
          <select
            name="idCompetencies"
            value={form.idCompetencies}
            onChange={onChange}
          >
            <option value="">Selecciona una competencia</option>
            {competencias.map((c) => (
              <option key={c.idCompetencies} value={c.idCompetencies}>
                {c.name}
              </option>
            ))}
          </select>
        </div>

        {/* Aprendizajes */}
        <div>
          <label>Aprendizajes</label>
          <select
            name="idLearning"
            value={form.idLearning}
            onChange={onChange}
          >
            <option value="">Selecciona un aprendizaje</option>
            {learning.map((l) => (
              <option key={l.idLearning} value={l.idLearning}>
                {l.name}
              </option>
            ))}
          </select>
        </div>

        {/* Ejes */}
        <div>
          <label>Ejes Temáticos</label>
          <select
            name="idThematicAxes"
            value={form.idThematicAxes}
            onChange={onChange}
          >
            <option value="">Selecciona un eje</option>
            {ejes.map((e) => (
              <option key={e.idThematicAxes} value={e.idThematicAxes}>
                {e.name}
              </option>
            ))}
          </select>
        </div>

        {/* Actividades */}
        <div>
          <label>Actividades</label>
          <select
            name="idSiA"
            value={form.idSiA}
            onChange={onChange}
          >
            <option value="">Selecciona una actividad</option>
            
            {actividades.map((a) => (
              
              <option key={a.idSiA} value={a.idSiA}>
                {a.name}
                console.log("ACT:", a),
              </option>
              
            ))} 
          </select>
        </div>

        {/* Recursos */}
        <div>
          <label>Recursos</label>
          <select
            name="idResources"
            value={form.idResources}
            onChange={onChange}
          >
            <option value="">Selecciona un recurso</option>
            {recursos.map((r) => (
              <option key={r.idResources} value={r.idResources}>
                {r.name}
              </option>
            ))}
          </select>
        </div>

        {/* Criterios */}
        <div>
          <label>Criterios de Evaluación</label>
          <select
            name="idEvaluationCriteria"
            value={form.idEvaluationCriteria}
            onChange={onChange}
          >
            <option value="">Selecciona un criterio</option>
            {criterios.map((cr) => (
              <option key={cr.idEvaluationCriteria} value={cr.idEvaluationCriteria}>
                {cr.description}
              </option>
            ))}
          </select>
        </div>

        {/* Observaciones */}
        <div>
          <label>Observaciones</label>
          <textarea
            name="observations"
            value={form.observations}
            onChange={onChange}
            rows={4}
            style={{ width: "100%" }}
          />
        </div>
        {/* Subir imágenes */}
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


        <button type="submit" className="button-primary" style={{ marginTop: "1rem" }}>
          Guardar planeación
        </button>
      </div>
    </form>,
    console.log("📤 Enviando datos al backend:", form)
  );
}
