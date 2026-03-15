import { LayoutDefault } from "@/layouts/admin/LayoutDefault";
import ProtectedRoute from "./ProtectedRoute";

import authRoutes from "./auth.routes";
import buildingSharedRoutes from "./building.shared.routes";
import buildingAdminRoutes from "./building.admin.routes";
import roleRoutes from "./role.routes";
import userRoutes from "./user.routes";
import customerRoutes from "./customer.routes";
import transactionRoutes from "./transaction.routes";
import systemRoutes from "./system.routes";

const routes = [
  ...authRoutes,

  {
    element: <ProtectedRoute roles={["ADMIN", "STAFF"]} />,
    children: [
      {
        path: "/admin",
        element: <LayoutDefault />,
        children: [...buildingSharedRoutes],
      },
    ],
  },

  {
    element: <ProtectedRoute roles={["ADMIN"]} />,
    children: [
      {
        path: "/admin",
        element: <LayoutDefault />,
        children: [
          ...systemRoutes,
          ...buildingAdminRoutes,
          ...roleRoutes,
          ...userRoutes,
          ...customerRoutes,
          ...transactionRoutes,
        ],
      },
    ],
  },
];

export default routes;