import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useClientAuthStore } from "@/stores/client/useClientAuthStore";

export default function RegisterPage() {
  const navigate = useNavigate();
  const { register, loading, errorMessage, successMessage, clearMessages } = useClientAuthStore();

  const [form, setForm] = useState({
    fullName: "",
    email: "",
    phone: "",
    password: "",
  });

  const handleChange = (
    key: "fullName" | "email" | "phone" | "password",
    value: string
  ) => {
    clearMessages();
    setForm((prev) => ({ ...prev, [key]: value }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const ok = await register(form);
    if (ok) {
      setTimeout(() => navigate("/login"), 1000);
    }
  };

  return (
    <div className="flex items-center justify-center min-h-screen px-4 bg-slate-50">
      <div className="w-full max-w-md p-8 bg-white shadow-sm rounded-3xl">
        <h1 className="text-2xl font-bold text-slate-900">Đăng ký tài khoản</h1>

        <form onSubmit={handleSubmit} className="mt-6 space-y-4">
          <input
            value={form.fullName}
            onChange={(e) => handleChange("fullName", e.target.value)}
            placeholder="Họ tên"
            className="w-full px-4 py-3 border outline-none rounded-2xl border-slate-300 focus:border-violet-500"
          />

          <input
            value={form.email}
            onChange={(e) => handleChange("email", e.target.value)}
            placeholder="Email"
            className="w-full px-4 py-3 border outline-none rounded-2xl border-slate-300 focus:border-violet-500"
          />

          <input
            value={form.phone}
            onChange={(e) => handleChange("phone", e.target.value)}
            placeholder="Số điện thoại"
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
            {loading ? "Đang xử lý..." : "Đăng ký"}
          </button>
        </form>

        <div className="mt-4 text-sm">
          <Link to="/login" className="text-violet-600 hover:underline">
            Đã có tài khoản? Đăng nhập
          </Link>
        </div>
      </div>
    </div>
  );
}