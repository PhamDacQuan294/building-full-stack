import { getToken } from "@/utils/auth";
import axios from "axios";

const apiClient = axios.create({
  baseURL:
    import.meta.env.MODE === "development"
      ? "http://localhost:8081/api"
      : "/api",
  withCredentials: true,
});

apiClient.interceptors.request.use((config) => {
  const token = getToken();

  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

export default apiClient;