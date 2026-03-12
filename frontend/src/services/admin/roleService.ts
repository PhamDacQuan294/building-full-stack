import api from "@/lib/axios";
import type { RoleDetailResponse, RoleFormData, RoleResponse } from "@/types/admin/roles";

export const roleService = {
  getRoles: async (): Promise<RoleResponse> => {
    const res = await api.get("/roles");
    return res.data;
  },

  createRole: async (payload: RoleFormData) => {
    const res = await api.post("/roles/create", payload);
    return res.data;
  },

  getRoleDetail: async (id: string | number): Promise<RoleDetailResponse> => {
    const res = await api.get(`/roles/detail/${id}`);
    return res.data;
  },

  updateRole: async (id: string | number, payload: RoleFormData) => {
    const res = await api.put(`/roles/edit/${id}`, payload);
    return res.data;
  },
};