import { useState } from "react";
import type { RoleFormData } from "@/types/admin/roles";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";

type RoleFormProps = {
  initialValues?: RoleFormData;
  onSubmit: (values: RoleFormData) => Promise<void>;
  loading?: boolean;
  submitText?: string;
};

const defaultValues: RoleFormData = {
  name: "",
  code: "",
};

export default function RoleForm({
  initialValues,
  onSubmit,
  loading = false,
  submitText = "Lưu",
}: RoleFormProps) {
  const [form, setForm] = useState<RoleFormData>(
    initialValues ?? defaultValues
  );

  const handleChange = (key: keyof RoleFormData, value: string) => {
    setForm((prev) => ({
      ...prev,
      [key]: value,
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    await onSubmit(form);
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-5">
      <div className="grid grid-cols-1 gap-4 md:grid-cols-2">
        <div>
          <Label>Tên nhóm quyền</Label>
          <Input
            value={form.name}
            onChange={(e) => handleChange("name", e.target.value)}
            placeholder="Nhập tên nhóm quyền"
          />
        </div>

        <div>
          <Label>Mã nhóm quyền</Label>
          <Input
            value={form.code}
            onChange={(e) => handleChange("code", e.target.value)}
            placeholder="VD: ADMIN"
          />
        </div>
      </div>

      <Button type="submit" disabled={loading}>
        {submitText}
      </Button>
    </form>
  );
}