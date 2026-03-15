import { create } from "zustand";
import { activityLogService } from "@/services/admin/activityLogService";
import type { ActivityLogFilters, ActivityLogItem } from "@/types/admin/activityLog";

type ActivityLogState = {
  items: ActivityLogItem[];
  loading: boolean;
  totalItems: number;
  filters: ActivityLogFilters;
  setFilters: (data: Partial<ActivityLogFilters>) => void;
  resetFilters: () => void;
  search: () => Promise<void>;
};

const initialFilters: ActivityLogFilters = {
  actorEmail: "",
  action: "",
  module: "",
  page: 1,
  limit: 10,
};

export const useActivityLogStore = create<ActivityLogState>((set, get) => ({
  items: [],
  loading: false,
  totalItems: 0,
  filters: initialFilters,

  setFilters: (data) => {
    set({
      filters: {
        ...get().filters,
        ...data,
      },
    });
  },

  resetFilters: () => {
    set({ filters: initialFilters });
  },

  search: async () => {
    try {
      set({ loading: true });
      const res = await activityLogService.search(get().filters);

      set({
        items: res.data || [],
        totalItems: res.totalItem || 0,
      });
    } catch (error) {
      console.error(error);
    } finally {
      set({ loading: false });
    }
  },
}));