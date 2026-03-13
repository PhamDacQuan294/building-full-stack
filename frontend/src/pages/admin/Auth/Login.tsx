import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "sonner";
import { saveToken } from "@/utils/auth";
import { authService } from "@/services/admin/authService";

export default function Login() {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    email: "",
    password: "",
  });

  const [loading, setLoading] = useState(false);

  const handleChange = (key: "email" | "password", value: string) => {
    setForm((prev) => ({
      ...prev,
      [key]: value,
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      setLoading(true);

      const token = await authService.login(form);
      saveToken(token);

      toast.success("Đăng nhập thành công");
      navigate("/admin");
    } catch (error) {
      console.error(error);
      toast.error("Đăng nhập thất bại");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex items-center justify-center min-h-screen p-4 bg-slate-100">
      <div className="w-full max-w-md p-6 bg-white shadow rounded-2xl">
        <h1 className="mb-6 text-2xl font-bold text-center">Đăng nhập</h1>

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block mb-1 text-sm font-medium">Email</label>
            <input
              type="email"
              value={form.email}
              onChange={(e) => handleChange("email", e.target.value)}
              placeholder="Nhập email"
              className="w-full px-3 py-2 border rounded-xl"
            />
          </div>

          <div>
            <label className="block mb-1 text-sm font-medium">Password</label>
            <input
              type="password"
              value={form.password}
              onChange={(e) => handleChange("password", e.target.value)}
              placeholder="Nhập password"
              className="w-full px-3 py-2 border rounded-xl"
            />
          </div>

          <button
            type="submit"
            disabled={loading}
            className="w-full py-2 text-white rounded-xl bg-violet-600 hover:bg-violet-700"
          >
            {loading ? "Đang đăng nhập..." : "Đăng nhập"}
          </button>

          <div className="text-right">
            <a
              href="/forgot-password"
              className="text-sm text-violet-600 hover:underline"
            >
              Quên mật khẩu?
            </a>
          </div>
        </form>
      </div>
    </div>
  );
}
