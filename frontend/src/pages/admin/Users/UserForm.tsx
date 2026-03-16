import { useState } from "react";
import { Button } from "@/components/ui/button";

type RoleItem = {
  id: number;
  name: string;
};

type UserFormValues = {
  fullName: string;
  email: string;
  phone: string;
  username: string;
  password?: string;
  avatar: string;
  roleIds: number[];
};

type Props = {
  initialValues?: UserFormValues;
  roles: RoleItem[];
  loading?: boolean;
  submitText?: string;
  showPassword?: boolean;
  onSubmit: (values: UserFormValues) => Promise<void>;
};

const defaultValues: UserFormValues = {
  fullName: "",
  email: "",
  phone: "",
  username: "",
  password: "",
  avatar: "",
  roleIds: [],
};

export default function UserForm({
  initialValues,
  roles,
  loading = false,
  submitText = "Lưu",
  showPassword = false,
  onSubmit,
}: Props) {
  const [form, setForm] = useState<UserFormValues>({
    ...defaultValues,
    ...initialValues,
  });

  const handleChange = (key: keyof UserFormValues, value: string) => {
    setForm((prev) => ({
      ...prev,
      [key]: value,
    }));
  };

  const handleRoleChange = (roleId: number) => {
    setForm((prev) => {
      const exists = prev.roleIds.includes(roleId);

      return {
        ...prev,
        roleIds: exists
          ? prev.roleIds.filter((id) => id !== roleId)
          : [...prev.roleIds, roleId],
      };
    });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    await onSubmit(form);
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <input
        value={form.fullName}
        onChange={(e) => handleChange("fullName", e.target.value)}
        placeholder="Họ tên"
        className="w-full p-3 border rounded-xl"
      />

      <input
        value={form.email}
        onChange={(e) => handleChange("email", e.target.value)}
        placeholder="Email"
        className="w-full p-3 border rounded-xl"
      />

      <input
        value={form.phone}
        onChange={(e) => handleChange("phone", e.target.value)}
        placeholder="Số điện thoại"
        className="w-full p-3 border rounded-xl"
      />

      <input
        value={form.username}
        onChange={(e) => handleChange("username", e.target.value)}
        placeholder="Username"
        className="w-full p-3 border rounded-xl"
      />

      {showPassword && (
        <input
          type="password"
          value={form.password ?? ""}
          onChange={(e) => handleChange("password", e.target.value)}
          placeholder="Mật khẩu"
          className="w-full p-3 border rounded-xl"
        />
      )}

      <input
        value={form.avatar}
        onChange={(e) => handleChange("avatar", e.target.value)}
        placeholder="Avatar URL"
        className="w-full p-3 border rounded-xl"
      />

      <div className="space-y-2">
        <div className="font-medium">Nhóm quyền</div>
        {roles.map((role) => (
          <label key={role.id} className="flex items-center gap-2">
            <input
              type="checkbox"
              checked={form.roleIds.includes(role.id)}
              onChange={() => handleRoleChange(role.id)}
            />
            <span>{role.name}</span>
          </label>
        ))}
      </div>

      <Button type="submit" disabled={loading}>
        {loading ? "Đang lưu..." : submitText}
      </Button>
    </form>
  );
}