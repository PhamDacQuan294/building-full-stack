import { create } from "zustand";
import { toast } from "sonner";
import { customerService } from "@/services/admin/customerService";
import type { CustomerFilters, CustomerItem } from "@/types/admin/customers";

const initialFilters: CustomerFilters = {
  fullName: "",
  phone: "",
  email: "",
  staffId: "",
  status: "",
  page: 1,
  limit: 10,
};

type CustomerState = {
  filters: CustomerFilters;
  items: CustomerItem[];
  totalItems: number;
  loading: boolean;

  setFilters: (patch: Partial<CustomerFilters>) => void;
  resetFilters: () => void;
  search: () => Promise<void>;
};

export const useCustomerStore = create<CustomerState>((set, get) => ({
  filters: initialFilters,
  items: [],
  totalItems: 0,
  loading: false,

  setFilters: (patch) => {
    set((state) => ({
      filters: {
        ...state.filters,
        ...patch,
        page: patch.page ?? 1,
      },
    }));
  },

  resetFilters: () => {
    set({ filters: initialFilters });
  },

  search: async () => {
    try {
      set({ loading: true });

      const { filters } = get();
      const res = await customerService.search(filters);

      set({
        items: res.data.customers || [],
        totalItems: res.data.totalItems || 0,
      });
    } catch (error) {
      console.error(error);
      toast.error("Tải danh sách khách hàng thất bại");
    } finally {
      set({ loading: false });
    }
  },
}));