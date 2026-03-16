import LoginPage from "@/pages/client/auth/LoginPage";
import RegisterPage from "@/pages/client/auth/RegisterPage";
import ForgotPasswordPage from "@/pages/client/auth/ForgotPasswordPage";
import ProfilePage from "@/pages/client/auth/ProfilePage";
import ChangePasswordPage from "@/pages/client/auth/ChangePasswordPage";

const authClientRoutes = [
  {
    path: "/login",
    element: <LoginPage />,
  },
  {
    path: "/register",
    element: <RegisterPage />,
  },
  {
    path: "/forgot-password",
    element: <ForgotPasswordPage />,
  },
  {
    path: "/profile",
    element: <ProfilePage />,
  },
  {
    path: "/change-password",
    element: <ChangePasswordPage />,
  },
];

export default authClientRoutes;
