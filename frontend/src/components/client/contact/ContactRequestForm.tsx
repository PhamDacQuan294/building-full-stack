import { useState } from "react";
import { useContactRequestStore } from "@/stores/client/useContactRequestStore";

type Props = {
  buildingId?: number;
  buildingName?: string;
};

export default function ContactRequestForm({ buildingId, buildingName }: Props) {
  const { submit, loading, successMessage, errorMessage, clearMessages } =
    useContactRequestStore();

  const [form, setForm] = useState({
    fullName: "",
    phone: "",
    email: "",
    message: "",
  });

  const handleChange = (
    key: "fullName" | "phone" | "email" | "message",
    value: string
  ) => {
    clearMessages();
    setForm((prev) => ({ ...prev, [key]: value }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    const ok = await submit({
      ...form,
      buildingId,
    });

    if (ok) {
      setForm({
        fullName: "",
        phone: "",
        email: "",
        message: "",
      });
    }
  };

  return (
    <div className="p-6 bg-white border shadow-sm rounded-3xl border-slate-200">
      <div className="mb-5">
        <h3 className="text-xl font-bold text-slate-900">Yêu cầu tư vấn</h3>
        <p className="mt-1 text-sm text-slate-500">
          Điền thông tin để được tư vấn nhanh chóng
        </p>
        {buildingName && (
          <p className="mt-2 text-sm font-medium text-violet-600">
            Bất động sản quan tâm: {buildingName}
          </p>
        )}
      </div>

      <form className="space-y-4" onSubmit={handleSubmit}>
        <div>
          <label className="block mb-2 text-sm font-medium text-slate-700">
            Họ tên
          </label>
          <input
            value={form.fullName}
            onChange={(e) => handleChange("fullName", e.target.value)}
            placeholder="Nhập họ tên"
            className="w-full px-4 py-3 border outline-none rounded-2xl border-slate-300 focus:border-violet-500"
          />
        </div>

        <div>
          <label className="block mb-2 text-sm font-medium text-slate-700">
            Số điện thoại
          </label>
          <input
            value={form.phone}
            onChange={(e) => handleChange("phone", e.target.value)}
            placeholder="Nhập số điện thoại"
            className="w-full px-4 py-3 border outline-none rounded-2xl border-slate-300 focus:border-violet-500"
          />
        </div>

        <div>
          <label className="block mb-2 text-sm font-medium text-slate-700">
            Email
          </label>
          <input
            value={form.email}
            onChange={(e) => handleChange("email", e.target.value)}
            placeholder="Nhập email"
            className="w-full px-4 py-3 border outline-none rounded-2xl border-slate-300 focus:border-violet-500"
          />
        </div>

        <div>
          <label className="block mb-2 text-sm font-medium text-slate-700">
            Nhu cầu / nội dung
          </label>
          <textarea
            rows={5}
            value={form.message}
            onChange={(e) => handleChange("message", e.target.value)}
            placeholder="Ví dụ: Tôi muốn xem mặt bằng vào cuối tuần này..."
            className="w-full px-4 py-3 border outline-none rounded-2xl border-slate-300 focus:border-violet-500"
          />
        </div>

        {successMessage && (
          <div className="px-4 py-3 text-sm text-green-700 rounded-2xl bg-green-50">
            {successMessage}
          </div>
        )}

        {errorMessage && (
          <div className="px-4 py-3 text-sm text-red-700 rounded-2xl bg-red-50">
            {errorMessage}
          </div>
        )}

        <button
          type="submit"
          disabled={loading}
          className="px-6 py-3 font-medium text-white rounded-2xl bg-violet-600 hover:bg-violet-700 disabled:opacity-50"
        >
          {loading ? "Đang gửi..." : "Gửi yêu cầu"}
        </button>
      </form>
    </div>
  );
}