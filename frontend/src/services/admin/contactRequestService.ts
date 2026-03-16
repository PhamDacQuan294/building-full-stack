import api from "@/lib/axios";
import type { AdminContactRequestItem } from "@/types/admin/contactRequest";

export const adminContactRequestService = {
  findAll: async (): Promise<AdminContactRequestItem[]> => {
    const res = await api.get("/contact-requests");
    return res.data.data;
  },

  updateStatus: async (id: number, status: string): Promise<void> => {
    await api.put(`/contact-requests/${id}/status`, { status });
  },
};