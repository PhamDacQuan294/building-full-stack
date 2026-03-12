import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "sonner";
import RoleForm from "./RoleForm";
import { roleService } from "@/services/admin/roleService";
import type { RoleFormData } from "@/types/admin/roles";

export default function CreateRole() {
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleCreate = async (values: RoleFormData) => {
    try {
      setLoading(true);

      await roleService.createRole(values);

      toast.success("Thêm mới nhóm quyền thành công!");
      navigate("/admin/roles");
    } catch (error) {
      console.error(error);
      toast.error("Thêm mới nhóm quyền thất bại!");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="p-6 border rounded-xl border-border bg-background">
      <h1 className="mb-6 text-xl font-semibold">Thêm mới nhóm quyền</h1>
      <RoleForm onSubmit={handleCreate} loading={loading} />
    </div>
  );
}