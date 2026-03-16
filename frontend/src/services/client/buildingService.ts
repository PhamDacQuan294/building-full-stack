import apiClient from "@/lib/axiosClient";
import type {
  ClientBuildingDetail,
  ClientBuildingListResponse,
  ClientBuildingSearchFilters,
} from "@/types/client/building";

export const buildingService = {
  getBuildings: async (
    filters: ClientBuildingSearchFilters
  ): Promise<ClientBuildingListResponse> => {
    const params = {
      ...filters,
      rentPriceFrom: filters.rentPriceFrom ? Number(filters.rentPriceFrom) : undefined,
      rentPriceTo: filters.rentPriceTo ? Number(filters.rentPriceTo) : undefined,
      areaFrom: filters.areaFrom ? Number(filters.areaFrom) : undefined,
      areaTo: filters.areaTo ? Number(filters.areaTo) : undefined,
    };

    const res = await apiClient.get("/client/buildings", { params });
    return res.data.data;
  },

  getBuildingDetail: async (id: number): Promise<ClientBuildingDetail> => {
    const res = await apiClient.get(`/client/buildings/${id}`);
    return res.data.data;
  },
};