import { create } from "zustand";
import { toast } from "sonner";
import { transactionService } from "@/services/admin/transactionService";
import type { TransactionFilters, TransactionItem } from "@/types/admin/transactions";

const initialFilters: TransactionFilters = {
  customerId: "",
  staffId: "",
  transactionType: "",
  transactionStatus: "",
  fromDate: "",
  toDate: "",
  page: 1,
  limit: 10,
};

type TransactionState = {
  filters: TransactionFilters;
  items: TransactionItem[];
  totalItems: number;
  loading: boolean;
  setFilters: (patch: Partial<TransactionFilters>) => void;
  resetFilters: () => void;
  search: () => Promise<void>;
};

export const useTransactionStore = create<TransactionState>((set, get) => ({
  filters: initialFilters,
  items: [],
  totalItems: 0,
  loading: false,

  setFilters: (patch) => {
    set((state) => ({
      filters: {
        ...state.filters,
        ...patch,
      },
    }));
  },

  resetFilters: () => {
    set({ filters: initialFilters });
  },

  search: async () => {
    try {
      set({ loading: true });
      const res = await transactionService.search(get().filters);

      set({
        items: res.data.transactions || [],
        totalItems: res.data.totalItems || 0,
      });
    } catch (error) {
      console.error(error);
      toast.error("Tải danh sách giao dịch thất bại");
    } finally {
      set({ loading: false });
    }
  },
}));