import api from "@/lib/axios";
import type { CreateUserPayload, UserResponse } from "@/types/admin/users";

export const userService = {
  getUsers: async (): Promise<UserResponse> => {
    const res = await api.get("/users");
    return res.data;
  },

  createUser: async (payload: CreateUserPayload) => {
    const res = await api.post("/users/create", payload);
    return res.data;
  },
};