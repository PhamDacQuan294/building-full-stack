import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "sonner";
import UserForm from "./UserForm";
import { userService } from "@/services/admin/userService";
import { roleService } from "@/services/admin/roleService";
import type { CreateUserPayload } from "@/types/admin/users";

type RoleOption = {
  id: number;
  name: string;
};

export default function CreateUser() {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [roles, setRoles] = useState<RoleOption[]>([]);

  useEffect(() => {
    const fetchRoles = async () => {
      try {
        const res = await roleService.getRoles();
        setRoles(res.data || []);
      } catch (error) {
        console.error(error);
        toast.error("Không tải được danh sách nhóm quyền");
      }
    };

    fetchRoles();
  }, []);

  const handleCreate = async (values: CreateUserPayload) => {
    try {
      setLoading(true);
      await userService.createUser(values);

      toast.success("Thêm người dùng thành công");
      navigate("/admin/users");
    } catch (error) {
      console.error(error);
      toast.error("Thêm người dùng thất bại");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="p-6 bg-white border rounded-xl border-border">
      <h1 className="mb-6 text-xl font-semibold">Thêm người dùng</h1>
      <UserForm roles={roles} onSubmit={handleCreate} loading={loading} />
    </div>
  );
}