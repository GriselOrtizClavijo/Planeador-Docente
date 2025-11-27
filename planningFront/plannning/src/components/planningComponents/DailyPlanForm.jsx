// src/components/planningComponents/DailyPlanForm.jsx
import React, { useState, useEffect } from "react";

export default function DailyPlanForm({
	area,
	fecha,
	plan,
	onSaved,
	onCancel,
}) {
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
		resources: plan.resources?.map((r) => r.idResources) ?? [],
		observations: plan.observations ?? "",
	});

	const [datos, setDatos] = useState({
		dbas: [],
		competencias: [],
		learning: [],
		ejes: [],
		criterios: [],
		actividades: [],
		recursos: [],
	});

	const [imagenesGuardadas, setImagenesGuardadas] = useState([]);
	const [imagenes, setImagenes] = useState([]);

	// ✔ Cargar imágenes guardadas (fuera del useEffect)
	const loadSavedImages = async (planId) => {
		if (!planId) return;

		try {
			const res = await fetch(
				`http://localhost:8080/api/daily-plan/images/${planId}`
			);
			const data = await res.json();
			setImagenesGuardadas(data);
		} catch (e) {
			console.error("Error cargando imágenes:", e);
		}
	};

	// ✔ SOLO UN useEffect – sin nested hooks
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
				recursos,
			] = await Promise.all([
				fetch(`http://localhost:8080/api/dbas?areaId=${id}`).then((r) =>
					r.json()
				),
				fetch(`http://localhost:8080/api/competencies?areaId=${id}`).then((r) =>
					r.json()
				),
				fetch(`http://localhost:8080/api/learning?areaId=${id}`).then((r) =>
					r.json()
				),
				fetch(`http://localhost:8080/api/thematic-axes?areaId=${id}`).then(
					(r) => r.json()
				),
				fetch(
					`http://localhost:8080/api/evaluation-criteria?areaId=${id}`
				).then((r) => r.json()),
				fetch(
					`http://localhost:8080/api/self-improvement-activities?areaId=${id}`
				).then((r) => r.json()),
				fetch(`http://localhost:8080/api/resources?areaId=${id}`).then((r) =>
					r.json()
				),
			]);

			setDatos({
				dbas,
				competencias,
				learning,
				ejes,
				criterios,
				actividades,
				recursos,
			});

			// 🔥 cargar imágenes guardadas
			await loadSavedImages(form.idPlan);
		};

		load();
	}, [area, form.idPlan]);

	const onChange = (e) => {
		setForm({ ...form, [e.target.name]: e.target.value });
	};

	// ✔ Normalizador
	const normalize = (value) => (value === "" ? null : value);

	// ✔ Actualizar estado y subir imágenes
	const actualizarEstado = async (nuevoEstado) => {
		const payload = {
			...form,
			state: nuevoEstado,
			idDba: normalize(form.idDba),
			idCompetencies: normalize(form.idCompetencies),
			idLearning: normalize(form.idLearning),
			idThematicAxes: normalize(form.idThematicAxes),
			idEvaluationCriteria: normalize(form.idEvaluationCriteria),
			idSiA: normalize(form.idSiA),
			resources: form.resources ?? [],
		};

		const url = `http://localhost:8080/api/daily-plan/${form.idPlan}`;

		const res = await fetch(url, {
			method: "PUT",
			headers: { "Content-Type": "application/json" },
			body: JSON.stringify(payload),
		});

		if (!res.ok) {
			alert("No se pudo guardar");
			return;
		}

		// 🔹 Subir imágenes nuevas
		await uploadImages();

		// 🔹 Recargar imágenes guardadas
		await loadSavedImages(form.idPlan);

		// 🔹 Reset de imágenes locales
		setImagenes([]);

		onSaved();
		window.dispatchEvent(new Event("updateCalendar"));
	};

	// ✔ Subir imágenes al backend
	const uploadImages = async () => {
		if (!form.idPlan) return;

		for (const img of imagenes) {
			const formData = new FormData();
			formData.append("file", img);

			try {
				await fetch(
					`http://localhost:8080/api/daily-plan/images/${form.idPlan}`,
					{
						method: "POST",
						body: formData,
					}
				);
			} catch (e) {
				console.error("Error subiendo imagen:", e);
			}
		}
	};

	// ⛔ Si no hay área
	if (!area || !area.idArea) {
		return <p>Cargando datos del área...</p>;
	}

	return (
		<form className="card" style={{ padding: "1.4rem", marginTop: "2rem" }}>
			<h3>{area.name}</h3>
			<p style={{ fontSize: ".9rem", color: "#555" }}>Fecha: {fecha}</p>

			{/* --- SELECTS --- */}
			<div>
				<label>DBA</label>
				<select name="idDba" value={form.idDba} onChange={onChange}>
					<option value="">Seleccione un DBA</option>
					{datos.dbas.map((d) => (
						<option key={d.idDba} value={d.idDba}>
							{d.description}
						</option>
					))}
				</select>
			</div>

			<div>
				<label>Competencias</label>
				<select
					name="idCompetencies"
					value={form.idCompetencies}
					onChange={onChange}
				>
					<option value="">Seleccione una competencia</option>
					{datos.competencias.map((c) => (
						<option key={c.idCompetencies} value={c.idCompetencies}>
							{c.name}
						</option>
					))}
				</select>
			</div>

			<div>
				<label>Aprendizajes</label>
				<select name="idLearning" value={form.idLearning} onChange={onChange}>
					<option value="">Seleccione un aprendizaje</option>
					{datos.learning.map((l) => (
						<option key={l.idLearning} value={l.idLearning}>
							{l.name}
						</option>
					))}
				</select>
			</div>

			<div>
				<label>Ejes Temáticos</label>
				<select
					name="idThematicAxes"
					value={form.idThematicAxes}
					onChange={onChange}
				>
					<option value="">Seleccione un eje</option>
					{datos.ejes.map((e) => (
						<option key={e.idThematicAxes} value={e.idThematicAxes}>
							{e.name}
						</option>
					))}
				</select>
			</div>

			<div>
				<label>Actividades de profundización</label>
				<select name="idSiA" value={form.idSiA} onChange={onChange}>
					<option value="">Seleccione una actividad</option>
					{datos.actividades.map((a) => (
						<option key={a.idSiA} value={a.idSiA}>
							{a.name}
						</option>
					))}
				</select>
			</div>

			<div>
				<label>Criterios de Evaluación</label>
				<select
					name="idEvaluationCriteria"
					value={form.idEvaluationCriteria}
					onChange={onChange}
				>
					<option value="">Seleccione un criterio</option>
					{datos.criterios.map((cr) => (
						<option
							key={cr.idEvaluationCriteria}
							value={cr.idEvaluationCriteria}
						>
							{cr.description}
						</option>
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

			{/* IMÁGENES */}
			<div style={{ marginTop: "1rem" }}>
				<label style={{ fontWeight: 600 }}>Imágenes (máximo 3)</label>

				<input
					type="file"
					accept="image/png, image/jpeg, image/jpg"
					multiple
					onChange={(e) => {
						const files = Array.from(e.target.files);

						if (imagenesGuardadas.length + imagenes.length + files.length > 3) {
							alert("Máximo 3 imágenes en total.");
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

				<p style={{ fontSize: ".8rem", color: "#555", marginTop: ".3rem" }}>
					Total seleccionadas: {imagenes.length} / 3
				</p>

				{/* IMÁGENES GUARDADAS */}
				{imagenesGuardadas.length > 0 && (
					<div style={{ marginTop: "1rem" }}>
						<h4>Imágenes guardadas</h4>

						<div style={{ display: "flex", gap: "1rem", flexWrap: "wrap" }}>
							{imagenesGuardadas.map((img) => (
								<div key={img.id} style={{ textAlign: "center" }}>
									<img
										src={`http://localhost:8080${img.url}`}
										alt="prev"
										style={{
											width: 120,
											height: 120,
											objectFit: "cover",
											borderRadius: 8,
											border: "1px solid #ccc",
										}}
									/>
								</div>
							))}
						</div>
					</div>
				)}

				{/* NUEVAS IMÁGENES */}
				{imagenes.length > 0 && (
					<div style={{ marginTop: "1rem" }}>
						<h4>Nuevas imágenes (aún no guardadas)</h4>

						<div style={{ display: "flex", gap: "1rem", flexWrap: "wrap" }}>
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
											border: "1px solid #ccc",
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
					</div>
				)}
			</div>

			{/* BOTONES */}
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

				<button type="button" className="button-ghost" onClick={onCancel}>
					Cancelar
				</button>
			</div>
		</form>
	);
}
