import { authService } from "@/services/admin/authService";
import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { toast } from "sonner";

export default function ResetPassword() {
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [loading, setLoading] = useState(false);

  const location = useLocation();
  const navigate = useNavigate();

  const email =
    new URLSearchParams(location.search).get("email") || "";
  const otp =
    new URLSearchParams(location.search).get("otp") || "";

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (newPassword !== confirmPassword) {
      toast.error("Mật khẩu nhập lại không khớp");
      return;
    }

    try {
      setLoading(true);
      await authService.resetPassword(email, otp, newPassword);

      toast.success("Đổi mật khẩu thành công");
      navigate("/admin/login");
    } catch (error: unknown) {
      console.error(error);
      toast.error("Đổi mật khẩu thất bại");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex items-center justify-center min-h-screen p-4 bg-slate-100">
      <div className="w-full max-w-md p-6 bg-white shadow rounded-2xl">
        <h1 className="mb-6 text-2xl font-bold text-center">Đặt lại mật khẩu</h1>

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block mb-1 text-sm font-medium">Mật khẩu mới</label>
            <input
              type="password"
              value={newPassword}
              onChange={(e) => setNewPassword(e.target.value)}
              className="w-full px-3 py-2 border rounded-xl"
              placeholder="Nhập mật khẩu mới"
            />
          </div>

          <div>
            <label className="block mb-1 text-sm font-medium">Nhập lại mật khẩu</label>
            <input
              type="password"
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
              className="w-full px-3 py-2 border rounded-xl"
              placeholder="Nhập lại mật khẩu"
            />
          </div>

          <button
            type="submit"
            disabled={loading}
            className="w-full py-2 text-white rounded-xl bg-violet-600"
          >
            {loading ? "Đang đổi..." : "Đổi mật khẩu"}
          </button>
        </form>
      </div>
    </div>
  );
}