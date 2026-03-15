import api from "@/lib/axios";
import type { TransactionFilters, TransactionFormData } from "@/types/admin/transactions";

export const transactionService = {
  search: async (params: TransactionFilters) => {
    const res = await api.get("/transactions", { params });
    console.log(res.data);
    return res.data;
  },

  getDetail: async (id: string | number) => {
    const res = await api.get(`/transactions/${id}`);
    return res.data;
  },

  create: async (payload: TransactionFormData) => {
    const res = await api.post("/transactions/create", {
      ...payload,
      customerId: Number(payload.customerId),
      staffId: Number(payload.staffId),
    });
    return res.data;
  },

  update: async (id: string | number, payload: TransactionFormData) => {
    const res = await api.put(`/transactions/edit/${id}`, {
      ...payload,
      customerId: Number(payload.customerId),
      staffId: Number(payload.staffId),
    });
    return res.data;
  },

  delete: async (id: string | number) => {
    const res = await api.delete(`/transactions/delete/${id}`);
    return res.data;
  },

  updateStatus: async (id: string | number, status: string) => {
    const res = await api.put(`/transactions/${id}/status`, null, {
      params: { status },
    });
    return res.data;
  },
};