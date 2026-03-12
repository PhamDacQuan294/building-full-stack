import { create } from "zustand";
import { toast } from "sonner";
import { roleService } from "@/services/admin/roleService";
import type { RoleItem } from "@/types/admin/roles";

type RoleState = {
  items: RoleItem[];
  loading: boolean;
  fetchRoles: () => Promise<void>;
};

export const useRoleStore = create<RoleState>((set) => ({
  items: [],
  loading: false,

  fetchRoles: async () => {
    try {
      set({ loading: true });

      const res = await roleService.getRoles();

      set({
        items: res.data || [],
      });
    } catch (error) {
      console.error(error);
      toast.error("Không tải được danh sách nhóm quyền!");
    } finally {
      set({ loading: false });
    }
  },
}));