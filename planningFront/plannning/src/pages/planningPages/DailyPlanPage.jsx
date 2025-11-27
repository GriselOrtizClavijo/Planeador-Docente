import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import SelectSubjects from "../../components/planningComponents/SelectSubjects";
import DailyPlanForm from "../../components/planningComponents/DailyPlanForm";

export default function DailyPlanPage() {
	const { fecha } = useParams();
	const navigate = useNavigate();

	const [materiasDia, setMateriasDia] = useState([]);
	const [seleccionando, setSeleccionando] = useState(false);
	const [editando, setEditando] = useState(null);

	// 🔹 Cargar materias ya creadas para este día
	const loadMaterias = async () => {
		const res = await fetch(
			`http://localhost:8080/api/daily-plan/by-date/${fecha}`
		);
		const data = await res.json();
		setMateriasDia(data);
	};

	useEffect(() => {
		loadMaterias();
	}, [fecha]);

	// 🔹 Crear plan vacío luego de seleccionar un área
	const crearPlanVacio = async (materia) => {
		const payload = {
			areaId: materia.idArea,
			date: fecha,
			state: "INCOMPLETE",
		};

		const res = await fetch(`http://localhost:8080/api/daily-plan`, {
			method: "POST",
			headers: { "Content-Type": "application/json" },
			body: JSON.stringify(payload),
		});

		if (!res.ok) {
			alert("Error creando el plan");
			return;
		}

		loadMaterias();
	};

	return (
		<div style={{ padding: "2rem" }}>
			{/* Botón volver */}
			<button
				type="button"
				onClick={() => navigate(-1)}
				className="button-ghost"
				style={{ marginBottom: "1rem" }}
			>
				⬅ Volver
			</button>

			<h2>
				Planeación del día: <span style={{ color: "#007bff" }}>{fecha}</span>
			</h2>

			{/* Botón seleccionar materias */}
			{!seleccionando && !editando && (
				<button
					onClick={() => setSeleccionando(true)}
					className="button-primary"
					style={{ marginTop: "1rem" }}
				>
					+ Seleccionar materias del día
				</button>
			)}

			{/* Lista de materias */}
			{!seleccionando && !editando && (
				<div style={{ marginTop: "2rem" }}>
					<h3>Materias asignadas hoy:</h3>

					{materiasDia.length === 0 && (
						<p>📌 No hay materias seleccionadas todavía.</p>
					)}

					{materiasDia.length > 0 &&
						materiasDia.map((m) => {
							const estado = m.state === "COMPLETE" ? "COMPLETO" : "INCOMPLETO";
							const color = m.state === "COMPLETE" ? "#28a745" : "#f4c542";

							return (
								<div
									key={m.idPlan}
									style={{
										padding: "1rem",
										border: "1px solid #ddd",
										borderRadius: "10px",
										marginBottom: ".9rem",
										display: "flex",
										justifyContent: "space-between",
										alignItems: "center",
										background: "#fff",
										boxShadow: "0 2px 5px rgba(0,0,0,.08)",
									}}
								>
									<div>
										<strong style={{ fontSize: "1rem" }}>{m.area.name}</strong>
										<span
											style={{
												marginLeft: "1rem",
												padding: ".25rem .6rem",
												backgroundColor: color,
												color: "#000",
												borderRadius: "6px",
												fontSize: ".85rem",
												fontWeight: 600,
											}}
										>
											{estado}
										</span>
									</div>

									<button
										className="button-primary"
										onClick={() => setEditando(m)}
										style={{ padding: ".4rem 1rem" }}
									>
										Completar
									</button>
									<button
										className="button-danger"
										onClick={async () => {
											if (!window.confirm("¿Eliminar esta materia del día?"))
												return;

											await fetch(
												`http://localhost:8080/api/daily-plan/${m.idPlan}`,
												{
													method: "DELETE",
												}
											);
											loadMaterias();
											window.dispatchEvent(new Event("updateCalendar"));
										}}
										style={{ padding: ".4rem 1rem", marginLeft: ".5rem" }}
									>
										Eliminar
									</button>
								</div>
							);
						})}
				</div>
			)}

			{/* Selección de materias */}
			{seleccionando && (
				<SelectSubjects
					onSelect={(materias) => {
						setSeleccionando(false);

						// crear varios planes vacíos
						materias.forEach((materia) => {
							crearPlanVacio(materia);
							window.dispatchEvent(new Event("updateCalendar"));
						});
					}}
					onCancel={() => setSeleccionando(false)}
				/>
			)}

			{/* Formulario de planeación */}
			{editando && (
				<DailyPlanForm
					area={editando.area}
					fecha={fecha}
					plan={editando}
					onSaved={() => {
						setEditando(null);
						loadMaterias();
					}}
					onCancel={() => setEditando(null)}
				/>
			)}
		</div>
	);
}
