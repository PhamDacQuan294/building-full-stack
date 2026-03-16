// import api from "@/lib/axios";
// import type { CreateUserPayload, UserResponse } from "@/types/admin/users";

// export const userService = {
//   getUsers: async (): Promise<UserResponse> => {
//     const res = await api.get("/users");
//     return res.data;
//   },

//   createUser: async (payload: CreateUserPayload) => {
//     const res = await api.post("/users/create", payload);
//     return res.data;
//   },
// };

import api from "@/lib/axios";
import type {
  CreateUserPayload,
  UpdateUserPayload,
  UserFilters,
} from "@/types/admin/users";

export const userService = {
  search: async (params: UserFilters) => {
    const res = await api.get("/users2", { params });
    return res.data;
  },

  getDetail: async (id: string | number) => {
    const res = await api.get(`/users2/${id}`);
    return res.data;
  },

  create: async (payload: CreateUserPayload) => {
    const res = await api.post("/users2/create", payload);
    return res.data;
  },

  update: async (id: string | number, payload: UpdateUserPayload) => {
    const res = await api.put(`/users2/edit/${id}`, payload);
    return res.data;
  },

  changeStatus: async (id: string | number, status: string) => {
    const res = await api.put(`/users2/${id}/status`, { status });
    return res.data;
  },

  resetPassword: async (id: string | number, newPassword: string) => {
    const res = await api.put(`/users2/${id}/reset-password`, { newPassword });
    return res.data;
  },

  getRoles: async () => {
    const res = await api.get("/roles");
    return res.data;
  },
};