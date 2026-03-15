import api from "@/lib/axios";

export const dashboardService = {
  getDashboard: async () => {
    const res = await api.get("/dashboard");
    return res.data;
  },
};