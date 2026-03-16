import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { userService } from "@/services/admin/userService";
import UserForm from "./UserForm";
import { toast } from "sonner";

type RoleItem = {
  id: number;
  name: string;
};

export default function CreateUser() {
  const navigate = useNavigate();
  const [roles, setRoles] = useState<RoleItem[]>([]);

  useEffect(() => {
    const fetchRoles = async () => {
      try {
        const res = await userService.getRoles();
        setRoles(res.data || []);
      } catch (error) {
        console.error(error);
      }
    };

    fetchRoles();
  }, []);

  const handleSubmit = async (values: any) => {
    try {
      await userService.create(values);
      toast.success("Tạo user thành công");
      navigate("/admin/users");
    } catch (error) {
      console.error(error);
      toast.error("Tạo user thất bại");
    }
  };

  return (
    <div className="space-y-4">
      <h1 className="text-2xl font-bold">Thêm người dùng</h1>
      <UserForm
        roles={roles}
        showPassword
        submitText="Tạo mới"
        onSubmit={handleSubmit}
      />
    </div>
  );
}