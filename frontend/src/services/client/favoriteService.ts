import apiClient from "@/lib/apiClient";
import type {
  ClientFavoriteBuilding,
  ClientFavoriteToggleResponse,
} from "@/types/client/favorite";

export const favoriteService = {
  addFavorite: async (buildingId: number): Promise<ClientFavoriteToggleResponse> => {
    const res = await apiClient.post(`/client/favorites/${buildingId}`);
    return res.data.data;
  },

  removeFavorite: async (buildingId: number): Promise<ClientFavoriteToggleResponse> => {
    const res = await apiClient.delete(`/client/favorites/${buildingId}`);
    return res.data.data;
  },

  checkFavorite: async (buildingId: number): Promise<ClientFavoriteToggleResponse> => {
    const res = await apiClient.get(`/client/favorites/${buildingId}/check`);
    return res.data.data;
  },

  getMyFavorites: async (): Promise<ClientFavoriteBuilding[]> => {
    const res = await apiClient.get("/client/favorites");
    return res.data.data;
  },
};