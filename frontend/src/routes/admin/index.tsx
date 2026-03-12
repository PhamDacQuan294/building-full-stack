import { LayoutDefault } from "@/layouts/admin/LayoutDefault";
import Login from "@/pages/admin/Auth/Login";
import Building from "@/pages/admin/Buildings";
import CreateBuilding from "@/pages/admin/Buildings/CreateBuilding";
import EditBuilding from "@/pages/admin/Buildings/EditBuilding";
import CreateRole from "@/pages/admin/Roles/CreateRole";
import EditRole from "@/pages/admin/Roles/EditRole";
import RoleList from "@/pages/admin/Roles/RoleList";
import ProtectedRoute from "./ProtectedRoute";
// import Profile from "@/pages/admin/Profile";
// import Login from "@/pages/Login";
// import ProtectedRoute from "@/components/ProtectedRoute";

const routes = [
  {
    path: "/admin/login",
    element: <Login />
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
            element: <Building />
          },
          {
            path: "buildings/create",
            element: <CreateBuilding />
          },
          {
            path: "buildings/:id/edit",
            element: <EditBuilding />
          },
          {
            path: "roles",
            element: <RoleList />
          },
          {
            path: "roles/create",
            element: <CreateRole />
          },
          {
            path: "roles/:id/edit",
            element: <EditRole />
          }
        ]
      }
    ]
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