import { Navigate, Outlet } from "react-router-dom";
import { getToken, getRolesFromToken } from "@/utils/auth";

type ProtectedRouteProps = {
  roles?: string[];
};

export default function ProtectedRoute({ roles = [] }: ProtectedRouteProps) {
  const token = getToken();

  if (!token) {
    return <Navigate to="/admin/login" replace />;
  }

  if (roles.length > 0) {
    const userRoles = getRolesFromToken();
    const hasPermission = roles.some((role) => userRoles.includes(role));

    if (!hasPermission) {
      return <Navigate to="/403" replace />;
    }
  }

  return <Outlet />;
}