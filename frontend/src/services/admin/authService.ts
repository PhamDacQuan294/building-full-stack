import api from "@/lib/axios";
import type { LoginPayload } from "@/types/admin/auth";

export const authService = {
  login: async (payload: LoginPayload): Promise<string> => {
    const res = await api.post("/users/login", payload);
    return res.data;
  },

  forgotPassword: async (email: string) => {
    const res = await api.post("/password/forgot", { email });
    return res.data;
  },

  verifyOtp: async (email: string, otp: string) => {
    const res = await api.post("/password/verify-otp", { email, otp });
    return res.data;
  },

  resetPassword: async (email: string, otp: string, newPassword: string) => {
    const res = await api.post("/password/reset", {
      email,
      otp,
      newPassword,
    });
    return res.data;
  },
};
