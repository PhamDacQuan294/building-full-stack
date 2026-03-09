import { create } from "zustand";
import { toast } from "sonner";
import { buildingService } from "@/services/admin/buildingService";
import type { BuildingFilters, BuildingItem } from "@/types/admin/buildings";

const initialFilters: BuildingFilters = {
  name: "",
  floorArea: "",
  district: "",
  ward: "",
  street: "",
  basement: "",
  rentFrom: "",
  rentTo: "",
  staffId: "",

  types: {},

  status: "all",
  sort: "rent-desc",
  page: 1,
  limit: 10,
};

type BuildingState = {
  filters: BuildingFilters;
  items: BuildingItem[];
  districts: Record<string, string>;
  staffs: Record<string, string>;
  rentTypes: Record<string, string>;
  pagination: { page: number; totalPage: number; totalItems?: number };
  loading: boolean;

  setFilters: (patch: Partial<BuildingFilters>) => void;
  setTypes: (patch: Record<string, boolean>) => void;
  resetFilters: () => void;
  search: () => Promise<void>;
};

export const useBuildingStore = create<BuildingState>((set, get) => ({
  filters: initialFilters,
  items: [],
  districts: {},
  staffs: {},
  rentTypes: {},
  pagination: { page: 1, totalPage: 1 },
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

  setTypes: (patch) => {
    set((state) => ({
      filters: {
        ...state.filters,
        page: 1,
        types: {
          ...state.filters.types,
          ...patch,
        },
      },
    }));
  },

  resetFilters: async () => {
    set({ filters: initialFilters });
    await get().search();
  },

  search: async () => {
    try {
      set({ loading: true })

      const { filters } = get()
      const res = await buildingService.search(filters)

      set({
        items: res.data.buildings || [],
        districts: res.data.districts || {},
        staffs: res.data.staffs || {},
        rentTypes: res.data.rentTypes || {},
        pagination: { page: filters.page, totalPage: 1 },
      })
    } catch (error) {
      console.error(error)
      toast.error("Tìm kiếm không thành công!")
    } finally {
      set({ loading: false })
    }
  },
}));