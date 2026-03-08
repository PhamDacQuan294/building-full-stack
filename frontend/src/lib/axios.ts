import axios from "axios";

const api = axios.create({
  baseURL:
    import.meta.env.MODE === "development" ? "http://localhost:8081/api/admin" : "/api/admin",
  withCredentials: true,
});


export default api;