import { create } from "zustand";
import { contactRequestService } from "@/services/client/contactRequestService";

type ContactRequestStore = {
  loading: boolean;
  successMessage: string;
  errorMessage: string;
  submit: (payload: {
    fullName: string;
    phone: string;
    email: string;
    message: string;
    buildingId?: number;
  }) => Promise<boolean>;
  clearMessages: () => void;
};

export const useContactRequestStore = create<ContactRequestStore>((set) => ({
  loading: false,
  successMessage: "",
  errorMessage: "",

  submit: async (payload) => {
    set({ loading: true, successMessage: "", errorMessage: "" });

    try {
      await contactRequestService.create(payload);
      set({
        successMessage: "Gửi yêu cầu thành công. Chúng tôi sẽ liên hệ với bạn sớm.",
      });
      return true;
    } catch (error: any) {
      set({
        errorMessage:
          error?.response?.data?.detail || "Có lỗi xảy ra khi gửi yêu cầu.",
      });
      return false;
    } finally {
      set({ loading: false });
    }
  },

  clearMessages: () => set({ successMessage: "", errorMessage: "" }),
}));