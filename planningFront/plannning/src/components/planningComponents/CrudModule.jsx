// src/components/planningComponents/CrudModule.jsx
import { Plus, List, Pencil, Trash2, X } from "lucide-react";
import React, { useState, useEffect } from "react";

export default function CrudModule({ title, fields = [], endpoint }) {
  const [items, setItems] = useState([]);
  const [form, setForm] = useState({});
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [showForm, setShowForm] = useState(false);
  const [showList, setShowList] = useState(false);
  const [editingId, setEditingId] = useState(null);

  // 🔹 Cargar lista
  const load = async () => {
    try {
      setLoading(true);
      const res = await fetch(`http://localhost:8080${endpoint}`);
      if (!res.ok) throw new Error("No se pudo cargar la lista");
      const data = await res.json();
      setItems(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  // 🔹 Manejar inputs
  const onChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  // 🔹 Crear nuevo registro
  const startCreate = () => {
    setForm({});
    setEditingId(null);
    setShowForm(true);
  };

  // 🔹 Editar existente
  const startEdit = (item) => {
    setForm(item);
    setEditingId(item.id ?? item.idArea ?? item.idDba);
    setShowForm(true);
  };

  // 🔹 Guardar (crear o editar)
  const submit = async (e) => {
    e.preventDefault();
    try {
      const method = editingId ? "PUT" : "POST";
      const url = editingId
          ? `http://localhost:8080${endpoint}/${editingId}`
          : `http://localhost:8080${endpoint}`;


      const res = await fetch(url, {
        method,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(form),
      });
      if (!res.ok) throw new Error("Error al guardar");
      await load();
      setShowForm(false);
    } catch (err) {
      setError(err.message);
    }
  };

  // 🔹 Eliminar
  const remove = async (id) => {
    if (!window.confirm("¿Seguro que deseas eliminar este registro?")) return;
    try {
      const res = await fetch(`http://localhost:8080${endpoint}/${id}`, {
        method: "DELETE",
      });
      if (!res.ok) throw new Error("Error al eliminar");
      await load();
    } catch (err) {
      setError(err.message);
    }
  };

  // 🔹 Mostrar lista si el usuario hace click
  const toggleList = async () => {
    if (!showList) await load();
    setShowList(!showList);
  };

  return (
    <div className="card" style={{ padding: "1rem" }}>
      {/* Header */}
      <div
        style={{
          display: "flex",
          alignItems: "center",
          justifyContent: "space-between",
          marginBottom: ".6rem",
        }}
      >
        <h3 style={{ fontSize: "1.1rem", fontWeight: 700 }}>{title}</h3>
        <div style={{ display: "flex", gap: ".5rem" }}>
          <button className="button-primary" onClick={startCreate}>
            <Plus size={16} style={{ marginRight: "6px" }} />
            Crear
          </button>
          <button className="button-ghost" onClick={toggleList}>
            <List size={16} style={{ marginRight: "6px" }} />
            {showList ? "Ocultar" : "Listar"}
          </button>
        </div>
      </div>

      {/* Mensajes */}
      {loading && <p>Cargando…</p>}
      {error && <p style={{ color: "red" }}>{error}</p>}

      {/* Tabla (solo visible si se presiona “Listar”) */}
      {!loading && showList && items.length > 0 && (
        <div
          className="table-wrap"
          style={{ overflowX: "auto", marginTop: ".8rem" }}
        >
          <table style={{ width: "100%", borderCollapse: "collapse" }}>
            <thead>
              <tr>
                {fields.map((f) => (
                  <th
                    key={f.name}
                    style={{
                      textAlign: "left",
                      padding: "8px",
                      borderBottom: "1px solid #eee",
                    }}
                  >
                    {f.label}
                  </th>
                ))}
                <th style={{ width: 140 }}></th>
              </tr>
            </thead>
            <tbody>
              {items.map((it) => (
                <tr key={it.id ?? it.idArea ?? it.idDba ?? JSON.stringify(it)}>
                  {fields.map((f) => (
                    <td
                      key={f.name}
                      style={{
                        padding: "8px",
                        borderBottom: "1px solid #f2f2f2",
                      }}
                    >
                      {String(it[f.name] ?? "")}
                    </td>
                  ))}
                  <td
                    style={{
                      padding: "8px",
                      display: "flex",
                      gap: ".5rem",
                      justifyContent: "flex-start",
                    }}
                  >
                    <button
                      className="button-ghost"
                      style={{
                        display: "flex",
                        alignItems: "center",
                        gap: "4px",
                      }}
                      onClick={() => startEdit(it)}
                    >
                      <Pencil size={16} />
                      Editar
                    </button>
                    <button
                      className="button-danger"
                      style={{
                        display: "flex",
                        alignItems: "center",
                        gap: "4px",
                      }}
                      onClick={() =>
                        remove(it.id ?? it.idArea ?? it.idDba)
                      }
                    >
                      <Trash2 size={16} />
                      Eliminar
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {/* Modal */}
      {showForm && (
        <div
          style={{
            position: "fixed",
            inset: 0,
            background: "rgba(0,0,0,.3)",
            display: "grid",
            placeItems: "center",
            zIndex: 1000,
          }}
        >
          <div
            className="card"
            style={{
              width: "min(560px, 96vw)",
              padding: "1rem 1.2rem",
              boxShadow: "0 4px 14px rgba(0,0,0,.2)",
            }}
          >
            <div
              style={{
                display: "flex",
                justifyContent: "space-between",
                alignItems: "center",
                marginBottom: "0.6rem",
              }}
            >
              <h4 style={{ fontSize: "1.05rem", fontWeight: 700 }}>
                {editingId ? "Editar" : "Crear"} {title.slice(0, -1)}
              </h4>
              <button
                className="button-ghost"
                onClick={() => setShowForm(false)}
                style={{ display: "flex", alignItems: "center" }}
              >
                <X size={18} />
              </button>
            </div>

            <form onSubmit={submit} style={{ display: "grid", gap: ".75rem" }}>
              {fields.map((f) => (
                <div key={f.name} style={{ display: "grid", gap: ".35rem" }}>
                  <label htmlFor={f.name} style={{ fontSize: ".92rem" }}>
                    {f.label}
                  </label>
                  <input
                    id={f.name}
                    name={f.name}
                    value={form[f.name] || ""}
                    onChange={onChange}
                    placeholder={f.placeholder || ""}
                    className="card"
                    style={{
                      padding: ".65rem .8rem",
                      outline: "none",
                      border: "1px solid #e7e8ea",
                    }}
                  />
                </div>
              ))}

              <div
                style={{
                  display: "flex",
                  gap: ".6rem",
                  justifyContent: "flex-end",
                  marginTop: ".5rem",
                }}
              >
                <button
                  type="button"
                  className="button-ghost"
                  onClick={() => setShowForm(false)}
                >
                  Cancelar
                </button>
                <button type="submit" className="button-primary">
                  Guardar
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}
