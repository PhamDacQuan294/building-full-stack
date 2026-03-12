import { useState } from "react";
import type { CreateUserPayload } from "@/types/admin/users";

type RoleOption = {
  id: number;
  name: string;
};

type UserFormProps = {
  roles: RoleOption[];
  onSubmit: (values: CreateUserPayload) => Promise<void>;
  loading?: boolean;
};

const defaultValues: CreateUserPayload = {
  userName: "",
  fullName: "",
  email: "",
  phone: "",
  password: "",
  status: "ACTIVE",
  roleIds: [],
};

export default function UserForm({
  roles,
  onSubmit,
  loading = false,
}: UserFormProps) {
  const [form, setForm] = useState<CreateUserPayload>(defaultValues);

  const handleChange = (
    key: keyof CreateUserPayload,
    value: string | number[],
  ) => {
    setForm((prev) => ({
      ...prev,
      [key]: value,
    }));
  };

  const handleRoleChange = (roleId: number, checked: boolean) => {
    if (checked) {
      setForm((prev) => ({
        ...prev,
        roleIds: [...prev.roleIds, roleId],
      }));
    } else {
      setForm((prev) => ({
        ...prev,
        roleIds: prev.roleIds.filter((id) => id !== roleId),
      }));
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    await onSubmit(form);
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-5">
      <div>
        <label>Tên đăng nhập</label>
        <input
          value={form.userName}
          onChange={(e) => handleChange("userName", e.target.value)}
          className="w-full px-3 py-2 border rounded"
          placeholder="Nhập tên đăng nhập"
        />
      </div>
      
      <div>
        <label>Họ tên</label>
        <input
          value={form.fullName}
          onChange={(e) => handleChange("fullName", e.target.value)}
          className="w-full px-3 py-2 border rounded"
          placeholder="Nhập họ tên"
        />
      </div>

      <div>
        <label>Email</label>
        <input
          value={form.email}
          onChange={(e) => handleChange("email", e.target.value)}
          className="w-full px-3 py-2 border rounded"
          placeholder="Nhập email"
        />
      </div>

      <div>
        <label>Số điện thoại</label>
        <input
          value={form.phone}
          onChange={(e) => handleChange("phone", e.target.value)}
          className="w-full px-3 py-2 border rounded"
          placeholder="Nhập số điện thoại"
        />
      </div>

      <div>
        <label>Mật khẩu</label>
        <input
          type="password"
          value={form.password}
          onChange={(e) => handleChange("password", e.target.value)}
          className="w-full px-3 py-2 border rounded"
          placeholder="Nhập mật khẩu"
        />
      </div>

      <div>
        <label>Trạng thái</label>
        <select
          value={form.status}
          onChange={(e) =>
            handleChange("status", e.target.value as "ACTIVE" | "INACTIVE")
          }
          className="w-full px-3 py-2 border rounded"
        >
          <option value="ACTIVE">Hoạt động</option>
          <option value="INACTIVE">Ngưng hoạt động</option>
        </select>
      </div>

      <div>
        <label>Nhóm quyền</label>
        <div className="space-y-2">
          {roles.map((role) => (
            <label key={role.id} className="flex items-center gap-2">
              <input
                type="checkbox"
                checked={form.roleIds.includes(role.id)}
                onChange={(e) => handleRoleChange(role.id, e.target.checked)}
              />
              {role.name}
            </label>
          ))}
        </div>
      </div>

      <button
        type="submit"
        disabled={loading}
        className="px-4 py-2 text-white rounded bg-violet-600"
      >
        {loading ? "Đang lưu..." : "Thêm người dùng"}
      </button>
    </form>
  );
}
