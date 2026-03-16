// import apiClient from "@/lib/axiosClient";
import apiClient from "@/lib/apiClient";
import type { HomeSearchFilters } from "@/types/client/home";

export const homeService = {
  getHomePageData: async () => {
    const res = await apiClient.get("/client/home");
    return res.data;
  },

  searchBuildings: async (params: HomeSearchFilters) => {
    const payload = {
      ...params,
      rentPriceFrom: params.rentPriceFrom ? Number(params.rentPriceFrom) : undefined,
      rentPriceTo: params.rentPriceTo ? Number(params.rentPriceTo) : undefined,
    };

    const res = await apiClient.get("/client/home/search", { params: payload });
    return res.data;
  },
};