import axios from "axios";
import { getClientToken } from "@/utils/clientAuth";

const apiClient = axios.create({
  baseURL:
    import.meta.env.MODE === "development"
      ? "http://localhost:8081/api"
      : "/api",
  withCredentials: true,
});

apiClient.interceptors.request.use((config) => {
  const token = getClientToken();

  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

export default apiClient;