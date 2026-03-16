import { create } from "zustand";
import { adminContactRequestService } from "@/services/admin/contactRequestService";
import type { AdminContactRequestItem } from "@/types/admin/contactRequest";

type Store = {
  items: AdminContactRequestItem[];
  loading: boolean;
  fetchAll: () => Promise<void>;
  updateStatus: (id: number, status: string) => Promise<void>;
};

export const useAdminContactRequestStore = create<Store>((set, get) => ({
  items: [],
  loading: false,

  fetchAll: async () => {
    set({ loading: true });
    try {
      const data = await adminContactRequestService.findAll();
      set({ items: data });
    } finally {
      set({ loading: false });
    }
  },

  updateStatus: async (id, status) => {
    await adminContactRequestService.updateStatus(id, status);
    await get().fetchAll();
  },
}));