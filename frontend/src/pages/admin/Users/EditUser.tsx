import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { userService } from "@/services/admin/userService";
import UserForm from "./UserForm";
import { toast } from "sonner";

type RoleItem = {
  id: number;
  name: string;
};

export default function EditUser() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [roles, setRoles] = useState<RoleItem[]>([]);
  const [initialValues, setInitialValues] = useState<any>();

  useEffect(() => {
    const fetchData = async () => {
      try {
        const roleRes = await userService.getRoles();
        setRoles(roleRes.data || []);

        const detailRes = await userService.getDetail(id!);
        const item = detailRes.data;

        setInitialValues({
          fullName: item.fullName || "",
          email: item.email || "",
          phone: item.phone || "",
          username: item.username || "",
          avatar: item.avatar || "",
          roleIds: item.roleIds || [],
        });
      } catch (error) {
        console.error(error);
        toast.error("Tải dữ liệu user thất bại");
      }
    };

    fetchData();
  }, [id]);

  const handleSubmit = async (values: any) => {
    try {
      await userService.update(id!, values);
      toast.success("Cập nhật user thành công");
      navigate("/admin/users");
    } catch (error) {
      console.error(error);
      toast.error("Cập nhật user thất bại");
    }
  };

  if (!initialValues) {
    return <div>Đang tải...</div>;
  }

  return (
    <div className="space-y-4">
      <h1 className="text-2xl font-bold">Chỉnh sửa người dùng</h1>
      <UserForm
        roles={roles}
        initialValues={initialValues}
        submitText="Cập nhật"
        onSubmit={handleSubmit}
      />
    </div>
  );
}