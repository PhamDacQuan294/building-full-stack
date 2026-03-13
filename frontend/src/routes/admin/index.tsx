import { LayoutDefault } from "@/layouts/admin/LayoutDefault";
import Login from "@/pages/admin/Auth/Login";
import Building from "@/pages/admin/Buildings";
import CreateBuilding from "@/pages/admin/Buildings/CreateBuilding";
import EditBuilding from "@/pages/admin/Buildings/EditBuilding";
import CreateRole from "@/pages/admin/Roles/CreateRole";
import EditRole from "@/pages/admin/Roles/EditRole";
import RoleList from "@/pages/admin/Roles/RoleList";
import ProtectedRoute from "./ProtectedRoute";
import UserList from "@/pages/admin/Users/UserList";
import CreateUser from "@/pages/admin/Users/CreateUser";
import ForgotPassword from "@/pages/admin/Auth/ForgotPassword";
import VerifyOtp from "@/pages/admin/Auth/VerifyOtp";
import ResetPassword from "@/pages/admin/Auth/ResetPassword";
// import Profile from "@/pages/admin/Profile";
// import Login from "@/pages/Login";
// import ProtectedRoute from "@/components/ProtectedRoute";

const routes = [
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

  {
    element: <ProtectedRoute roles={["ADMIN"]} />,
    children: [
      {
        path: "/admin",
        element: <LayoutDefault />,
        children: [
          {
            path: "buildings",
            element: <Building />,
          },
          {
            path: "buildings/create",
            element: <CreateBuilding />,
          },
          {
            path: "buildings/:id/edit",
            element: <EditBuilding />,
          },
          {
            path: "roles",
            element: <RoleList />,
          },
          {
            path: "roles/create",
            element: <CreateRole />,
          },
          {
            path: "roles/:id/edit",
            element: <EditRole />,
          },
          {
            path: "users",
            element: <UserList />,
          },
          {
            path: "users/create",
            element: <CreateUser />,
          },
        ],
      },
    ],
  },

  // {
  //   element: <ProtectedRoute roles={["ADMIN", "STAFF"]} />,
  //   children: [
  //     {
  //       path: "/admin/profile",
  //       element: <Profile />
  //     }
  //   ]
  // }
];

export default routes;
