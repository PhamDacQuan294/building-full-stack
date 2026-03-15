import api from "@/lib/axios";
import type { ActivityLogFilters } from "@/types/admin/activityLog";

export const activityLogService = {
  search: async (params: ActivityLogFilters) => {
    const res = await api.get("/activity-logs", { params });
    return res.data;
  },
};