import { create } from "zustand";
import { homeService } from "@/services/client/homeService";
import type { ClientBuildingCard, HighlightDistrict, HomeSearchFilters } from "@/types/client/home";

type HomeState = {
  bannerTitle: string;
  bannerDescription: string;
  featuredBuildings: ClientBuildingCard[];
  newestBuildings: ClientBuildingCard[];
  highlightDistricts: HighlightDistrict[];
  districts: string[];

  searchItems: ClientBuildingCard[];
  totalItems: number;

  loading: boolean;

  filters: HomeSearchFilters;
  setFilters: (data: Partial<HomeSearchFilters>) => void;
  resetFilters: () => void;

  loadHomePage: () => Promise<void>;
  searchBuildings: () => Promise<void>;
};

const initialFilters: HomeSearchFilters = {
  keyword: "",
  district: "",
  rentPriceFrom: "",
  rentPriceTo: "",
  page: 1,
  limit: 8,
};

export const useHomeStore = create<HomeState>((set, get) => ({
  bannerTitle: "",
  bannerDescription: "",
  featuredBuildings: [],
  newestBuildings: [],
  highlightDistricts: [],
  districts: [],

  searchItems: [],
  totalItems: 0,

  loading: false,

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

  loadHomePage: async () => {
    try {
      set({ loading: true });
      const res = await homeService.getHomePageData();
      const data = res.data;

      set({
        bannerTitle: data.bannerTitle || "",
        bannerDescription: data.bannerDescription || "",
        featuredBuildings: data.featuredBuildings || [],
        newestBuildings: data.newestBuildings || [],
        highlightDistricts: data.highlightDistricts || [],
        districts: data.districts || [],
      });
    } catch (error) {
      console.error(error);
    } finally {
      set({ loading: false });
    }
  },

  searchBuildings: async () => {
    try {
      set({ loading: true });
      const res = await homeService.searchBuildings(get().filters);
      const data = res.data;

      set({
        searchItems: data.items || [],
        totalItems: data.totalItems || 0,
      });
    } catch (error) {
      console.error(error);
    } finally {
      set({ loading: false });
    }
  },
}));