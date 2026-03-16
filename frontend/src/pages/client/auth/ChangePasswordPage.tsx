import { useState } from "react";
import { useClientAuthStore } from "@/stores/client/useClientAuthStore";

export default function ChangePasswordPage() {
  const { changePassword, loading, successMessage, errorMessage, clearMessages } =
    useClientAuthStore();

  const [form, setForm] = useState({
    oldPassword: "",
    newPassword: "",
  });

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    clearMessages();
    await changePassword(form);
  };

  return (
    <div className="min-h-screen px-4 py-10 bg-slate-50">
      <div className="max-w-md p-8 mx-auto bg-white shadow-sm rounded-3xl">
        <h1 className="text-2xl font-bold text-slate-900">Đổi mật khẩu</h1>

        <form onSubmit={handleSubmit} className="mt-6 space-y-4">
          <input
            type="password"
            value={form.oldPassword}
            onChange={(e) => setForm((prev) => ({ ...prev, oldPassword: e.target.value }))}
            placeholder="Mật khẩu cũ"
            className="w-full px-4 py-3 border outline-none rounded-2xl border-slate-300 focus:border-violet-500"
          />

          <input
            type="password"
            value={form.newPassword}
            onChange={(e) => setForm((prev) => ({ ...prev, newPassword: e.target.value }))}
            placeholder="Mật khẩu mới"
            className="w-full px-4 py-3 border outline-none rounded-2xl border-slate-300 focus:border-violet-500"
          />

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
            className="w-full px-4 py-3 font-medium text-white rounded-2xl bg-violet-600 hover:bg-violet-700"
          >
            {loading ? "Đang xử lý..." : "Đổi mật khẩu"}
          </button>
        </form>
      </div>
    </div>
  );
}