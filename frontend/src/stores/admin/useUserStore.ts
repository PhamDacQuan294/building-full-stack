import { create } from "zustand";
import { userService } from "@/services/admin/userService";
import type { UserFilters, UserItem } from "@/types/admin/users";

type UserState = {
  items: UserItem[];
  loading: boolean;
  totalItems: number;
  filters: UserFilters;
  setFilters: (data: Partial<UserFilters>) => void;
  resetFilters: () => void;
  search: () => Promise<void>;
};

const initialFilters: UserFilters = {
  fullName: "",
  email: "",
  phone: "",
  status: "",
  page: 1,
  limit: 10,
};

export const useUserStore = create<UserState>((set, get) => ({
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
      const res = await userService.search(get().filters);

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