import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { toast } from "sonner";
import RoleForm from "./RoleForm";
import { roleService } from "@/services/admin/roleService";
import type { RoleFormData } from "@/types/admin/roles";

export default function EditRole() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [loading, setLoading] = useState(false);
  const [initialValues, setInitialValues] = useState<RoleFormData | null>(null);

  useEffect(() => {
    const fetchDetail = async () => {
      try {
        if (!id) return;

        setLoading(true);
        const res = await roleService.getRoleDetail(id);

        setInitialValues({
          name: res.data.name ?? "",
          code: res.data.code ?? "",
        });
      } catch (error) {
        console.error(error);
        toast.error("Không tải được chi tiết nhóm quyền!");
      } finally {
        setLoading(false);
      }
    };

    fetchDetail();
  }, [id]);

  const handleUpdate = async (values: RoleFormData) => {
    try {
      if (!id) return;

      setLoading(true);
      await roleService.updateRole(id, values);

      toast.success("Cập nhật nhóm quyền thành công!");
      navigate("/admin/roles");
    } catch (error) {
      console.error(error);
      toast.error("Cập nhật nhóm quyền thất bại!");
    } finally {
      setLoading(false);
    }
  };

  if (loading && !initialValues) {
    return <div>Đang tải dữ liệu...</div>;
  }

  if (!initialValues) {
    return <div>Không có dữ liệu</div>;
  }

  return (
    <div className="p-6 border rounded-xl border-border bg-background">
      <h1 className="mb-6 text-xl font-semibold">Chỉnh sửa nhóm quyền</h1>
      <RoleForm
        key={id}
        initialValues={initialValues}
        onSubmit={handleUpdate}
        loading={loading}
        submitText="Cập nhật"
      />
    </div>
  );
}