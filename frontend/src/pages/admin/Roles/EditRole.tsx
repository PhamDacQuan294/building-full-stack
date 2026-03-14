import RoleForm from "./RoleForm";
import { roleService } from "@/services/admin/roleService";
import type { PermissionItem, RoleFormData } from "@/types/admin/roles";
import { useEffect, useState } from "react";
import { toast } from "sonner";
import { useNavigate, useParams } from "react-router-dom";

export default function EditRole() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [permissions, setPermissions] = useState<PermissionItem[]>([]);
  const [initialValues, setInitialValues] = useState<RoleFormData | undefined>();
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const fetchData = async () => {
      try {
        if (!id) return;

        const [roleRes, permissionRes] = await Promise.all([
          roleService.getRoleDetail(id),
          roleService.getPermissions(),
        ]);

        setPermissions(permissionRes.data || []);

        setInitialValues({
          code: roleRes.data.code,
          name: roleRes.data.name,
          description: roleRes.data.description || "",
          permissionIds: roleRes.data.permissions.map((item) => item.id),
        });
      } catch (error) {
        console.error(error);
        toast.error("Không tải được dữ liệu nhóm quyền");
      }
    };

    fetchData();
  }, [id]);

  const handleSubmit = async (values: RoleFormData) => {
    try {
      if (!id) return;

      setLoading(true);
      await roleService.updateRole(id, values);

      toast.success("Cập nhật nhóm quyền thành công");
      navigate("/admin/roles");
    } catch (error) {
      console.error(error);
      toast.error("Cập nhật nhóm quyền thất bại");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="p-6 bg-white border rounded-xl">
      <h1 className="mb-6 text-xl font-semibold">Cập nhật nhóm quyền</h1>

      {initialValues && (
        <RoleForm
          initialValues={initialValues}
          permissions={permissions}
          loading={loading}
          submitText="Cập nhật"
          onSubmit={handleSubmit}
        />
      )}
    </div>
  );
}