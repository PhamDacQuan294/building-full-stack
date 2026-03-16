import { create } from "zustand";
import { authService } from "@/services/client/authService";
import { removeClientToken, setClientToken } from "@/utils/clientAuth";
import type {
  ClientChangePasswordPayload,
  ClientForgotPasswordPayload,
  ClientLoginPayload,
  ClientProfile,
  ClientRegisterPayload,
  ClientResetPasswordPayload,
  ClientUpdateProfilePayload,
} from "@/types/client/auth";

type Store = {
  user: ClientProfile | null;
  loading: boolean;
  errorMessage: string;
  successMessage: string;

  register: (payload: ClientRegisterPayload) => Promise<boolean>;
  login: (payload: ClientLoginPayload) => Promise<boolean>;
  logout: () => void;
  fetchMe: () => Promise<void>;
  updateProfile: (payload: ClientUpdateProfilePayload) => Promise<boolean>;
  changePassword: (payload: ClientChangePasswordPayload) => Promise<boolean>;
  forgotPassword: (payload: ClientForgotPasswordPayload) => Promise<boolean>;
  resetPassword: (payload: ClientResetPasswordPayload) => Promise<boolean>;
  clearMessages: () => void;
};

export const useClientAuthStore = create<Store>((set) => ({
  user: null,
  loading: false,
  errorMessage: "",
  successMessage: "",

  register: async (payload) => {
    set({ loading: true, errorMessage: "", successMessage: "" });
    try {
      await authService.register(payload);
      set({ successMessage: "Đăng ký thành công. Bạn có thể đăng nhập ngay." });
      return true;
    } catch (error: any) {
      set({ errorMessage: error?.response?.data?.detail || "Đăng ký thất bại" });
      return false;
    } finally {
      set({ loading: false });
    }
  },

  login: async (payload) => {
    set({ loading: true, errorMessage: "", successMessage: "" });
    try {
      const data = await authService.login(payload);
      setClientToken(data.token);
      set({ user: data.user, successMessage: "Đăng nhập thành công" });
      return true;
    } catch (error: any) {
      set({ errorMessage: error?.response?.data?.detail || "Đăng nhập thất bại" });
      return false;
    } finally {
      set({ loading: false });
    }
  },

  logout: () => {
    removeClientToken();
    set({ user: null, successMessage: "", errorMessage: "" });
  },

  fetchMe: async () => {
    try {
      const data = await authService.getMe();
      set({ user: data });
    } catch {
      removeClientToken();
      set({ user: null });
    }
  },

  updateProfile: async (payload) => {
    set({ loading: true, errorMessage: "", successMessage: "" });
    try {
      const data = await authService.updateProfile(payload);
      set({ user: data, successMessage: "Cập nhật hồ sơ thành công" });
      return true;
    } catch (error: any) {
      set({ errorMessage: error?.response?.data?.detail || "Cập nhật thất bại" });
      return false;
    } finally {
      set({ loading: false });
    }
  },

  changePassword: async (payload) => {
    set({ loading: true, errorMessage: "", successMessage: "" });
    try {
      await authService.changePassword(payload);
      set({ successMessage: "Đổi mật khẩu thành công" });
      return true;
    } catch (error: any) {
      set({ errorMessage: error?.response?.data?.detail || "Đổi mật khẩu thất bại" });
      return false;
    } finally {
      set({ loading: false });
    }
  },

  forgotPassword: async (payload) => {
    set({ loading: true, errorMessage: "", successMessage: "" });
    try {
      await authService.forgotPassword(payload);
      set({ successMessage: "OTP đã được gửi. Kiểm tra console backend hoặc email." });
      return true;
    } catch (error: any) {
      set({ errorMessage: error?.response?.data?.detail || "Gửi OTP thất bại" });
      return false;
    } finally {
      set({ loading: false });
    }
  },

  resetPassword: async (payload) => {
    set({ loading: true, errorMessage: "", successMessage: "" });
    try {
      await authService.resetPassword(payload);
      set({ successMessage: "Đặt lại mật khẩu thành công" });
      return true;
    } catch (error: any) {
      set({ errorMessage: error?.response?.data?.detail || "Đặt lại mật khẩu thất bại" });
      return false;
    } finally {
      set({ loading: false });
    }
  },

  clearMessages: () => set({ errorMessage: "", successMessage: "" }),
}));