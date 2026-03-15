import api from "@/lib/axios";
import type {
  ChangePasswordPayload,
  UpdateProfilePayload,
} from "@/types/admin/profile";

export const profileService = {
  getMyProfile: async () => {
    const res = await api.get("/profile");
    return res.data;
  },

  updateMyProfile: async (payload: UpdateProfilePayload) => {
    const res = await api.put("/profile", payload);
    return res.data;
  },

  changePassword: async (payload: ChangePasswordPayload) => {
    const res = await api.put("/profile/change-password", payload);
    return res.data;
  },
};