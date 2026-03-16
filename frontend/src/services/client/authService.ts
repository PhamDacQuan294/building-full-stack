import apiClient from "@/lib/axiosClient";
import type {
  ClientChangePasswordPayload,
  ClientForgotPasswordPayload,
  ClientLoginPayload,
  ClientLoginResponse,
  ClientProfile,
  ClientRegisterPayload,
  ClientResetPasswordPayload,
  ClientUpdateProfilePayload,
} from "@/types/client/auth";

export const authService = {
  register: async (payload: ClientRegisterPayload): Promise<ClientProfile> => {
    const res = await apiClient.post("/client/auth/register", payload);
    return res.data.data;
  },

  login: async (payload: ClientLoginPayload): Promise<ClientLoginResponse> => {
    const res = await apiClient.post("/client/auth/login", payload);
    return res.data.data;
  },

  getMe: async (): Promise<ClientProfile> => {
    const res = await apiClient.get("/client/auth/me");
    return res.data.data;
  },

  updateProfile: async (payload: ClientUpdateProfilePayload): Promise<ClientProfile> => {
    const res = await apiClient.put("/client/auth/me", payload);
    return res.data.data;
  },

  changePassword: async (payload: ClientChangePasswordPayload): Promise<void> => {
    await apiClient.put("/client/auth/change-password", payload);
  },

  forgotPassword: async (payload: ClientForgotPasswordPayload): Promise<void> => {
    await apiClient.post("/client/auth/forgot-password", payload);
  },

  resetPassword: async (payload: ClientResetPasswordPayload): Promise<void> => {
    await apiClient.post("/client/auth/reset-password", payload);
  },
};