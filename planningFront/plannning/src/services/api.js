// src/services/api.js
import axios from "axios";


const api = axios.create({
baseURL: "http://localhost:8080/api", // ajusta si usas otro puerto o prefijo
headers: { "Content-Type": "application/json" },
});


export default api;