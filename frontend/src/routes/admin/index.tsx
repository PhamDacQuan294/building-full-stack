import { LayoutDefault } from "@/layouts/admin/LayoutDefault";
import Building from "@/pages/admin/Buildings";
import CreateBuilding from "@/pages/admin/Buildings/CreateBuilding";
import EditBuilding from "@/pages/admin/Buildings/EditBuilding";

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
      }
    ]
  }
]

export default routes;