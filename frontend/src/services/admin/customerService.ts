import api from "@/lib/axios";
import type { CustomerFilters, CustomerFormData } from "@/types/admin/customers";

export const customerService = {
  search: async (params: CustomerFilters) => {
    const res = await api.get("/customers", { params });
    return res.data;
  },

  getDetail: async (id: string | number) => {
    const res = await api.get(`/customers/${id}`);
    return res.data;
  },

  create: async (payload: CustomerFormData) => {
    const res = await api.post("/customers/create", payload);
    return res.data;
  },

  update: async (id: string | number, payload: CustomerFormData) => {
    const res = await api.put(`/customers/edit/${id}`, payload);
    return res.data;
  },

  delete: async (id: string | number) => {
    const res = await api.delete(`/customers/delete/${id}`);
    return res.data;
  },

  getStaffs: async (id: string | number) => {
    const res = await api.get(`/customers/${id}/staffs`);
    return res.data;
  },

  assign: async (customerId: number, staffs: number[]) => {
    const res = await api.post("/customers/assignment", {
      customerId,
      staffs,
    });
    return res.data;
  },
};