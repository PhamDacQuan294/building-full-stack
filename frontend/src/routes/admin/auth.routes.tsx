import Login from "@/pages/admin/Auth/Login";
import ForgotPassword from "@/pages/admin/Auth/ForgotPassword";
import VerifyOtp from "@/pages/admin/Auth/VerifyOtp";
import ResetPassword from "@/pages/admin/Auth/ResetPassword";

const authRoutes = [
  {
    path: "/admin/login",
    element: <Login />,
  },
  {
    path: "/forgot-password",
    element: <ForgotPassword />,
  },
  {
    path: "/verify-otp",
    element: <VerifyOtp />,
  },
  {
    path: "/reset-password",
    element: <ResetPassword />,
  },
];

export default authRoutes;