import { LayoutDefault } from "@/layouts/admin/LayoutDefault";
import Building from "@/pages/admin/Buildings";
import CreateBuilding from "@/pages/admin/Buildings/CreateBuilding";
import EditBuilding from "@/pages/admin/Buildings/EditBuilding";
import CreateRole from "@/pages/admin/Roles/CreateRole";
import EditRole from "@/pages/admin/Roles/EditRole";
import RoleList from "@/pages/admin/Roles/RoleList";

const routes = [
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
        element: <EditRole/>
      }
    ]
  }
]

export default routes;