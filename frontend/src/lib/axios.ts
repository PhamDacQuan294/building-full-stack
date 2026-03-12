import { getToken } from "@/utils/auth";
import axios from "axios";

const api = axios.create({
  baseURL:
    import.meta.env.MODE === "development" ? "http://localhost:8081/api/admin" : "/api/admin",
  withCredentials: true,
});

api.interceptors.request.use((config) => {
  const token = getToken();

  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

export default api;