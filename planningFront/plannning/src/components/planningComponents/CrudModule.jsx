// src/components/planningComponents/CrudModule.jsx
import { Plus, List, Pencil, Trash2, X } from "lucide-react";
import React, { useState, useEffect } from "react";

import "../../styles/CrudModule.css";

export default function CrudModule({
	title,
	fields = [],
	endpoint,
	savedState = {},
	onStateChange,
}) {
	const [items, setItems] = useState(savedState.items || []);
	const [form, setForm] = useState(savedState.form || {});
	const [loading, setLoading] = useState(false);
	const [error, setError] = useState("");
	const [showForm, setShowForm] = useState(savedState.showForm || false);
	const [showList, setShowList] = useState(savedState.showList || false);
	const [editingId, setEditingId] = useState(savedState.editingId || null);

	// 🔹 Nuevos estados para áreas y periodos
	const [areas, setAreas] = useState([]);
	const periodOptions = [1, 2, 3, 4];

	// 🔹 Cargar lista general
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

	// 🔹 Cargar lista de áreas solo una vez
	useEffect(() => {
		const loadAreas = async () => {
			try {
				const res = await fetch("http://localhost:8080/api/areas");
				if (res.ok) {
					const data = await res.json();
					setAreas(data);
				}
			} catch (e) {
				console.error("Error al cargar áreas:", e);
			}
		};
		loadAreas();
	}, []);

	// 🔹 Manejar inputs de texto
	const onChange = (e) => {
		setForm({ ...form, [e.target.name]: e.target.value });
	};

	// 🔹 Manejar selección de área
	const onAreaChange = (e) => {
		setForm({ ...form, areaId: parseInt(e.target.value, 10) });
	};

	// 🔹 Manejar selección múltiple de periodos
	const onPeriodChange = (e) => {
		const selected = Array.from(e.target.selectedOptions, (opt) => opt.value);
		const formatted = selected.map((num) => `PERIODO_${num}`);
		setForm({ ...form, periods: formatted });
	};

	// 🔹 Crear nuevo registro
	const startCreate = () => {
		setForm({ periods: ["PERIODO_1"] });
		setForm({});
		setEditingId(null);
		setShowForm(true);
	};

	// 🔹 Editar existente
	// 🔹 Editar existente
	const startEdit = (item) => {
		const detectedId =
			item.id ??
			item.idDba ??
			item.idLearning ??
			item.idCompetencies ??
			item.idThematicAxes ??
			item.idEvaluationCriteria ??
			item.idActivity ??
			item.idSiA ??
			item.idArea;
		//item.idResource ??

		setForm({
			...item,
			areaId: item.areas?.idArea || item.areaId || "",
			periods: item.periods || [],
		});
		setEditingId(detectedId);
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

			// 🧹 Limpiamos campos innecesarios
			const payload = { ...form };
			delete payload.areas; // 🔥 evita error "Bad Request"
			delete payload.filterAreaId;

			console.log("🛰️ Enviando datos:", JSON.stringify(payload)); // 👀 depuración

			const res = await fetch(url, {
				method,
				headers: { "Content-Type": "application/json" },
				body: JSON.stringify(payload),
				credentials: "include",
			});

			if (!res.ok) throw new Error("Error al guardar");
			await load();
			setShowForm(false);
		} catch (err) {
			setError(err.message);
		}
	};

	// 🔹 Eliminar
	// 🔹 Eliminar (versión automática y genérica)
	const remove = async (item) => {
		// 🔍 Buscar automáticamente el campo de ID (el que empieza por "id")
		const idKey = Object.keys(item).find((k) =>
			k.toLowerCase().startsWith("id")
		);
		const id = idKey ? item[idKey] : null;

		if (!id) {
			alert("No se encontró el ID del registro para eliminar.");
			return;
		}

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

	// 🔹 Determinar si este módulo usa área/periodos
	const normalizedTitle = title
		.toLowerCase()
		.normalize("NFD")
		.replace(/[\u0300-\u036f]/g, "");

	const isComplexModule = [
		"dba",
		"competencias",
		"ejes tematicos",
		"criterios de evaluacion",
		"actividades de profundizacion",
		"aprendizajes",
		"actividades de profundización",
	].includes(normalizedTitle);

	React.useEffect(() => {
		onStateChange({
			items,
			form,
			showForm,
			showList,
			editingId,
		});
	}, [items, form, showForm, showList, editingId]);

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
					{/* 🔍 Filtro por área (solo visible si es módulo complejo) */}
					{isComplexModule && areas.length > 0 && (
						<select
							value={form.filterAreaId || ""}
							onChange={(e) =>
								setForm({ ...form, filterAreaId: e.target.value })
							}
							className="filter-select"
						>
							<option value="">Todas las áreas</option>
							{areas.map((a) => (
								<option key={a.idArea} value={a.idArea}>
									{a.name}
								</option>
							))}
						</select>
					)}

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

			{/* --- TABLA --- */}
			{!loading && showList && items.length > 0 && (
				<div className="table-container">
					<table className="styled-table">
						<thead>
							<tr>
								{fields.map((f) => (
									<th key={f.name}>{f.label}</th>
								))}
								{isComplexModule && <th>Área</th>}
								{isComplexModule && <th>Periodos</th>}
								<th style={{ width: 140 }}></th>
							</tr>
						</thead>
						<tbody>
							{items
								.filter((it) => {
									// 🔍 filtrar por área si el usuario seleccionó una
									if (!form.filterAreaId) return true;
									return it.areas?.idArea === parseInt(form.filterAreaId);
								})
								.map((it) => (
									<tr
										key={it.id ?? it.idDba ?? it.idArea ?? JSON.stringify(it)}
									>
										{fields.map((f) => (
											<td key={f.name}>{String(it[f.name] ?? "")}</td>
										))}

										{/* Mostrar nombre del área */}
										{isComplexModule && <td>{it.areas?.name || "—"}</td>}

										{/* Mostrar periodos en chips */}
										{isComplexModule && (
											<td>
												<div className="period-badges">
													{(it.periods || []).map((p) => {
														const num = p.split("_")[1];
														return (
															<span
																key={p}
																className={`period-badge period-${num}`}
															>
																{num}
															</span>
														);
													})}
												</div>
											</td>
										)}

										<td className="actions">
											<button
												className="button-ghost"
												onClick={() => startEdit(it)}
											>
												<Pencil size={16} /> Editar
											</button>
											<button
												className="button-danger"
												onClick={() => remove(it)}
											>
												<Trash2 size={16} /> Eliminar
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
							padding: "1.4rem",
							boxShadow: "0 4px 14px rgba(0,0,0,.25)",
							background: "#fff",
							borderRadius: "12px",
						}}
					>
						<div
							style={{
								display: "flex",
								justifyContent: "space-between",
								alignItems: "center",
							}}
						>
							<h4 style={{ fontSize: "1.05rem", fontWeight: 700 }}>
								{editingId ? "Editar" : "Crear"} {title.slice(0, -1)}
							</h4>
							<button
								className="button-ghost"
								onClick={() => setShowForm(false)}
							>
								<X size={18} />
							</button>
						</div>

						<form
							onSubmit={submit}
							style={{ display: "grid", gap: ".9rem", marginTop: ".8rem" }}
						>
							{isComplexModule && (
								<>
									{/* Selector de área */}
									<div>
										<label htmlFor="areaId" style={{ fontWeight: 600 }}>
											Área
										</label>
										<select
											id="areaId"
											name="areaId"
											value={form.areaId || ""}
											onChange={onAreaChange}
											className="form-select"
											style={{
												width: "100%",
												padding: ".6rem",
												borderRadius: "8px",
												border: "1px solid #ccc",
											}}
											required
										>
											<option value="">Seleccione un área</option>
											{areas.map((a) => (
												<option key={a.idArea} value={a.idArea}>
													{a.name}
												</option>
											))}
										</select>
									</div>

									{/* Selector de periodos con colores */}
									<div className="periods-group">
										<label style={{ fontWeight: 600 }}>Periodos</label>
										<div className="period-buttons">
											{periodOptions.map((p) => {
												const isSelected = form.periods?.includes(
													`PERIODO_${p}`
												);
												return (
													<button
														type="button"
														key={p}
														className={`period-btn period-${p} ${
															isSelected ? "selected" : ""
														}`}
														onClick={() => {
															const current = new Set(form.periods || []);
															if (isSelected) {
																current.delete(`PERIODO_${p}`);
															} else {
																current.add(`PERIODO_${p}`);
															}
															setForm({
																...form,
																periods: Array.from(current),
															});
														}}
													>
														{p}
													</button>
												);
											})}
										</div>
									</div>
								</>
							)}

							{/* Campos de texto normales */}
							{fields.map((f) => (
								<div key={f.name} style={{ display: "grid", gap: ".35rem" }}>
									<label htmlFor={f.name} style={{ fontWeight: 600 }}>
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
											borderRadius: "8px",
											border: "1px solid #ccc",
										}}
										required
									/>
								</div>
							))}

							<div
								style={{
									display: "flex",
									gap: ".6rem",
									justifyContent: "flex-end",
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
