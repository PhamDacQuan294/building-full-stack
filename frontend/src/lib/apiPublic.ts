import axios from "axios";

const apiPublic = axios.create({
  baseURL:
    import.meta.env.MODE === "development"
      ? "http://localhost:8081/api"
      : "/api",
  withCredentials: true,
});

export default apiPublic;