import { LayoutDefault } from "@/layouts/admin/LayoutDefault";
import Building from "@/pages/admin/Buildings";

const routes = [
  {
    path: "/admin",
    element: <LayoutDefault />,
    children: [
      {
        path: "buildings",
        element: <Building />
      }
    ]
  }
]

export default routes;