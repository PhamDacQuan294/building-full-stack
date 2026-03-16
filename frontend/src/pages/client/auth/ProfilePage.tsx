import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { useClientAuthStore } from "@/stores/client/useClientAuthStore";

export default function ProfilePage() {
  const { user, fetchMe, updateProfile, loading, successMessage, errorMessage, clearMessages, logout } =
    useClientAuthStore();

  const [form, setForm] = useState({
    fullName: "",
    email: "",
    phone: "",
  });

  useEffect(() => {
    fetchMe();
  }, [fetchMe]);

  useEffect(() => {
    if (user) {
      setForm({
        fullName: user.fullName || "",
        email: user.email || "",
        phone: user.phone || "",
      });
    }
  }, [user]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    clearMessages();
    await updateProfile(form);
  };

  return (
    <div className="min-h-screen px-4 py-10 bg-slate-50">
      <div className="max-w-2xl p-8 mx-auto bg-white shadow-sm rounded-3xl">
        <div className="flex items-center justify-between">
          <h1 className="text-2xl font-bold text-slate-900">Hồ sơ cá nhân</h1>
          <button
            onClick={logout}
            className="px-4 py-2 text-sm border rounded-2xl border-slate-300 hover:bg-slate-50"
          >
            Đăng xuất
          </button>
        </div>

        <form onSubmit={handleSubmit} className="mt-6 space-y-4">
          <input
            value={form.fullName}
            onChange={(e) => setForm((prev) => ({ ...prev, fullName: e.target.value }))}
            placeholder="Họ tên"
            className="w-full px-4 py-3 border outline-none rounded-2xl border-slate-300 focus:border-violet-500"
          />

          <input
            value={form.email}
            onChange={(e) => setForm((prev) => ({ ...prev, email: e.target.value }))}
            placeholder="Email"
            className="w-full px-4 py-3 border outline-none rounded-2xl border-slate-300 focus:border-violet-500"
          />

          <input
            value={form.phone}
            onChange={(e) => setForm((prev) => ({ ...prev, phone: e.target.value }))}
            placeholder="Số điện thoại"
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

          <div className="flex flex-wrap gap-3">
            <button
              type="submit"
              disabled={loading}
              className="px-5 py-3 font-medium text-white rounded-2xl bg-violet-600 hover:bg-violet-700"
            >
              {loading ? "Đang lưu..." : "Cập nhật hồ sơ"}
            </button>

            <Link
              to="/change-password"
              className="px-5 py-3 font-medium border rounded-2xl border-slate-300 text-slate-700 hover:bg-slate-50"
            >
              Đổi mật khẩu
            </Link>
          </div>
        </form>
      </div>
    </div>
  );
}