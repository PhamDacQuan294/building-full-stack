import { create } from "zustand";
import { buildingService } from "@/services/client/buildingService";
import type { ClientBuildingDetail } from "@/types/client/building";

type BuildingDetailStore = {
  item: ClientBuildingDetail | null;
  loading: boolean;
  fetchDetail: (id: number) => Promise<void>;
};

export const useBuildingDetailStore = create<BuildingDetailStore>((set) => ({
  item: null,
  loading: false,

  fetchDetail: async (id: number) => {
    set({ loading: true });
    try {
      const data = await buildingService.getBuildingDetail(id);
      set({ item: data });
    } finally {
      set({ loading: false });
    }
  },
}));