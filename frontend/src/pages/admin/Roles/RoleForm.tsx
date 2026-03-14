import type { PermissionItem, RoleFormData } from "@/types/admin/roles";
import { useEffect, useState } from "react";

type RoleFormProps = {
  initialValues?: RoleFormData;
  permissions: PermissionItem[];
  loading?: boolean;
  submitText?: string;
  onSubmit: (values: RoleFormData) => Promise<void>;
};

const defaultValues: RoleFormData = {
  code: "",
  name: "",
  description: "",
  permissionIds: [],
};

export default function RoleForm({
  initialValues,
  permissions,
  loading = false,
  submitText = "Lưu",
  onSubmit,
}: RoleFormProps) {
  const [form, setForm] = useState<RoleFormData>(defaultValues);

  useEffect(() => {
    if (initialValues) {
      setForm(initialValues);
    }
  }, [initialValues]);

  const handleChange = (key: keyof RoleFormData, value: string | number[]) => {
    setForm((prev) => ({
      ...prev,
      [key]: value,
    }));
  };

  const handleTogglePermission = (permissionId: number) => {
    const exists = form.permissionIds.includes(permissionId);

    if (exists) {
      handleChange(
        "permissionIds",
        form.permissionIds.filter((id) => id !== permissionId)
      );
    } else {
      handleChange("permissionIds", [...form.permissionIds, permissionId]);
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    await onSubmit(form);
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-5">
      <div>
        <label className="block mb-1 text-sm font-medium">Mã nhóm quyền</label>
        <input
          value={form.code}
          onChange={(e) => handleChange("code", e.target.value)}
          className="w-full px-3 py-2 border rounded-xl"
          placeholder="Ví dụ: ADMIN"
        />
      </div>

      <div>
        <label className="block mb-1 text-sm font-medium">Tên nhóm quyền</label>
        <input
          value={form.name}
          onChange={(e) => handleChange("name", e.target.value)}
          className="w-full px-3 py-2 border rounded-xl"
          placeholder="Ví dụ: Quản trị viên"
        />
      </div>

      <div>
        <label className="block mb-1 text-sm font-medium">Mô tả</label>
        <textarea
          value={form.description}
          onChange={(e) => handleChange("description", e.target.value)}
          className="w-full px-3 py-2 border rounded-xl"
          placeholder="Nhập mô tả nhóm quyền"
        />
      </div>

      <div>
        <label className="block mb-3 text-sm font-medium">Quyền thao tác</label>

        <div className="grid grid-cols-2 gap-3">
          {permissions.map((permission) => (
            <label
              key={permission.id}
              className="flex items-start gap-2 p-3 border rounded-xl"
            >
              <input
                type="checkbox"
                checked={form.permissionIds.includes(permission.id)}
                onChange={() => handleTogglePermission(permission.id)}
                className="mt-1"
              />
              <div>
                <div className="font-medium">{permission.name}</div>
                <div className="text-sm text-slate-500">{permission.code}</div>
              </div>
            </label>
          ))}
        </div>
      </div>

      <button
        type="submit"
        disabled={loading}
        className="px-4 py-2 text-white bg-violet-600 rounded-xl"
      >
        {loading ? "Đang lưu..." : submitText}
      </button>
    </form>
  );
}