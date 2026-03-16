import { create } from "zustand";
import { favoriteService } from "@/services/client/favoriteService";
import type { ClientFavoriteBuilding } from "@/types/client/favorite";

type Store = {
  items: ClientFavoriteBuilding[];
  favoriteMap: Record<number, boolean>;
  loading: boolean;

  fetchFavorites: () => Promise<void>;
  checkFavorite: (buildingId: number) => Promise<void>;
  toggleFavorite: (buildingId: number) => Promise<void>;
};

export const useFavoriteStore = create<Store>((set, get) => ({
  items: [],
  favoriteMap: {},
  loading: false,

  fetchFavorites: async () => {
    set({ loading: true });
    try {
      const data = await favoriteService.getMyFavorites();

      const map: Record<number, boolean> = {};
      data.forEach((item) => {
        map[item.buildingId] = true;
      });

      set({
        items: data,
        favoriteMap: map,
      });
    } finally {
      set({ loading: false });
    }
  },

  checkFavorite: async (buildingId: number) => {
    try {
      const data = await favoriteService.checkFavorite(buildingId);
      set((state) => ({
        favoriteMap: {
          ...state.favoriteMap,
          [buildingId]: data.favorite,
        },
      }));
    } catch {
      set((state) => ({
        favoriteMap: {
          ...state.favoriteMap,
          [buildingId]: false,
        },
      }));
    }
  },

  toggleFavorite: async (buildingId: number) => {
    const isFavorite = get().favoriteMap[buildingId];

    if (isFavorite) {
      await favoriteService.removeFavorite(buildingId);
    } else {
      await favoriteService.addFavorite(buildingId);
    }

    await get().fetchFavorites();
  },
}));