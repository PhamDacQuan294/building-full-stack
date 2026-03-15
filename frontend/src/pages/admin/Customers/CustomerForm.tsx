import { useState } from "react";
import type { CustomerFormData } from "@/types/admin/customers";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";

type Props = {
  initialValues?: CustomerFormData;
  loading?: boolean;
  submitText?: string;
  onSubmit: (values: CustomerFormData) => Promise<void>;
};

const defaultValues: CustomerFormData = {
  fullName: "",
  phone: "",
  email: "",
  companyName: "",
  demand: "",
  status: "MOI",
  note: "",
};

export default function CustomerForm({
  initialValues,
  loading = false,
  submitText = "Lưu",
  onSubmit,
}: Props) {
  const [form, setForm] = useState<CustomerFormData>(initialValues || defaultValues);

  const handleChange = (key: keyof CustomerFormData, value: string) => {
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
    <form onSubmit={handleSubmit} className="space-y-4">
      <Input
        value={form.fullName ?? ""}
        onChange={(e) => handleChange("fullName", e.target.value)}
        placeholder="Họ tên"
      />

      <Input
        value={form.phone ?? ""}
        onChange={(e) => handleChange("phone", e.target.value)}
        placeholder="Số điện thoại"
      />

      <Input
        value={form.email ?? ""}
        onChange={(e) => handleChange("email", e.target.value)}
        placeholder="Email"
      />

      <Input
        value={form.companyName ?? ""}
        onChange={(e) => handleChange("companyName", e.target.value)}
        placeholder="Công ty"
      />

      <textarea
        value={form.demand ?? ""}
        onChange={(e) => handleChange("demand", e.target.value)}
        placeholder="Nhu cầu"
        className="w-full p-3 border rounded-xl"
      />

      <select
        value={form.status ?? "MOI"}
        onChange={(e) => handleChange("status", e.target.value)}
        className="w-full p-3 border rounded-xl"
      >
        <option value="MOI">Mới</option>
        <option value="DANG_CHAM_SOC">Đang chăm sóc</option>
        <option value="DA_CHOT">Đã chốt</option>
        <option value="NGUNG_THEO_DOI">Ngừng theo dõi</option>
      </select>

      <textarea
        value={form.note ?? ""}
        onChange={(e) => handleChange("note", e.target.value)}
        placeholder="Ghi chú"
        className="w-full p-3 border rounded-xl"
      />

      <Button type="submit" disabled={loading}>
        {loading ? "Đang lưu..." : submitText}
      </Button>
    </form>
  );
}