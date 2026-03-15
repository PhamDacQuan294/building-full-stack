import { create } from "zustand";
import { mailService } from "@/services/admin/mailService";
import type { EmailLogFilters, EmailLogItem } from "@/types/admin/mail";

type EmailLogState = {
  items: EmailLogItem[];
  loading: boolean;
  totalItems: number;
  filters: EmailLogFilters;
  setFilters: (data: Partial<EmailLogFilters>) => void;
  resetFilters: () => void;
  search: () => Promise<void>;
};

const initialFilters: EmailLogFilters = {
  toEmail: "",
  mailType: "",
  module: "",
  page: 1,
  limit: 10,
};

export const useEmailLogStore = create<EmailLogState>((set, get) => ({
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
      const res = await mailService.getLogs(get().filters);

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