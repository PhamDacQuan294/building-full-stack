import RoleForm from "./RoleForm";
import { roleService } from "@/services/admin/roleService";
import type { PermissionItem, RoleFormData } from "@/types/admin/roles";
import { useEffect, useState } from "react";
import { toast } from "sonner";
import { useNavigate } from "react-router-dom";

export default function CreateRole() {
  const [permissions, setPermissions] = useState<PermissionItem[]>([]);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchPermissions = async () => {
      try {
        const res = await roleService.getPermissions();
        setPermissions(res.data || []);
      } catch (error) {
        console.error(error);
        toast.error("Không tải được danh sách quyền thao tác");
      }
    };

    fetchPermissions();
  }, []);

  const handleSubmit = async (values: RoleFormData) => {
    try {
      setLoading(true);
      await roleService.createRole(values);

      toast.success("Thêm nhóm quyền thành công");
      navigate("/admin/roles");
    } catch (error) {
      console.error(error);
      toast.error("Thêm nhóm quyền thất bại");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="p-6 bg-white border rounded-xl">
      <h1 className="mb-6 text-xl font-semibold">Thêm nhóm quyền</h1>

      <RoleForm
        permissions={permissions}
        loading={loading}
        submitText="Thêm mới"
        onSubmit={handleSubmit}
      />
    </div>
  );
}