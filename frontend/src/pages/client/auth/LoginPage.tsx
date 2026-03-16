import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useClientAuthStore } from "@/stores/client/useClientAuthStore";

export default function LoginPage() {
  const navigate = useNavigate();
  const { login, loading, errorMessage, successMessage, clearMessages } = useClientAuthStore();

  const [form, setForm] = useState({
    email: "",
    password: "",
  });

  const handleChange = (key: "email" | "password", value: string) => {
    clearMessages();
    setForm((prev) => ({ ...prev, [key]: value }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const ok = await login(form);
    if (ok) {
      navigate("/profile");
    }
  };

  return (
    <div className="flex items-center justify-center min-h-screen px-4 bg-slate-50">
      <div className="w-full max-w-md p-8 bg-white shadow-sm rounded-3xl">
        <h1 className="text-2xl font-bold text-slate-900">Đăng nhập</h1>
        <p className="mt-2 text-sm text-slate-500">Đăng nhập để xem hồ sơ và gửi yêu cầu</p>

        <form onSubmit={handleSubmit} className="mt-6 space-y-4">
          <input
            value={form.email}
            onChange={(e) => handleChange("email", e.target.value)}
            placeholder="Email"
            className="w-full px-4 py-3 border outline-none rounded-2xl border-slate-300 focus:border-violet-500"
          />

          <input
            type="password"
            value={form.password}
            onChange={(e) => handleChange("password", e.target.value)}
            placeholder="Mật khẩu"
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
            {loading ? "Đang xử lý..." : "Đăng nhập"}
          </button>
        </form>

        <div className="flex items-center justify-between mt-4 text-sm">
          <Link to="/forgot-password" className="text-violet-600 hover:underline">
            Quên mật khẩu
          </Link>

          <Link to="/register" className="text-violet-600 hover:underline">
            Đăng ký
          </Link>
        </div>
      </div>
    </div>
  );
}