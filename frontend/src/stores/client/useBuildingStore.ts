import { create } from "zustand";
import { buildingService } from "@/services/client/buildingService";
import type {
  ClientBuildingListItem,
  ClientBuildingSearchFilters,
} from "@/types/client/building";

type BuildingStore = {
  items: ClientBuildingListItem[];
  totalItems: number;
  totalPages: number;
  loading: boolean;
  filters: ClientBuildingSearchFilters;

  setFilters: (payload: Partial<ClientBuildingSearchFilters>) => void;
  fetchBuildings: () => Promise<void>;
  changePage: (page: number) => Promise<void>;
  resetFilters: () => Promise<void>;
};

const defaultFilters: ClientBuildingSearchFilters = {
  keyword: "",
  district: "",
  rentPriceFrom: "",
  rentPriceTo: "",
  areaFrom: "",
  areaTo: "",
  rentType: "",
  sortBy: "newest",
  page: 1,
  limit: 8,
};

export const useBuildingStore = create<BuildingStore>((set, get) => ({
  items: [],
  totalItems: 0,
  totalPages: 0,
  loading: false,
  filters: defaultFilters,

  setFilters: (payload) =>
    set((state) => ({
      filters: {
        ...state.filters,
        ...payload,
      },
    })),

  fetchBuildings: async () => {
    set({ loading: true });
    try {
      const { filters } = get();
      const data = await buildingService.getBuildings(filters);

      set({
        items: data.items || [],
        totalItems: data.totalItems || 0,
        totalPages: data.totalPages || 0,
      });
    } finally {
      set({ loading: false });
    }
  },

  changePage: async (page) => {
    set((state) => ({
      filters: {
        ...state.filters,
        page,
      },
    }));
    await get().fetchBuildings();
  },

  resetFilters: async () => {
    set({ filters: defaultFilters });
    await get().fetchBuildings();
  },
}));