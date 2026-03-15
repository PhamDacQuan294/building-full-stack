import { useState } from "react";
import type { TransactionFormData } from "@/types/admin/transactions";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";

type OptionItem = {
  id: number;
  label: string;
};

type Props = {
  initialValues?: TransactionFormData;
  loading?: boolean;
  submitText?: string;
  customers: OptionItem[];
  staffs: OptionItem[];
  onSubmit: (values: TransactionFormData) => Promise<void>;
};

const defaultValues: TransactionFormData = {
  customerId: "",
  staffId: "",
  transactionType: "TU_VAN",
  transactionStatus: "MOI",
  content: "",
  transactionDate: "",
};

export default function TransactionForm({
  initialValues,
  loading = false,
  submitText = "Lưu",
  customers,
  staffs,
  onSubmit,
}: Props) {
  const [form, setForm] = useState<TransactionFormData>({
    ...defaultValues,
    ...initialValues,
  });

  const handleChange = (key: keyof TransactionFormData, value: string) => {
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
      <select
        value={form.customerId ?? ""}
        onChange={(e) => handleChange("customerId", e.target.value)}
        className="w-full p-3 border rounded-xl"
      >
        <option value="">-- Chọn khách hàng --</option>
        {customers.map((item) => (
          <option key={item.id} value={item.id}>
            {item.label}
          </option>
        ))}
      </select>

      <select
        value={form.staffId ?? ""}
        onChange={(e) => handleChange("staffId", e.target.value)}
        className="w-full p-3 border rounded-xl"
      >
        <option value="">-- Chọn staff --</option>
        {staffs.map((item) => (
          <option key={item.id} value={item.id}>
            {item.label}
          </option>
        ))}
      </select>

      <select
        value={form.transactionType ?? "TU_VAN"}
        onChange={(e) => handleChange("transactionType", e.target.value)}
        className="w-full p-3 border rounded-xl"
      >
        <option value="DAN_DI_XEM">Dẫn đi xem</option>
        <option value="TU_VAN">Tư vấn</option>
        <option value="DAT_COC">Đặt cọc</option>
        <option value="KY_HOP_DONG">Ký hợp đồng</option>
      </select>

      <select
        value={form.transactionStatus ?? "MOI"}
        onChange={(e) => handleChange("transactionStatus", e.target.value)}
        className="w-full p-3 border rounded-xl"
      >
        <option value="MOI">Mới</option>
        <option value="DANG_XU_LY">Đang xử lý</option>
        <option value="HOAN_THANH">Hoàn thành</option>
        <option value="HUY">Hủy</option>
      </select>

      <Input
        type="datetime-local"
        value={form.transactionDate ?? ""}
        onChange={(e) => handleChange("transactionDate", e.target.value)}
      />

      <textarea
        value={form.content ?? ""}
        onChange={(e) => handleChange("content", e.target.value)}
        placeholder="Nội dung giao dịch"
        className="w-full p-3 border rounded-xl"
      />

      <Button type="submit" disabled={loading}>
        {loading ? "Đang lưu..." : submitText}
      </Button>
    </form>
  );
}