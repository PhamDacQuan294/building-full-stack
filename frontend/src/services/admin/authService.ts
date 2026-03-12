import api from "@/lib/axios";
import type { LoginPayload } from "@/types/admin/auth";

export const authService = {
  login: async (payload: LoginPayload): Promise<string> => {
    const res = await api.post("/users/login", payload);
    return res.data;
  },
};