import { authService } from "@/services/admin/authService";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "sonner";

export default function ForgotPassword() {
  const [email, setEmail] = useState("");
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      setLoading(true);
      await authService.forgotPassword(email);

      toast.success("Đã gửi OTP về email");
      navigate(`/verify-otp?email=${encodeURIComponent(email)}`);
    } catch (error: any) {
      console.error("FORGOT PASSWORD ERROR:", error?.response?.data || error);
      toast.error(error?.response?.data?.detail || "Gửi OTP thất bại");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex items-center justify-center min-h-screen p-4 bg-slate-100">
      <div className="w-full max-w-md p-6 bg-white shadow rounded-2xl">
        <h1 className="mb-6 text-2xl font-bold text-center">Quên mật khẩu</h1>

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block mb-1 text-sm font-medium">Email</label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="w-full px-3 py-2 border rounded-xl"
              placeholder="Nhập email"
            />
          </div>

          <button
            type="submit"
            disabled={loading}
            className="w-full py-2 text-white rounded-xl bg-violet-600"
          >
            {loading ? "Đang gửi..." : "Gửi OTP"}
          </button>
        </form>
      </div>
    </div>
  );
}